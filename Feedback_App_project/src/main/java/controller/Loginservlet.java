package controller;

import dao.UserDAO;
import dao.QuestionDAO;
import model.Question;
import model.User;
import dao.RatingsDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/Loginservlet")
public class Loginservlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        

        String action = request.getParameter("action");
        QuestionDAO questionDAO = new QuestionDAO();

        if ("edit".equals(action)) {
            int questionId = Integer.parseInt(request.getParameter("id"));
            Question question = questionDAO.getQuestionById(questionId);

            if (question != null) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Edit Question</title>");
                out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap' rel='stylesheet'>");
                out.println("<style>");
                out.println("* { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }");
                out.println("body { background: linear-gradient(135deg, #b18cf2, #a174f0, #7d2ae8); min-height: 100vh; padding: 60px 20px; }");
                out.println(".edit-container { max-width: 600px; margin: auto; background: white; border-radius: 16px; padding: 30px; box-shadow: 0 0 20px rgba(0,0,0,0.1); }");
                out.println("h2 { color: #7d2ae8; text-align: center; margin-bottom: 20px; }");
                out.println("form { display: flex; flex-direction: column; gap: 15px; }");
                out.println("input[type=text] { padding: 10px; border: 1px solid #ccc; border-radius: 8px; }");
                out.println("input[type=submit] { background-color: #7d2ae8; color: white; padding: 10px; border: none; border-radius: 8px; cursor: pointer; transition: background 0.3s ease; }");
                out.println("input[type=submit]:hover { background-color: #6c1be0; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='edit-container'>");
                out.println("<h2>Edit Question</h2>");
                out.println("<form method='post' action='Loginservlet'>");
                out.println("<input type='hidden' name='action' value='update'/>");
                out.println("<input type='hidden' name='id' value='" + question.getId() + "'/>");
                out.println("<input type='text' name='questionText' value='" + question.getQuestionText() + "' required/>");
                out.println("<input type='submit' value='Update Question'/>");
                out.println("</form>");
                out.println("</div>");
                out.println("</body></html>");
            } else {
                out.println("<h2>Question not found!</h2>");
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String updatedText = request.getParameter("questionText");

            Question updatedQuestion = new Question();
            updatedQuestion.setId(id);
            updatedQuestion.setQuestionText(updatedText);

            boolean isUpdated = questionDAO.updateQuestion(updatedQuestion);

            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("admin") != null) {
                User user = (User) session.getAttribute("admin");
                out.println("<h3>" + (isUpdated ? "Question updated successfully!" : "Failed to update question.") + "</h3>");
                showDashboard(request, response, user);
            } else {
                response.sendRedirect("index.html?error=sessionExpired");
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean isDeleted = questionDAO.deleteQuestion(id);

            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("admin") != null) {
                User user = (User) session.getAttribute("admin");
                out.println("<h3>" + (isDeleted ? "Question deleted successfully!" : "Failed to delete question.") + "</h3>");
                showDashboard(request, response, user);
            } else {
                response.sendRedirect("index.html?error=sessionExpired");
            }

        } else {
        	String email = request.getParameter("email");
        	String password = request.getParameter("password");

        	UserDAO userDAO = new UserDAO();
        	User user = userDAO.validateAdmin(email, password);

        	HttpSession session = request.getSession();

        	if (user != null && "admin".equalsIgnoreCase(user.getRole())) {
        	    // Admin login
        	    session.setAttribute("admin", user);
        	    showDashboard(request, response, user);
        	} else {
        	    // Check for regular user
        		RatingsDAO ratingsDAO = new RatingsDAO();
        	    int userId = ratingsDAO.validateUser(email, password);
        	    
        	    if (userId != -1) {
        	        // Valid user
        	        session.setAttribute("userId", userId);
        	        response.sendRedirect("Userfeedbackservlet");
        	    } else {
        	        // Invalid credentials
        	        request.setAttribute("error", "Invalid email or password");
        	        request.getRequestDispatcher("index.html").forward(request, response);
        	    }
        	}

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            User user = (User) session.getAttribute("admin");
            showDashboard(request, response, user);
        } else {
            response.sendRedirect("index.html?error=sessionExpired");
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        QuestionDAO questionDAO = new QuestionDAO();
        RatingsDAO ratingsDAO = new RatingsDAO();
        List<Question> questions = questionDAO.getAllQuestions();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Admin Dashboard</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap' rel='stylesheet'>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }");
        out.println("body { background: linear-gradient(135deg, #b18cf2, #a174f0, #7d2ae8); min-height: 100vh; padding: 60px 20px; }");
        out.println(".dashboard-container { max-width: 1000px; margin: auto; background: white; border-radius: 16px; padding: 30px; box-shadow: 0 0 20px rgba(0,0,0,0.1); }");
        out.println("h2, h3 { color: #7d2ae8; text-align: center; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("table, th, td { border: 1px solid #ccc; }");
        out.println("th, td { padding: 12px; text-align: center; }");
        out.println("th { background-color: #7d2ae8; color: white; }");
        out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
        out.println("tr:hover { background-color: #f1f1f1; }");
        out.println("input[type=submit] { background-color: #7d2ae8; color: white; padding: 8px 16px; border: none; border-radius: 6px; cursor: pointer; margin: 2px; transition: background 0.3s ease; }");
        out.println("input[type=submit]:hover { background-color: #6c1be0; }");
        out.println("form { display: inline; }");
        out.println(".button-row { display: flex; justify-content: center; gap: 15px; margin-top: 20px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='dashboard-container'>");
        out.println("<h2>Welcome, Admin: " + user.getName() + "</h2>");
        out.println("<h3>Feedback Questions</h3>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Question</th><th>Actions</th></tr>");

        for (Question q : questions) {
            double avgRating = ratingsDAO.getAverageRatingByQuestionId(q.getId());
            out.println("<tr>");
            out.println("<td>" + q.getId() + "</td>");
            out.println("<td>" + q.getQuestionText() + "<br><small>Avg. Rating: " + String.format("%.2f", avgRating) + "</small></td>");
            out.println("<td>");
            out.println("<form action='Loginservlet' method='post'>");
            out.println("<input type='hidden' name='action' value='edit'/>");
            out.println("<input type='hidden' name='id' value='" + q.getId() + "'/>");
            out.println("<input type='submit' value='Edit'/>");
            out.println("</form>");
            out.println("<form action='Loginservlet' method='post'>");
            out.println("<input type='hidden' name='action' value='delete'/>");
            out.println("<input type='hidden' name='id' value='" + q.getId() + "'/>");
            out.println("<input type='submit' value='Delete' onclick='return confirm(\"Are you sure?\")'/>");
            out.println("</form>");
            out.println("</td></tr>");
        }

        out.println("</table>");
        out.println("<div class='button-row'>");
        out.println("<form action='Addquestionservlet' method='get'>");
        out.println("<input type='submit' value='Add New Question'/>");
        out.println("</form>");
        out.println("<form action='index.html' method='get'>");
        out.println("<input type='submit' value='Go to Home'/>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
    }
}
