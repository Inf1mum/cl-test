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
public class AddHumanTabController {
    private final HumanService humanService;
    private final ViewLoader viewLoader;

    @FXML
    private TextField nameColumn;
    @FXML
    private TextField ageColumn;
    @FXML
    private DatePicker birthdateColumn;

    public AddHumanTabController(HumanService humanService, ViewLoader viewLoader) {
        this.humanService = humanService;
        this.viewLoader = viewLoader;
    }

    public void initialize() {
    }

    @FXML
    public void save(MouseEvent mouseEvent) {
        //todo add validations for incoming data
        Human human = Human.builder()
            .name(nameColumn.getText())
            .age(Integer.parseInt(ageColumn.getText()))
            .birthday(birthdateColumn.getValue())
            .build();
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
}
