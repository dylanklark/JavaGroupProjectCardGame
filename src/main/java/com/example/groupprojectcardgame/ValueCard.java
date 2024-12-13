package com.example.groupprojectcardgame;


/**
 * Represents a value card ie- 1 heart, 2 heart, etc.
 */
public class ValueCard extends Card {


    /**
     * Initializes a valueCard
     * @param suit String: the card's suit
     * @param rank int: the card's rank
     * @param label String: the card's label
     * @param src String: the card's image file
     */
    public ValueCard(String suit, int rank, String label, String src) {
        super(suit, rank, label, src);
    }
}
