package ecommerce.servlet;

import java.io.IOException;
import ecommerce.dao.UserDAO;
import ecommerce.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(email);
        u.setFullName(fullName);
        try {
            UserDAO dao = new UserDAO();
            boolean success = dao.registerUser(u);
            if (success) {
                response.sendRedirect("views/login.jsp?registered=true");
            } else {
                request.setAttribute("error", "Registration failed");
                request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            request.setAttribute("error", "Username or Email already exists!");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        }
    }
}