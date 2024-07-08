import java.io.Serializable;

class QuizCard implements Serializable {

    static final long serialVersionUID = 1L;

    static final String extension = "card";

    private String question;
    private String answer;

    QuizCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    String getQuestion() {
        return question;
    }

    void setQuestion(String question) {
        this.question = question;
    }

    String getAnswer() {
        return answer;
    }

    void setAnswer(String answer) {
        this.answer = answer;
    }
    
}