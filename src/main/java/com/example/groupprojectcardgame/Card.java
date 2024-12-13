package com.example.groupprojectcardgame;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Represents a standard playing card
 */
public class Card {
    private String suit;
    private int rank;
    private String label;
    private String src; // Card image

    /**
     * Initializes a card object
     * @param suit the suit of the card (heart, spades, etc.)
     * @param rank the rank of the card (1, 2, 3, etc.)
     * @param label the label of the card, allows non-numerical cards to be displayed, ie- AceHearts
     * @param src the cards associated image file
     */
    public Card(String suit, int rank, String label, String src) {
        this.suit = suit;
        this.rank = rank;
        this.label = label;
        this.src = src;
    }


    /**
     * method to take a Card's src and display on button
     * @param button the button to display the image on
     * @param card the card to be displayed
     */
    public static void setImage(Button button, Card card){
        Image img = new Image(card.getSrc());
        ImageView view = new ImageView(img);
        view.setFitHeight(button.getPrefHeight());
        view.setFitWidth(button.getPrefWidth());
        button.setGraphic(view);
    }


    /**
     * Gets the suit of a card
     * @return String
     */
    public String getSuit() {return this.suit;}


    /**
     * Gets the rank of a card
     * @return int
     */
    public int getRank() {return this.rank;}


    /**
     * Gets the label of the card
     * @return String
     */
    public String getLabel() {return this.label;}


    /**
     * Gets the image file of the card
     * @return String
     */
    public String getSrc(){
        return this.src;
    }


    /**
     * Sets the suit of the card
     * @param suit String
     */
    public void setSuit(String suit){
        this.suit = suit;
    }


    /**
     * Sets the rank of the card
     * @param rank int
     */
    public void setRank(int rank){
        this.rank = rank;
    }


    /**
     * Sets the label of the card
     * @param label String
     */
    public void setLabel(String label){
        this.label = label;
    }


    /**
     * Prints information about a card
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s%s%n%s%s%n%s%s%n",
                "Suit: ", this.suit,
                "Rank: ", this.rank,
                "label: ", this.label);
    }
}
