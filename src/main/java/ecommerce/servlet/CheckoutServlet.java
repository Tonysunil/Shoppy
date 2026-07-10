package ecommerce.servlet;

import java.io.IOException;
import java.util.List;
import ecommerce.dao.CartDAO;
import ecommerce.dao.OrderDAO;
import ecommerce.model.CartItem;
import ecommerce.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
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
            if (items.isEmpty()) {
                response.sendRedirect("cart");
                return;
            }
            request.setAttribute("cartItems", items);
            request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("views/login.jsp");
            return;
        }
        String shippingAddress = request.getParameter("shippingAddress");
        String paymentMethod = request.getParameter("paymentMethod");
        try {
            CartDAO cartDAO = new CartDAO();
            List<CartItem> items = cartDAO.getCartItems(user.getUserId());
            if (items.isEmpty()) {
                response.sendRedirect("cart");
                return;
            }
            OrderDAO orderDAO = new OrderDAO();
            boolean success = orderDAO.placeOrder(user.getUserId(), shippingAddress, paymentMethod, items);
            if (success) {
                response.sendRedirect("order-history?placed=true");
            } else {
                request.setAttribute("error", "Order failed");
                request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}