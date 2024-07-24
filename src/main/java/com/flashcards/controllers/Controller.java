package com.flashcards.controllers;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.flashcards.models.Card;
import com.flashcards.models.Model;
import com.flashcards.models.Utils;

/**
 * This class controll card actions, like save and open a card.
 * 
 * @see {@link com.flashcards.models.Model}
 */
public class Controller {

    // Quiz Card (*.card) file filter
    private FileFilter cardFileFilter = new FileFilter() {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = Utils.getExtension(f).toLowerCase();
            if (extension != null) {
                if (extension.equals(Card.EXTENSION.toLowerCase())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        @Override
        public String getDescription() {
            return "Quiz card (*.card)";
        }

    };

    /**
     * Opens file chooser to select a file and get the <code>Card</code> object.
     * 
     * @param component the parent component to show the file chooser dialog.
     * Can be <code>null</code>
     * @return the card deserialized from selected file
     * @see {@link com.flashcards.models.Card}
     */
    public Card getCardFromFile(Component component) {

        // File chooser
        JFileChooser fileChooser = new JFileChooser(Utils.touchDirectory(Model.CARDS_FOLDER));
        fileChooser.setFileFilter(cardFileFilter);
        if (fileChooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {

            // Get Card object from file
            Card card = Model.deserializeCard(fileChooser.getSelectedFile());

            // Return card
            return card;
        }
        return null;
    }

    /**
     * Opens file chooser to select a local
     * and a name to save the <code>Card</code>.
     * 
     * @param card the card to save
     * @param component the parent component to show the file chooser dialog.
     * Can be <code>null</code>
     * @see {@link com.flashcards.models.Card}
     */
    public void saveCard(Card card, Component component) {

        // File chooser
        JFileChooser fileChooser = new JFileChooser(Utils.touchDirectory(Model.CARDS_FOLDER));
        fileChooser.setFileFilter(cardFileFilter);
        if (fileChooser.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {

            // Set name and location to card
            String filePath = fileChooser.getSelectedFile().getPath();
            card = new Card(filePath + "." + Card.EXTENSION,
                    card.getQuestion(),
                    card.getAnswer());

            // Save card
            Model.serializeCard(card);
        }
    }

}