package ecommerce.servlet;

import java.io.IOException;
import ecommerce.dao.CartDAO;
import ecommerce.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("views/login.jsp");
            return;
        }
        int productId = Integer.parseInt(request.getParameter("productId"));
        try {
            CartDAO dao = new CartDAO();
            dao.addOrUpdateCart(user.getUserId(), productId, 1);
            String referer = request.getHeader("Referer");
            response.sendRedirect(referer != null ? referer : "home");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}