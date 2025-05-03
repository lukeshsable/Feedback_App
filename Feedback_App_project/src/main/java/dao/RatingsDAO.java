package dao;

import model.Ratings;

import java.sql.*;

public class RatingsDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/feedbackdb";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "Archer@123";

    private static final String INSERT_FEEDBACK = "INSERT INTO feedback (question_id, ratings, user_id) VALUES (?, ?, ?)";
    private static final String RATINGS_BY_ID = "SELECT AVG(ratings) FROM feedback WHERE question_id = ?";

    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public double getAverageRatingByQuestionId(int questionId) {
        double avgRating = 0.0;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(RATINGS_BY_ID)) {

            ps.setInt(1,questionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                avgRating = rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return avgRating;
    }

    public boolean saveFeedback(Ratings feedback) {
        boolean status = false;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_FEEDBACK)) {

        	ps.setInt(1, feedback.getQuestionId());
            ps.setInt(2, feedback.getRating());
            ps.setInt(3, feedback.getUserId());
            status = ps.executeUpdate() > 0;

            System.out.println("Feedback inserted? " + status); // Debug log
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    
    public int validateUser(String email, String password) {
    	int userId = -1;
        String query = "SELECT id FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	userId=rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		return userId;

    }


}

