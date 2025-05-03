package model;

public class Ratings {
    private int id;
    private int questionId;
    private int rating;
    private int userId;

    // Constructors
    public Ratings() {}

    public Ratings(int id, int questionId, int rating, int userId) {
    	this.id=id;
        this.questionId = questionId;
        this.rating = rating;
        this.userId = userId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
