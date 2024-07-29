package com.flashcards.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Properties;

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
     * Reads card object from a file.
     * 
     * @param file the file to get card
     * @return the card
     */
    public static Card getCard(File file) {

        // Card
        Properties properties = null;
        String question = "";
        String answer = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            FileField field = null;
            String line = "";
            while ((line = reader.readLine()) != null) {
                
                if (field != null) {
                    switch (field) {

                        // Properties
                        case FileField.PROPERTIES:
                            if (!line.equals(FileField.PROPERTIES.separator)) {
                            } else { field = null; }
                            break;
                        
                        // Question
                        case FileField.QUESTION:
                            if (!line.equals(FileField.QUESTION.separator)) {
                                question += "\n" + line;
                            } else {
                                question = question.length() > 0 ?
                                        question.substring(1) : "";
                                field = null;
                            }
                            break;
                        
                        // Answer
                        case FileField.ANSWER:
                            if (!line.equals(FileField.ANSWER.separator)) {
                                answer += "\n" + line;
                            } else {
                                answer = answer.length() > 0 ?
                                        answer.substring(1) : "";
                                field = null;
                            }
                            break;
                    }

                // Enter a field
                } else {
                    if (line.equals(FileField.PROPERTIES.separator)) {
                        field = FileField.PROPERTIES;
                    } else if (line.equals(FileField.QUESTION.separator)) {
                        field = FileField.QUESTION;
                    }else if (line.equals(FileField.ANSWER.separator)) {
                        field = FileField.ANSWER;
                    }
                }
                
            }

            reader.close();

            // Return card
            if (field == null) {
                return new Card(file, question, answer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes card in a file.
     * 
     * @param card
     */
    public static void setCard(Card card, File file) {

        // Add Card extension (if doesn't have)
        if (!Card.EXTENSION.equalsIgnoreCase(getFileExtension(file))) {
            file = new File(file.getParentFile(), file.getName() + "." + Card.EXTENSION);
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            // Fields
            Properties properties = null;
            String question = card.getQuestion();
            String answer = card.getAnswer();

            // Properties
            if (properties != null) {
                writer.write(FileField.PROPERTIES.separator); writer.newLine();
                writer.write(FileField.PROPERTIES.separator); writer.newLine();
                writer.newLine();
            }
            
            // Question
            writer.write(FileField.QUESTION.separator); writer.newLine();
            writer.write(question.length() > 0 ? question + "\n" : "");
            writer.write(FileField.QUESTION.separator); writer.newLine();
            writer.newLine();
            
            // Answer
            writer.write(FileField.ANSWER.separator); writer.newLine();
            writer.write(answer.length() > 0 ? answer + "\n" : "");
            writer.write(FileField.ANSWER.separator); writer.newLine();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all cards in <code>cards folder</code>.
     * 
     * @return an array of the cards
     * @see {@link Card}
     * @see {@link #CARDS_FOLDER}
     */
    public static Card[] getCardsList() {
        ArrayList<Card> cards = new ArrayList<>();
        for (File f : touchDirectory(CARDS_FOLDER).listFiles()) {
            cards.add(getCard(f));
        }
        return cards.toArray(new Card[] {});
    }

    /**
     * Gets file name without extension.
     * 
     * @param fileName the name to get (without extension)
     * @return the file name (without extension)
     */
    public static String getFileName(String fileName) {
        String extension = getFileExtension(fileName);

        if (extension != null) {
            fileName = fileName.replaceAll("." + extension + "$", "");
        }

        return fileName;
    }

    /**
     * Gets file name without extension.
     * 
     * @param file the file to get name (without extension)
     * @return the file name (without extension)
     */
    public static String getFileName(File file) {
        return getFileName(file.getName());
    }

    /**
     * Gets files extension, based on file name.
     * 
     * @param fileName the file name
     * @return the extension (without '.')
     */
    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');

        if (i > 0 && i < fileName.length() - 1) {
            String extension = fileName.substring(i + 1).toLowerCase();
            return extension;
        }
        return null;
    }

    /**
     * Gets file extension, based on file.
     * 
     * @param file the file
     * @return the extension (without '.')
     * @see {@link File}
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
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

    /**
     * Makes specified directory, if it doesn't exists.
     * If it already exists, do nothing.
     * 
     * @param directory the directory to make
     * @return the directory as a {@link File} object
     */
    public static File touchDirectory(File directory) {
        directory.mkdir();
        return directory;
    }
}
