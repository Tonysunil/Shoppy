package ecommerce.servlet;

import java.io.IOException;
import java.util.List;
import ecommerce.dao.CartDAO;
import ecommerce.model.CartItem;
import ecommerce.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("views/login.jsp");
            return;
        }
        try {
            CartDAO dao = new CartDAO();
            List<CartItem> items = dao.getCartItems(user.getUserId());
            request.setAttribute("cartItems", items);
            request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}