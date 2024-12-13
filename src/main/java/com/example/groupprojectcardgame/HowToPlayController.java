package com.example.groupprojectcardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class HowToPlayController {
    @FXML
    private StackPane rootPane;

    @FXML
    private ImageView imagePane;

    @FXML
    public void initialize() {
        imagePane.fitWidthProperty().bind(rootPane.widthProperty());
        imagePane.fitHeightProperty().bind(rootPane.heightProperty());
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        try {
            // Load the Menu Screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/example/groupprojectcardgame/MenuScreen.fxml"));
            Parent menuScreenRoot = loader.load();

            // Get the current stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene to the Menu Screen
            stage.setScene(new Scene(menuScreenRoot));
            stage.setTitle("Welcome to Rumik");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
