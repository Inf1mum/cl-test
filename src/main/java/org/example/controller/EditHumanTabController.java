package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.entity.Human;
import org.example.fxml.LoadedView;
import org.example.fxml.LoadedView.View;
import org.example.fxml.ViewLoader;
import org.example.service.HumanService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EditHumanTabController {
    private final HumanService humanService;
    private final ViewLoader viewLoader;

    @FXML
    private TextField nameColumn;
    @FXML
    private TextField ageColumn;
    @FXML
    private DatePicker birthdateColumn;
    private Human human;

    public EditHumanTabController(HumanService humanService, ViewLoader viewLoader) {
        this.humanService = humanService;
        this.viewLoader = viewLoader;
    }

    public void initialize() {
    }

    @FXML
    public void save(MouseEvent mouseEvent) {
        //todo add validations for incoming data
        human.setName(nameColumn.getText());
        human.setAge(Integer.parseInt(ageColumn.getText()));
        human.setBirthday(birthdateColumn.getValue());
        humanService.save(human);

        Button button = (Button) mouseEvent.getSource();
        Scene scene = button.getScene();
        LoadedView<HumansTabController> humansView = viewLoader.get(View.HUMANS);
        scene.setRoot(humansView.getParent());
    }

    @FXML
    public void cancel(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Scene scene = button.getScene();
        LoadedView<HumansTabController> humansView = viewLoader.get(View.HUMANS);
        scene.setRoot(humansView.getParent());
    }

    public void initData(Long humanId) {
        Human human = humanService.findById(humanId)
            .orElseThrow(() -> new IllegalStateException(String.format("User with such id %s not found", humanId)));
        nameColumn.setText(human.getName());
        ageColumn.setText(human.getAge().toString());
        birthdateColumn.setValue(human.getBirthday());
        this.human = human;
    }
}
