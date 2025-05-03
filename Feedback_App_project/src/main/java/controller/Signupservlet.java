package controller;

import model.User;
import dao.UserDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Signupservlet")
public class Signupservlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role"); 
        
        // Create user object
        User user = new User(name, email, password, role);

        // DAO
        UserDAO dao = new UserDAO();

        // Already registered?
        if (dao.isUserExists(email)) {
        	
        	// response.sendRedirect("index.html");
        	response.sendRedirect("index.html?error=alreadyRegistered");
        	
        } else {
            boolean inserted = dao.registerUser(user);

            if (inserted) {
            	response.sendRedirect("index.html?success=registered");
                }
            else {
                response.getWriter().println("<h3>Error in registration. Try again.</h3>");
            }
        }
    }
}


