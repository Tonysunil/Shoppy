package ecommerce.servlet;

import java.io.IOException;
import java.util.List;
import ecommerce.dao.OrderDAO;
import ecommerce.model.Order;
import ecommerce.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/order-history")
public class OrderHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("views/login.jsp");
            return;
        }
        try {
            OrderDAO dao = new OrderDAO();
            List<Order> orders = dao.getOrdersByUserId(user.getUserId());
            java.util.Map<Integer, List<ecommerce.model.OrderItem>> orderItemsMap = new java.util.HashMap<>();
            for (Order o : orders) {
                orderItemsMap.put(o.getOrderId(), dao.getOrderItems(o.getOrderId()));
            }
            request.setAttribute("orders", orders);
            request.setAttribute("orderItemsMap", orderItemsMap);
            request.getRequestDispatcher("/views/order-history.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}