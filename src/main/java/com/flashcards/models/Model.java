package com.flashcards.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

/**
 * Model class to manage <code>Cards</code>.
 * 
 * <p>
 * This class implements low level operations,
 * like serialize and deserializeCards.
 * </p>
 * 
 * @see {@link Card}
 */
public class Model {

    // Default folder to save cards
    public static final String CARDS_FOLDER = "./cards";

    /**
     * Updates cards which is displaying in cards list.
     * Also creates cards directory, if it does not exists.
     */
    public static Card[] getCardsList() {
        File cardsDir = touchDirectory(CARDS_FOLDER);
        ArrayList<Card> cards = new ArrayList<>();
        for (File f : cardsDir.listFiles()) {
            cards.add(deserializeCard(f));
        }
        return cards.toArray(new Card[] {});
    }

    /**
     * Serialize <code>Card</code> object on file.
     * 
     * @param card the card to be serialized
     * @see {@link Card}
     */
    public static void serializeCard(Card card) {
        try {
            // Get file to save the card
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(card));

            // Save card
            oos.writeObject(card);
            oos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deserialize <code>Card</code> object from file.
     * 
     * @param file the file to get the Card object
     * @return the Card object
     * @see {@link Card}
     */
    public static Card deserializeCard(File file) {
        try {
            // Get card
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Card card = (Card) ois.readObject();
            ois.close();

            // Return card
            return card;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets files extension, based on file name.
     * 
     * @param fileName the file name
     * @return the extension
     */
    public static String getExtension(String fileName) {
        String ext = null;
        int i = fileName.lastIndexOf('.');

        if (i > 0 && i < fileName.length() - 1) {
            ext = fileName.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * Gets file extension, based on file.
     * 
     * @param f the file
     * @return the extension
     * @see {@link File}
     */
    public static String getExtension(File f) {
        return getExtension(f.getName());
    }

    /**
     * Makes specified directory, if it doesn't exists.
     * If it already exists, do nothing.
     * 
     * @param path the directory path
     * @return the directory as a {@link File} object
     */
    public static File touchDirectory(String path) {
        File directory = new File(path);
        directory.mkdir();
        return directory;
    }
}
