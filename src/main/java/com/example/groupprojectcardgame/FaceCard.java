package com.example.groupprojectcardgame;

/**
 * Represents a face card ie- King, Queen, etc.
 */
public class FaceCard extends Card{

    /**
     * Initializes a FaceCard (does not decline) ;) )
     * @param suit String: the card's suit
     * @param rank int: the card's rank
     * @param label String: the card's label
     * @param src String: the card's image dile
     */
    public FaceCard(String suit, int rank, String label, String src) {
        super(suit, rank, label, src);
    }
}
