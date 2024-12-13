package com.example.groupprojectcardgame;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.groupprojectcardgame.Animation.*;
import static com.example.groupprojectcardgame.Card.setImage;


public class GameScreenController {

    //init JavaFX containers and buttons
    @FXML
    private HBox topRow;

    @FXML
    private HBox bottomRow;

    @FXML
    private StackPane rootPane;

    @FXML
    private ImageView imagePane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane rightStackPane;

    @FXML
    private Button submit;

    //init decks and selected hand array list
    private final Deck FULLDECK = new Deck(); //reference of full card deck
    private Deck deck = new Deck(); //deck used for game
    private ArrayList<Card> selectedHand = new ArrayList<>(); //selected hand to keep track of cards

    //init players and health bars
    private Player user = new Player("User", 100); //use 100 health for live demo
    private Computer comp = new Computer("CPU", 100);
    private ProgressBar userProgress = new ProgressBar();
    private ProgressBar compProgress = new ProgressBar();

    //init action class to test hands within
    private final Action action = new Action();

    //init enum game status
    private enum Status{
        START,
        END,
        P1, //user turn
        P2 //comp turn
    }
    private Status gameStatus = Status.START;


    /**
     * starts the game
     */
    @FXML
    public void initialize() {

        //bind health bars
        userProgress.setProgress(1);
        compProgress.setProgress(1);

        // Resizes the background image to the size of the window ie- allows fullscreen
        imagePane.fitWidthProperty().bind(rootPane.widthProperty());
        imagePane.fitHeightProperty().bind(rootPane.heightProperty());

        // Initializes the 5 card buttons to the top and bottom rows
        addButtons(topRow);
        addButtons(bottomRow);

        // add deck button
        addDeck(rightStackPane);

        // create onscreen health bars
        createHealthBars();

        //setup submit hand button
        submit.setVisible(false);
        submit.setOnAction(actionEvent -> {
            testHand(bottomRow);
        });

        //deal cards to both players
        dealCards(topRow, true); //second parameter hides the deck
        dealCards(bottomRow, false);

        //pick the player to start the game
        pickStarter();
        if(gameStatus == Status.P1){ //run for user turn
            turn(user);
        } else if(gameStatus == Status.P2) { //run for cpu 1 turn
            turn(comp);
        }

        // Determines winner and displays the appropriate animation
        if (user.getHealthPoints() == 0 || comp.getHealthPoints() == 0) {
            gameStatus = Status.END;
            if (user.getHealthPoints() > comp.getHealthPoints()) {
                announceWinner(user);
            } else {
                announceWinner(comp);
            }
        }
    }

    /**
     * creates health bars for the players using health instance variable
     */
    private void createHealthBars() {
        // Create separate containers for health bars
        StackPane computerHealthBarContainer = new StackPane();
        StackPane playerHealthBarContainer = new StackPane();

        compProgress.setPrefWidth(400);
        userProgress.setPrefWidth(400);

        // Add health bars to containers
        computerHealthBarContainer.getChildren().add(compProgress);
        playerHealthBarContainer.getChildren().add(userProgress);

        // Set mouseTransparent property to true
        computerHealthBarContainer.setMouseTransparent(true);
        playerHealthBarContainer.setMouseTransparent(true);

        // Set health bar text
        computerHealthBarContainer.getChildren().add(comp.getPlayerHealthText());
        playerHealthBarContainer.getChildren().add(user.getPlayerHealthText());

        // Add containers to game screen
        rootPane.getChildren().add(computerHealthBarContainer);
        rootPane.getChildren().add(playerHealthBarContainer);

        // Position health bar containers
        computerHealthBarContainer.setTranslateX(10);
        computerHealthBarContainer.setTranslateY(topRow.getBoundsInParent().getMinY() - 120); // Move up by 80 pixels
        playerHealthBarContainer.setTranslateX(10);
        playerHealthBarContainer.setTranslateY(bottomRow.getBoundsInParent().getMaxY() + 120); // Move down by 80 pixels
    }

    /**
     * creates disabled deck button on right side of screen
     * @param location StackPane id to place deck (uses right)
     */
    public void addDeck(StackPane location) {
        deck.shuffle(); //shuffle the deck before dealing

        //places cards on the deck button for animation purposes
        for (int i = 0; i <= deck.size(); i++) {
            Button cardButton = new Button("Card "); // Representing a card with a button
            cardButton.setPrefSize(80, 120);
            cardButton.setId("null"); //card id pairs with card label
            cardButton.setText(""); //remove text for only image
            cardButton.setDisable(true); //disable user control

            //attach img to button
            Image img = new Image("com/example/groupprojectcardgame/images/Card Folder/1CardBackDesignCardDesigns.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(cardButton.getPrefHeight());
            view.setFitWidth(cardButton.getPrefWidth());
            cardButton.setGraphic(view);
            cardButton.setStyle("-fx-background-color: transparent;");

            //add card to StackPane
            location.getChildren().add(cardButton);
        }
    }

    /**
     * adds 5-button-card hands to specified HBox
     * @param location HBox location to add buttons
     */
    public void addButtons(HBox location) {
        // Add 5 cards to the row
        for (int i = 1; i <= 5; i++) {
            Button cardButton = new Button("Card " + i); // Representing a card with a button
            cardButton.setPrefSize(80, 120);
            cardButton.setId("null"); //card id pairs with card label
            cardButton.setText(""); //remove text for only image
            cardButton.setDisable(true); //disable user control

            //attach img to button
            Image img = new Image("com/example/groupprojectcardgame/images/Card Folder/1CardBackDesignCardDesigns.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(cardButton.getPrefHeight());
            view.setFitWidth(cardButton.getPrefWidth());
            cardButton.setGraphic(view);

            //add select card action to button
            cardButton.setOnAction(actionEvent -> {
                selectCard(cardButton);
            });

            //add button to hand
            location.getChildren().add(cardButton);
        }
    }


    /**
     * assigns cards to any empty buttons in a hand
     * @param location HBox hand to deal cards
     * @param disable if true, cards will be invisible and disabled for the user
     */
    public void dealCards(HBox location, boolean disable) {
        //get the buttons at the hand location
        ObservableList<Node> buttons = location.getChildren();

        //for every empty button, draw card and link its variables
        for (Node button : buttons) {
            if(button.getId().equals("null")) {
                //overdraw protection
                if(deck.size() <= 0){
                    deck = new Deck();
                    deck.shuffle();
                }

                //draw card and assign id
                Card card = deck.draw();
                button.setId(card.getLabel());
                button.setStyle("-fx-background-color: transparent;");

                //if parameter is 'false', re-enable the button and view the card to the user
                if(!disable){
                    button.setDisable(false);
                }
                //show fancy animation for dealing
                dealAnimation(card, rightStackPane, (Button) button, disable, borderPane);
            }
        }
    }


    /**
     * Allows the user to select card, deselect card, and submit their hand
     * @param button Button to become selectable
     */
    public void selectCard(Button button){
        Card card = FULLDECK.getCard(button.getId()); //find card linked with button id

        //if the card is already selected, deselect it
        if(selectedHand.contains(card)){
            selectedHand.remove(card);
            button.setStyle("-fx-background-color: transparent;");
        } else{ //otherwise select the card
            selectedHand.add(card);
            button.setStyle("-fx-border-color: #c2f0ee; -fx-border-width: 5px;");
        }

        //if any card is selected, enable submit button
        submit.setVisible(!selectedHand.isEmpty());
    }


    /**
     * takes in selected cards, tests if they are valid, deals damage based on hand, and removes cards from hand
     * @param location HBox hand to remove and deal cards to
     */
    public void testHand(HBox location) {
        //pass selected hand and determine validity
        double dmg = action.calculateDamage(selectedHand);

        //OR if one card is played, set damage to its value
        if (selectedHand.size() == 1) {
            dmg = selectedHand.getFirst().getRank();
        }

        //if the hand is valid, do damage to comp, remove cards, and deal new cards
        if (dmg > 0) {
            double newHealth = comp.getHealthPoints() - dmg;
            if (newHealth < 0) {
                comp.setHealth(0);
            } else {
                comp.setHealth(newHealth);
            }
            comp.updateHealthText();
            compProgress.setProgress(comp.getHealthPoints() / 100);

            //check if player dealth enough damage to win
            if (comp.getHealthPoints() == 0) {
                announceWinner(user);
            }

            // for every selected button in hand, get its linked card and remove it from hand
            ObservableList<Node> hand = location.getChildren();
            for (Node button : hand) { //find cards in players hand and remove them
                Card card = FULLDECK.getCard(button.getId());
                if (selectedHand.contains(card)) { //delete card
                    button.setStyle("-fx-border-width: 0;");
                    button.setId("null");
                    button.setDisable(true);
                    Card blank = new Card("none", 0, "na",
                            "com/example/groupprojectcardgame/images/Card Folder/1CardBackDesignCardDesigns.png");
                    setImage((Button) button, blank);
                }
            }

            //remove submit button, clear selected hand, and deal new cards
            submit.setVisible(false);
            selectedHand.clear();
            dealCards(location, false);

            //pass turn to computer
            if (gameStatus == Status.P1) {
                turn(comp);
            }
        }
    }

    /**
     * picks either the user or computer to start
     */
    public void pickStarter(){
        //calculates a random number 0 or 1
        int random = (int)(Math.random()*2);
        switch (random){ //user goes first
            case 0:
                gameStatus = Status.P1;
                //System.out.println(user.getName()+" starts!");
                break;
            case 1: //comp goes first
                gameStatus = Status.P2;
                //System.out.println(comp.getName()+ " starts!");
                break;
            default:
                break;
        }
    }

    /**
     * runs the tests for the computer, deals damage, and removes cards from the comp's hand
     * @param player current Player turn
     */
    public void turn(Player player){
        //check if player's turn, and reinstates game status if needed
        if (player.getName().equals("User")) {
            gameStatus = Status.P1;
            dealCards(bottomRow, false);
        } else { //check for comp's turn
            if (comp.getHealthPoints() > 0) { //if computer is still in game
                gameStatus = Status.P2;
//                dealCards(topRow, true);

                // convert comp buttons to cards
                ObservableList<Node> hand = topRow.getChildren();
                ArrayList<Card> cards = new ArrayList<>();
                for (int index = 0; index < 5; index++) {
                    cards.add(FULLDECK.getCard(hand.get(index).getId())); // <<
                }

                // pass cards and return hand dmg
                double dmg = action.calculateDamage(cards);
                user.setHealth(user.getHealthPoints() - dmg);
                userProgress.setProgress(user.getHealthPoints()/100);
                user.updateHealthText();

                // remove cards from comp hand
                ObservableList<Node> top = topRow.getChildren();
                for (Node button : top) { // find cards in players hand and remove them
                    Card card = FULLDECK.getCard(button.getId());
                    if (cards.contains(card)) {
                        button.setId("null");
                        button.setDisable(true);
                        Card blank = new Card("none", 0, "na",
                                "com/example/groupprojectcardgame/images/Card Folder/1CardBackDesignCardDesigns.png");
                        setImage((Button) button, blank);
                    }
                }

                //deal new cards to comp and pass turn to user
                dealCards(topRow, true);
                if (user.getHealthPoints() > 0) { //if user is still in game
                    turn(user);
                } else { //otherwise declare comp win
                    announceWinner(comp);
                }
            } else { //otherise declare user win
                announceWinner(user);
            }
        }
    }


    /**
     * starts winner animation and asks user if they want to play again
     * @param player Player who won the game
     */
    public void announceWinner(Player player){
        announceAnimation(player.getName(), rootPane);
        restartGame();
    }


    /**
     * creates a restart button that reloads the scene on activation
     */
    private void restartGame() {
        //create restart button
        Button restartButton = new Button();
        restartButton.setText("Restart game?");
        restartButton.setPrefWidth(200); // Set the width
        restartButton.setPrefHeight(100);
        rootPane.getChildren().add(restartButton);
        rootPane.setAlignment(restartButton, Pos.TOP_LEFT);

        //set activate to reload the scene
        restartButton.setOnAction(actionEvent -> {
            // Load the Game Screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/groupprojectcardgame/GameScreen.fxml"));
            Parent gameScreenRoot = null;
            try {
                gameScreenRoot = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Get the current stage from the button event
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene to the Game Screen
            stage.setScene(new Scene(gameScreenRoot));
            stage.setTitle("Game Screen");
            stage.setFullScreen(true);
            stage.show();
        });
    }
}
