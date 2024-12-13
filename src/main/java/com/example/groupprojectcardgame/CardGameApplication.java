package com.example.groupprojectcardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CardGameApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/groupprojectcardgame/MenuScreen.fxml"));
        Parent root = loader.load();

        // Set the scene
        Scene scene = new Scene(root);
        stage.setTitle("Card Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
