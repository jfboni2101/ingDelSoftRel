package com.example.relazioneingdelsoft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Main della mia applicazione

public class ManagementApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManagementApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Boni S.R.L.");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}