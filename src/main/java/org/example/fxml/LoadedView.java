package org.example.fxml;

import javafx.scene.Parent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.AddHumanTabController;
import org.example.controller.EditHumanTabController;
import org.example.controller.HumansTabController;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadedView<T> {
    private Parent parent;
    private T controller;

    public static class View<T> {
        public final static View<HumansTabController> HUMANS = new View<>("HumansTab.fxml", HumansTabController.class);
        public final static View<EditHumanTabController> EDIT_HUMAN = new View<>("EditHumanTab.fxml", EditHumanTabController.class);
        public final static View<AddHumanTabController> ADD_HUMAN = new View<>("AddHumanTab.fxml", AddHumanTabController.class);

        private final String fxmlName;
        private final Class<T> controllerType;

        private View(String fxmlName, Class<T> controllerType) {
            this.fxmlName = fxmlName;
            this.controllerType = controllerType;
        }

        public String getFxmlName() {
            return fxmlName;
        }

        public Class<T> getControllerType() {
            return controllerType;
        }
    }
}
