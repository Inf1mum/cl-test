package org.example.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.fxml.LoadedView.View;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViewLoader {
    private static final String TAB_FOLDER = "/fxml";

    private final ApplicationContext applicationContext;

    public <T> LoadedView<T> get(View<T> view) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("%s/%s", TAB_FOLDER, view.getFxmlName())));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();
            Object objController = fxmlLoader.getController();
            T controller = view.getControllerType().cast(objController);
            return LoadedView.<T>builder().parent(parent).controller(controller).build();
        } catch (Exception e) {
            log.error(String.format("Can't load tab with name - %s", view.getFxmlName()));
            throw new IllegalStateException();
        }
    }
}
