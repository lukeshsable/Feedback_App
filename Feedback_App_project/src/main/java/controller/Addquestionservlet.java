package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QuestionDAO;
import model.Question;

@WebServlet("/Addquestionservlet")
public class Addquestionservlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String questionText = request.getParameter("questionText");

	    Question question = new Question();
	    question.setQuestionText(questionText);

	    QuestionDAO questionDAO = new QuestionDAO();
	    boolean isAdded = questionDAO.addQuestion(question);

	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    out.println("<!DOCTYPE html>");
	    out.println("<html lang='en'>");
	    out.println("<head>");
	    out.println("<meta charset='UTF-8'>");
	    out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
	    out.println("<title>Question Status</title>");
	    out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap' rel='stylesheet'>");
	    out.println("<style>");
	    out.println("* { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }");
	    out.println("body { background: linear-gradient(135deg, #b18cf2, #a174f0, #7d2ae8); min-height: 100vh; padding: 60px 20px; }");
	    out.println(".status-container { max-width: 600px; margin: auto; background: white; border-radius: 16px; padding: 30px; text-align: center; box-shadow: 0 0 20px rgba(0,0,0,0.1); }");
	    out.println("h2 { color: #7d2ae8; margin-bottom: 20px; }");
	    out.println("form { display: inline-block; margin: 10px; }");
	    out.println("input[type=submit] { background-color: #7d2ae8; color: white; padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; transition: background 0.3s ease; }");
	    out.println("input[type=submit]:hover { background-color: #6c1be0; }");
	    out.println("</style>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<div class='status-container'>");

	    if (isAdded) {
	        out.println("<h2>Question added successfully!</h2>");
	    } else {
	        out.println("<h2>Failed to add question!</h2>");
	    }

	    // Back to Dashboard
	    out.println("<form action='Loginservlet' method='get'>");
	    out.println("<input type='submit' value='Back to Dashboard'/>");
	    out.println("</form>");

	    // Go to Home
	    out.println("<form action='index.html' method='get'>");
	    out.println("<input type='submit' value='Go to Home'/>");
	    out.println("</form>");

	    out.println("</div>");
	    out.println("</body></html>");

	}


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show form to add question
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Add Question</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap' rel='stylesheet'>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }");
        out.println("body { background: linear-gradient(135deg, #b18cf2, #a174f0, #7d2ae8); min-height: 100vh; padding: 60px 20px; }");
        out.println(".add-container { max-width: 600px; margin: auto; background: white; border-radius: 16px; padding: 30px; box-shadow: 0 0 20px rgba(0,0,0,0.1); }");
        out.println("h2 { color: #7d2ae8; text-align: center; margin-bottom: 20px; }");
        out.println("form { display: flex; flex-direction: column; gap: 15px; }");
        out.println("input[type=text] { padding: 10px; border: 1px solid #ccc; border-radius: 8px; }");
        out.println("input[type=submit] { background-color: #7d2ae8; color: white; padding: 10px; border: none; border-radius: 8px; cursor: pointer; transition: background 0.3s ease; }");
        out.println("input[type=submit]:hover { background-color: #6c1be0; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='add-container'>");
        out.println("<h2>Add New Question</h2>");
        out.println("<form method='post' action='Addquestionservlet'>");
        out.println("<input type='text' name='questionText' placeholder='Enter your question here' required/>");
        out.println("<input type='submit' value='Add Question'/>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body></html>");

    }
}
