package com.flashcards;

import com.flashcards.views.GUI;

/**
 * Flash Cards App.
 * 
 * <p>
 * App to play Flash Cards. Cards with question in front, answer in back.
 * </p>
 * 
 * <p>
 * This app can also create, edit, save and open cards.
 * </p>
 * 
 * @author Vítor Menezes Oliveira
 * @version 1.0
 */
public class App {

    public static void main(String args[]) {
        new App();
    }

    /**
     * Initializes the App.
     * 
     * @see {@link com.flashcards.views.GUI}
     */
    App() {
        new GUI();
    }

}