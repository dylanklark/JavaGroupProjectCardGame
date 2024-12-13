package com.example.groupprojectcardgame;

import javafx.scene.text.Text;

/**
 * Represents a player
 */
public class Player {
    private String name;
    private double health;
    private Text playerHealthText;


    /**
     * Initializes a Player
     * @param name String: the player's name
     * @param health double: the player's health
     */
    Player(String name, double health){
        this.name = name;
        this.health = health;
        this.playerHealthText = new Text();
        this.playerHealthText.setText(String.valueOf(health));
    }

    /**
     * Gets the player's name
     * @return String
     */
    public String getName() {return this.name;}


    /**
     * Gets the player's health
     * @return double
     */
    public double getHealthPoints() {return this.health;}


    /**
     * Sets the player's name
     * @param name String
     */
    public void setName(String name) {this.name = name;}


    /**
     * Updates the health text for when damage is taken
     */
    public void updateHealthText() {
        playerHealthText.setText(String.valueOf(health));
    }


    /**
     * Gets the player health text
     * @return Text
     */
    public Text getPlayerHealthText() {return this.playerHealthText;}


    /**
     * Sets the player's health
     * @param health double
     */
    public void setHealth(double health) {this.health = health;}


    /**
     * Prints general player information
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s%s%n%s%s",
                "Player name: ", this.name,
                "Current health: ", this.health);
    }
}
