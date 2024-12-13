package com.example.groupprojectcardgame;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a player's hand, extends Deck
 */
public class Hand extends Deck {
    private ArrayList<Card> cards; // The cards in the player's hand


    /**
     * Initializes a Hand
     * @param cards the cards currently in a player's hand
     */
    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }


    /**
     * Evalutates a player's hand, unused as testHand was added to GameScreenController
     * @return Map<String, Object>
     */
    public Map<String, Object> evaluateHand() {
        Map<Integer, Integer> valueCounts = new HashMap<>();
        Map<String, Integer> suitCounts = new HashMap<>();

        for (Card card : cards) {
            int value = card.getRank();
            String suit = card.getSuit();

            valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            suitCounts.put(suit, suitCounts.getOrDefault(suit, 0) + 1);
        }

        Map<String, Object> evaluation = new HashMap<>();
        evaluation.put("valueCounts", valueCounts);
        evaluation.put("suitCounts", suitCounts);

        return evaluation;
    }
}

