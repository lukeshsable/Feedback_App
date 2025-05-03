package model;

public class Question {
    private int id;
    private String questiontext;

    // Constructors
    public Question() {
    }

    public Question(int id, String questiontext) {
        this.id = id;
        this.questiontext = questiontext;
    }

    public Question(String questionText) {
        this.questiontext = questionText;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questiontext;
    }

    public void setQuestionText(String questionText) {
        this.questiontext = questionText;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questiontext + '\'' +
                '}';
    }
}
