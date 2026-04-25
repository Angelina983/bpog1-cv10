package com.mycompany.mavenproject1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

      @Override
    public void start(Stage stage) {
        ImagePixelApp app = new ImagePixelApp(stage);

        Scene scene = new Scene(app.getRoot(), 800, 600);

        stage.setTitle("Příklad 4 - Dialog pro výběr obrázku");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}