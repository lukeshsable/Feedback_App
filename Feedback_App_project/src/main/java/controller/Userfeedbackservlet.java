package controller;

import dao.QuestionDAO;
import dao.RatingsDAO;
import model.Question;
import model.Ratings;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/Userfeedbackservlet")
public class Userfeedbackservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Show questions with rating options
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = questionDAO.getAllQuestions();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>Feedback</title>");
        out.println("<style>");
        out.println("*{font-family: 'Poppins', sans-serif; box-sizing: border-box;}");
        out.println("body{background: linear-gradient(135deg, #b18cf2, #7d2ae8); padding: 40px;}");
        out.println(".feedback-container{background:white; padding:30px; border-radius:16px; max-width:800px; margin:auto;}");
        out.println("h2{text-align:center; color:#7d2ae8;}");
        out.println("form{margin-top:20px;}");
        out.println(".question{margin-bottom:20px;}");
        out.println("label{font-weight:bold; display:block; margin-bottom:8px;}");
        out.println(".ratings input{margin-right:6px;}");
        out.println("input[type=submit]{padding:10px 20px; background:#7d2ae8; color:white; border:none; border-radius:8px; cursor:pointer;}");
        out.println("</style></head><body>");
        out.println("<div class='feedback-container'>");
        out.println("<h2>Provide Your Feedback</h2>");
        out.println("<form method='post' action='Userfeedbackservlet'>");

        for (Question question : questions) {
            out.println("<div class='question'>");
            out.println("<label>" + question.getQuestionText() + "</label>");
            out.println("<div class='ratings'>");
            for (int i = 1; i <= 5; i++) {
                out.println("<input type='radio' name='rating_" + question.getId() + "' value='" + i + "' required/>" + i);
            }
            out.println("</div></div>");
        }

        out.println("<div style='text-align:center'><input type='submit' value='Submit Feedback'/></div>");
        out.println("</form></div></body></html>");
    }

    // Handle submission of ratings
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("userId"); 
	
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = questionDAO.getAllQuestions();
        RatingsDAO ratingsDAO = new RatingsDAO();

        for (Question question : questions) {
            String param = "rating_" + question.getId();
            String ratingStr = request.getParameter(param);

            if (ratingStr != null) {
                int rating = Integer.parseInt(ratingStr);
                Ratings feedback = new Ratings();
                feedback.setQuestionId(question.getId());
                feedback.setRating(rating);
                feedback.setUserId(userId);
                ratingsDAO.saveFeedback(feedback);
                
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><title>Thank You</title>");
        out.println("<style>");
        out.println("body { font-family: 'Poppins'; background: #f3f3ff; text-align: center; padding: 50px; }");
        out.println("h2 { color: #7d2ae8; }");
        out.println(".btn { margin-top: 20px; padding: 10px 20px; background-color: #7d2ae8; color: white; border: none; border-radius: 5px; text-decoration: none; font-size: 16px; }");
        out.println(".btn:hover { background-color: #5e22b8; }");
        out.println("</style></head><body>");
        out.println("<h2>Thank you for your feedback!</h2>");
        out.println("<a href='index.html' class='btn'>Go to Home</a>");
        out.println("</body></html>");

    }
}
