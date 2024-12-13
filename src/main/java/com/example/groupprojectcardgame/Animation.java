package com.example.groupprojectcardgame;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;
import static com.example.groupprojectcardgame.Card.setImage;


/**
 * Class that holds the animations used on the GameScreen.
 */
public class Animation {

    /**
     * Displays an end game animation.
     * @param winner the winner of the game
     * @param location the location for the animation
     */
    public static void announceAnimation(String winner, StackPane location) {
        Platform.runLater(() -> {
            Path path = new Path();
            Button placeholder = new Button();
            Text text = new Text();

            // Start and end positions
            double startX = location.getWidth() * -0.2;
            double startY = location.getHeight() * -0.2;
            double endX = location.getWidth() * 0.34;
            double endY = location.getHeight() * 0.8;

            // Define the animation path
            path.getElements().add(new MoveTo(startX, startY)); // Starting point
            path.getElements().add(new LineTo(endX, endY));
            placeholder.setPrefSize(1000, 1500);

            // Card is blank if player loses and an Ace is player wins
            String winOrLose;
            if (winner.equals("CPU")) {
                Card blank = new Card("none", 0, "na",
                        "com/example/groupprojectcardgame/images/Card Folder/1CardBackDesignCardDesigns.png");
                setImage(placeholder, blank);
                winOrLose = "lose";
                text.setStyle("-fx-font-weight: bold; -fx-font-size: 60px; -fx-fill: black;");
            }
            else {
                Card card = new Card("none", 0, "na",
                        "com/example/groupprojectcardgame/images/Card Folder/AceHeartCardDesigns.png");
                setImage(placeholder, card);
                winOrLose = "win";
                text.setStyle("-fx-font-weight: bold; -fx-font-size: 40px; -fx-fill: white;");
            }
            placeholder.setStyle("-fx-background-color: transparent;"); // Match the look

            // THIS NEEDS TO BE BORDERPANE AND NOTHING ELSE, USING ROOTPANE WAS MY WHOLE ISSUE
            // Adds the transition to screen and displays
            location.getChildren().add(placeholder);
            PathTransition transition = new PathTransition(Duration.seconds(2), path, placeholder);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            // Displays you win or you lose on screen
            text.setText("You " + winOrLose + "!");
            text.setX(location.getWidth() * 0.35);
            text.setY(location.getWidth() * 0.8);
            transition.setOnFinished(e -> location.getChildren().add(text));
        });
    }


    /**
     * Displays a card dealing animation, cards move from the deck on screen to the players hand.
     * @param card the card to be displayed
     * @param startLocation the start location of the animation (deck in this case)
     * @param endLocation the end location of the animatino (the card slots in the players hand)
     * @param disable whether or not to show the cards being dealt
     * @param borderPane the pane the animation will display on
     */
    public static void dealAnimation(Card card, StackPane startLocation, Button endLocation, Boolean disable,
                                     BorderPane borderPane) {
        Platform.runLater(() -> {
            Path path = new Path();
            Button placeholder = new Button();

            // Get start position
            ObservableList<Node> StackPaneChild = startLocation.getChildren();
            Node button = StackPaneChild.getFirst();
            double startX = button.localToScene(0, 0).getX();
            double startY = button.localToScene(0, 0).getY();

            // Get end position
            double endX = endLocation.localToScene(0, 0).getX();
            double endY = endLocation.localToScene(0, 0).getY();

            // Define the animation path
            path.getElements().add(new MoveTo(startX, startY)); // Starting point
            path.getElements().add(new LineTo(endX + 49, endY + 65));
            placeholder.setPrefSize(80, 120);

            // Check if card should be shown or not (usually shows for user)
            if (disable.equals(true)) {
                Card blank = new Card("none", 0, "na",
                        "com/example/groupprojectcardgame/images/Card Folder/1CardBackDesignCardDesigns.png");
                setImage(placeholder, blank);
            } else {
                setImage(placeholder, card);
            }
            placeholder.setStyle("-fx-background-color: transparent;"); // Match the look

            // THIS NEEDS TO BE BORDERPANE AND NOTHING ELSE, USING ROOTPANE WAS MY WHOLE ISSUE
            borderPane.getChildren().add(placeholder);
            PathTransition transition = new PathTransition(Duration.seconds(0.7), path, placeholder);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            // When animation is finished, remove card from screen
            transition.setOnFinished(e -> {
                // Computer cards set blank
                if (disable.equals(true)) {
                    Card blank = new Card("none", 0, "na",
                            "com/example/groupprojectcardgame/images/Card Folder/1CardBackDesignCardDesigns.png");
                    setImage(endLocation, blank);
                }
                // Player cards shown
                else {
                    setImage(endLocation, card);
                }
                borderPane.getChildren().remove(placeholder); // Remove the animation card
            });
        });
    }
}
