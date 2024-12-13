package com.example.groupprojectcardgame;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class Action {

    public double calculateDamage(ArrayList<Card> cards) {
        Map<Integer, Integer> valueCounts = new HashMap<>();
        Map<String, Integer> suitCounts = new HashMap<>();

        for (Card card : cards) {
            int value = card.getRank();
            String suit = card.getSuit();

            valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            suitCounts.put(suit, suitCounts.getOrDefault(suit, 0) + 1);
        }

        double damage = 0;

        // Check for pairs, trios, and runs
        for (Map.Entry<Integer, Integer> entry : valueCounts.entrySet()) {
            int value = entry.getKey();
            int count = entry.getValue();

            if (count == 2) {
                damage += getPairDamage(value, cards);
            } else if (count == 3) {
                damage += getTrioDamage(value, cards);
            } else if (count == 4) {
                damage += getFourOfAKindDamage(value, cards);
            }
        }

        // Check for runs
        damage += getRunDamage(valueCounts, cards);
        return damage;
    }


    private double getPairDamage(int value, ArrayList<Card> cards) {
        double damage = 0;
        int pairCount = 0;
        String suit = "";

        for (Card card : cards) {
            if (card.getRank() == value) {
                pairCount++;
                if (suit.isEmpty()) {
                    suit = card.getSuit();
                } else if (!suit.equals(card.getSuit())) {
                    suit = "mixed";
                }
            }
        }

        if (pairCount == 2) {
            if (suit.equals("mixed")) {
                damage = value * 2 * 1.5;
            } else {
                damage = value * 2 * 1.8;
            }
        }

        return damage;
    }


    private double getTrioDamage(int value, ArrayList<Card> cards) {
        double damage = 0;
        int trioCount = 0;
        String suit = "";

        for (Card card : cards) {
            if (card.getRank() == value) {
                trioCount++;
                if (suit.isEmpty()) {
                    suit = card.getSuit();
                } else if (!suit.equals(card.getSuit())) {
                    suit = "mixed";
                }
            }
        }

        if (trioCount == 3) {
            if (suit.equals("mixed")) {
                damage = value * 3 * 1.8;
            } else {
                damage = value * 3 * 2.0;
            }
        }

        return damage;
    }


    private double getFourOfAKindDamage(int value, ArrayList<Card> cards) {
        double damage = 0;
        int fourOfAKindCount = 0;

        for (Card card : cards) {
            if (card.getRank() == value) {
                fourOfAKindCount++;
            }
        }

        if (fourOfAKindCount == 4) {
            damage = value * 4 * 2.5;
        }

        return damage;
    }


    private double getRunDamage(Map<Integer, Integer> valueCounts, ArrayList<Card> cards) {
        double damage = 0;
        int runCount = 0;
        int runValue = 0;
        int previousValue = -1; // Start with an invalid value for comparison

        // Iterate through sorted card values
        for (int value : valueCounts.keySet().stream().sorted().toList()) {
            if (valueCounts.get(value) > 0 && value == previousValue + 1) { // Part of the run
                runCount++;
                runValue += value;
            } else { // Run breaks
                // Calculate damage for the current run
                if (runCount >= 3) {
                    damage += calculateRunBonus(runCount, runValue);
                }
                // Reset the run counters
                runCount = 1; // Current card starts a new potential run
                runValue = value;
            }
            previousValue = value;
        }

        // Final run calculation if the loop ends on a run
        if (runCount >= 3) {
            damage += calculateRunBonus(runCount, runValue);
        }

        return damage;
    }

    // Helper function to calculate damage based on run size and value
    private double calculateRunBonus(int runCount, int runValue) {
        if (runCount == 3) {
            return runValue * 1.5;
        } else if (runCount == 4) {
            return runValue * 1.8;
        } else if (runCount == 5) {
            return runValue * 2.0;
        }
        return 0; // No bonus for other run sizes
    }
}

