package com.example.gym;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML and create the scene
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 650);

        // Set the stage properties
        stage.setTitle("Gym Management System");
        stage.setScene(scene);

        // Make the stage non-resizable
        stage.setResizable(false);

        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
