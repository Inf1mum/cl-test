package org.example.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.example.entity.Human;
import org.example.fxml.LoadedView;
import org.example.fxml.LoadedView.View;
import org.example.fxml.ViewLoader;
import org.example.service.HumanService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Scope("prototype")
public class HumansTabController {
    private final HumanService humanService;
    private final ViewLoader fxmlLoader;

    @FXML
    private TreeTableView<Human> humansView;
    @FXML
    private TreeItem<Human> root;
    @FXML
    private TreeTableColumn<Human, String> nameColumn;
    @FXML
    private TreeTableColumn<Human, Object> ageColumn;
    @FXML
    private TreeTableColumn<Human, Object> birthdateColumn;

    public HumansTabController(HumanService humanService, ViewLoader fxmlLoader) {
        this.humanService = humanService;
        this.fxmlLoader = fxmlLoader;
    }

    @FXML
    public void initialize() {
        root = new TreeItem<>(Human.builder().name("Humans").build());
        List<Human> humans = humanService.findAll();
        humans.forEach((human) -> root.getChildren().add(new TreeItem<>(human)));

        nameColumn.setCellValueFactory(
            (TreeTableColumn.CellDataFeatures<Human, String> param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getName())
        );
        ageColumn.setCellValueFactory(
            (TreeTableColumn.CellDataFeatures<Human, Object> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getAge())
        );
        birthdateColumn.setCellValueFactory(
            (TreeTableColumn.CellDataFeatures<Human, Object> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getBirthday())
        );
        root.setExpanded(true);
        humansView.setOnMouseClicked(this::handleMouseClick);
        humansView.setRoot(root);
    }

    @FXML
    public void addHuman(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Scene scene = button.getScene();
        LoadedView<AddHumanTabController> view = fxmlLoader.get(View.ADD_HUMAN);
        scene.setRoot(view.getParent());
    }

    @FXML
    public void editHuman(MouseEvent mouseEvent) {
        TreeItem<Human> selectedHuman = humansView.getSelectionModel().getSelectedItem();
        boolean isHumanSelected = Optional.ofNullable(selectedHuman)
            .map(TreeItem::getValue)
            .map(Human::getId)
            .isPresent();

        if (!isHumanSelected) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please, select user for editing");
            alert.showAndWait();
        } else {
            Button button = (Button) mouseEvent.getSource();
            Scene scene = button.getScene();
            LoadedView<EditHumanTabController> view = fxmlLoader.get(View.EDIT_HUMAN);
            view.getController().initData(selectedHuman.getValue().getId());
            scene.setRoot(view.getParent());
        }

    }

    @FXML
    public void deleteHuman(MouseEvent mouseEvent) {
        TreeItem<Human> selectedHuman = humansView.getSelectionModel().getSelectedItem();
        boolean isHumanSelected = Optional.ofNullable(selectedHuman)
            .map(TreeItem::getValue)
            .map(Human::getId)
            .isPresent();

        if (!isHumanSelected) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please, select user for deleting");
            alert.showAndWait();
        } else {
            humanService.delete(selectedHuman.getValue());
            Button button = (Button) mouseEvent.getSource();
            Scene scene = button.getScene();
            LoadedView<HumansTabController> view = fxmlLoader.get(View.HUMANS);
            scene.setRoot(view.getParent());
        }
    }


    private void handleMouseClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            TreeItem<Human> selectedHuman = humansView.getSelectionModel().getSelectedItem();
            boolean isHumanSelected = Optional.ofNullable(selectedHuman)
                .map(TreeItem::getValue)
                .map(Human::getId)
                .isPresent();
            if (isHumanSelected && isTodayBirthday(selectedHuman.getValue().getBirthday())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Birthday");
                alert.setHeaderText(String.format("Today is %s's birthday", selectedHuman.getValue().getName()));
                alert.showAndWait();
            }
        }
    }

    private boolean isTodayBirthday(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        return today.getMonth() == birthday.getMonth() && today.getDayOfMonth() == birthday.getDayOfMonth();
    }
}
