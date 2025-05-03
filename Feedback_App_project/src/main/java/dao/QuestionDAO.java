package dao;

import model.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/feedbackdb"; 
    private final String jdbcUsername = "root"; 
    private final String jdbcPassword = "Archer@123"; 

    // SQL Queries
    private static final String SELECT_ALL = "SELECT * FROM questions";
    private static final String ADD_NEW_QUESTION = "INSERT INTO questions (question_text) VALUES (?)";
    private static final String DELETE_BY_ID = "DELETE FROM questions WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM questions WHERE id = ?";
    private static final String UPDATE_BY_ID = "UPDATE questions SET question_text = ? WHERE id = ?";

    // DB Connection
    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // List all feedback questions
    public List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setQuestionText(rs.getString("question_text"));
                list.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete question by ID
    public boolean deleteQuestion(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_BY_ID)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to add a new question
    public boolean addQuestion(Question question) {
       
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_QUESTION)) {
            
            preparedStatement.setString(1, question.getQuestionText());
            int result = preparedStatement.executeUpdate();
            
            return result > 0;  // Returns true if question is successfully added
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Returns false if an error occurs
        }
    }

    // Get question by ID (for editing)
    public Question getQuestionById(int id) {
        Question question = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                question = new Question();
                question.setId(rs.getInt("id"));
                question.setQuestionText(rs.getString("question_text"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return question;
    }

    // Update question text by ID
    public boolean updateQuestion(Question question) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BY_ID)) {

            ps.setString(1, question.getQuestionText());
            ps.setInt(2, question.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
