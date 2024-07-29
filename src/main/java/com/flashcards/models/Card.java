package com.flashcards.models;

import java.io.File;

/**
 * The Card used in <code>Flash Cards App</code>.
 * 
 * <p>
 * Used to save question and answer.
 * </p>
 * 
 * @see {@link com.flashcards.App}
 */
public class Card {

    public static final String EXTENSION = "card";

    private File file;

    private String question;
    private String answer;

    /**
     * Creates a card with empty question, answer and file.
     */
    public Card() {
        setFile(null);
        setQuestion("");
        setAnswer("");
    }

    /**
     * Creates a card with a question, answer and file.
     * 
     * @param file     the file
     * @param question the question
     * @param answer   the answer
     */
    public Card(File file, String question, String answer) {
        setFile(file);
        setQuestion(question);
        setAnswer(answer);
    }

    /**
     * Gets card question text
     * 
     * @return the question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets card question text
     * 
     * @param question the question text to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets card answer text
     * 
     * @return the answer text
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets card answer text
     * 
     * @param answer the answer text to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets card file reference
     * 
     * @return the card file reference
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets card file reference
     * 
     * @param file the file reference to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets card file name
     * 
     * @return the Card file name
     */
    @Override
    public String toString() {
        return Model.getFileName(file);
    }

}