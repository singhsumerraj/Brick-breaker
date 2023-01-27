package com.example.brickbreaker;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static Stage globalstage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Welcome to Start Page!");
        stage.setScene(scene);
        stage.show();
        globalstage = stage;
    }

    @FXML
    public void onButtonClick() throws IOException {
        // We have to open the game screen
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        globalstage.setScene(scene);
        globalstage.setTitle("Welcome to Game Screen");
        globalstage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}