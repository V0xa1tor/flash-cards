import java.io.File;

/**
 * The Card used in {@link App}. Used to save question and answer (serialized).
 * 
 * @author Vítor Menezes Oliveira
 */
class Card extends File {

    static final long serialVersionUID = 1L;

    static final String EXTENSION = "card";

    private String question;
    private String answer;



    /**
     * Creates a card with a pathname, question and answer texts.
     * 
     * @param pathname the pathname of the card
     * @param question the question text
     * @param answer the answer text
     */
    public Card(String pathname, String question, String answer) {
        super(pathname);
        setQuestion(question);
        setAnswer(answer);
    }

    /**
     * Creates a card with a question and answer texts.
     * Also sets the name to "card.card".
     * 
     * @param question the question text
     * @param answer the answer text
     */
    public Card(String question, String answer) {
        this("card." + EXTENSION, question, answer);
    }

    /**
     * Creates a card with empty question and answer.
     * Also sets the name to "card.card".
     */
    public Card() {
        this("", "");
    }

    

    /**
     * Gets card question text
     * 
     * @return the question text
     */
    String getQuestion() {
        return question;
    }

    /**
     * Sets card question text
     * 
     * @param question the question text to set
     */
    void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets card answer text
     * 
     * @return the answer text
     */
    String getAnswer() {
        return answer;
    }

    /**
     * Sets card answer text
     * 
     * @param answer the answer text to set
     */
    void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Get card file name (without extension)
     * 
     * @return the Card file name
     */
    @Override
    public String toString() {
        String name = this.getName();
        int i = name.lastIndexOf('.');
        name = name.substring(0, i);
        return name;
    }
    
}