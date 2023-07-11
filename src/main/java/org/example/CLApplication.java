package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fxml.LoadedView.View;
import org.example.fxml.ViewLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CLApplication extends Application {
    @Autowired
    private ViewLoader viewLoader;

    public static void main(String[] args) {
        Application.launch(CLApplication.class, args);
    }

    @Override
    public void init() {
        ConfigurableApplicationContext springContext = SpringApplication.run(getClass(), getParameters().getRaw().toArray(new String[0]));
        springContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(viewLoader.get(View.HUMANS).getParent());
        scene.getStylesheets().add(getClass().getResource("/fxml/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("CL Test");
        stage.centerOnScreen();
        stage.show();
    }
}
