package com.flashcards;

import com.flashcards.models.*;
import com.flashcards.views.*;
import com.flashcards.controllers.*;

/**
 * Quiz Card App.
 * App to play Quiz Cards. Cards with question in front, answer in back.
 * This app can also create, edit, save and open cards.
 * 
 * @author Vítor Menezes Oliveira
 * @version 1.0
 * @see {@link Card}
 * @see {@link GUI}
 * @see {@link Behavior}
 * @see {@link Utils}
 */
public class App {

    public static Card currentCard = new Card();
    public static final String CARDS_FOLDER = "./cards";


    
    public static void main(String args[]) {
        new App();
    }



    /**
     * Initialize the App.
     * Add behaviors and shows GUI.
     * 
     * @see {@link Behavior}
     * @see {@link GUI}
     */
    App() {
        new Behavior();
        new GUI();
    }

}