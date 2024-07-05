import java.io.Serializable;

class QuizCard implements Serializable {

    static final String extension = "card";

    private String question;
    private String answer;

    QuizCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    
}