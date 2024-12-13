package com.example.groupprojectcardgame;

import java.io.File;
import java.util.*;


/**
 * Represents a standard 52 deck of playing cards
 */
public class Deck {
    private ArrayList<Card> cardList = new ArrayList<>(); // This is basically the whole deck of cards


    /**
     * Initializes a deck object, matches card names with an associated image and sets all ranks
     * and labels for the cards.
     */
    public Deck() { //ArrayList<Card> cardList
        String[] suitList = {"Heart", "Diamond", "Spade", "Club"};
        int[] rankList = {2, 3, 4, 5, 6, 7, 8, 9, 10};

        Map<String, Integer> faceList = Map.of(
                "Ace", 1, "Jack", 11, "Queen", 12, "King", 13);

        // Folder that holds all the image files
        File folder = new File("src/main/resources/com/example/groupprojectcardgame/images/Card Folder");

        // The base path of the image folder, allows to append which specific card is needed
        String rootPath = "com/example/groupprojectcardgame/images/Card Folder/";

        // Iterates through the card png folder to match each png with the associated card.
        for (String fileName : folder.list()) {

            // Matches faceCard: creates every possible permutation
            for (String face : faceList.keySet()) {
                if (fileName.startsWith(face)) {
                    for (String suit : suitList) {
                        if (fileName.contains(face + suit)) { // Example: "AceHeart"
                            String cardLabel = face + suit;
                            FaceCard card = new FaceCard(suit, faceList.get(face), cardLabel, rootPath + fileName);
                            cardList.add(card); // Add card to deck
                            break;
                        }
                    }
                }
            }

            // Matches valueCard: creates every possible permutation
            for (String suit : suitList) {
                for (int rank : rankList) {
                    String rString = String.valueOf(rank);
                    if (fileName.contains(rString + suit)) { // Example: "1Heart"
                        String cardLabel = rString + suit;
                        ValueCard card = new ValueCard(suit, rank, cardLabel, rootPath + fileName);
                        cardList.add(card); // Add card to deck
                        break;
                    }
                }
            }
        }
    }


    /**
     * Shuffles the deck
     */
    public void shuffle() {
        Collections.shuffle(cardList); //built-in method that shuffles arraylists
    }


    /**
     * Removes a card from the deck
     * @param card the card to be removed
     */
    public void removeCard(Card card) {cardList.remove(card);}


    /**
     * Adds a card to the deck
     * @param card the card to be added
     */
    public void addCard(Card card) {cardList.add(card);}


    /**
     * Draws a card from the deck
     * @return Card: the topmost card in the deck
     */
    public Card draw() {return cardList.removeLast();}


    /**
     * Returns the size of the deck
     * @return int
     */
    public int size() {return cardList.size();}


    /**
     * Returns the card associated with a button id
     * @param buttonId location of a card
     * @return Card
     */
    public Card getCard(String buttonId){
        for(Card card: cardList){
            if(buttonId.equals(card.getLabel())){
                return card;
            }
        }
        return null; //if no card is found
    }
}
