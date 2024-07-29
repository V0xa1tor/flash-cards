package com.flashcards.controllers;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.flashcards.models.Card;
import com.flashcards.models.Model;

/**
 * This class controll card actions, like save and open a card.
 * 
 * @see {@link com.flashcards.models.Model}
 */
public class Controller {

    // Flash Card (*.card) file filter
    private static FileFilter cardFileFilter = new FileFilter() {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = Model.getFileExtension(f).toLowerCase();
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
            return "Flash card (*.card)";
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
    public static Card openCard(Component component) {

        // File chooser
        JFileChooser fileChooser = new JFileChooser(Model.touchDirectory(Model.CARDS_FOLDER));
        fileChooser.setFileFilter(cardFileFilter);
        if (fileChooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {

            // Get Card object from file
            Card card = Model.getCard(fileChooser.getSelectedFile());

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
    public static void saveCard(Card card, Component component) {

        // File chooser
        JFileChooser fileChooser = new JFileChooser(Model.touchDirectory(Model.CARDS_FOLDER));
        fileChooser.setFileFilter(cardFileFilter);
        if (fileChooser.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {

            // Get card name and location
            String cardPath;
            String fileFolder = fileChooser.getCurrentDirectory().toString();
            String fileName = fileChooser.getSelectedFile().getName();
            if (Card.EXTENSION.equals(Model.getFileExtension(fileName))) {
                cardPath = fileFolder + "/" + fileName;
            } else {
                cardPath = fileFolder + "/" + fileName + "." + Card.EXTENSION;
            }
            
            // The card
            card = new Card(new File(cardPath), card.getQuestion(), card.getAnswer());

            // Save card
            Model.setCard(card, card.getFile());
        }
    }

    /**
     * Deletes a card
     * 
     * @param card the card to delete
     */
    public static void deleteCard(Card card) {
        card.getFile().delete();
    }
    
    /**
     * Renames a card
     * 
     * @param card the card to rename
     * @param name the new name
     */
    public static void renameCard(Card card, String name) {

        // Save new card
        Model.setCard(card, new File(card.getFile().getParentFile(), name));

        // Delete old card
        deleteCard(card);
    }
}
