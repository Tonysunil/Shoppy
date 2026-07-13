package ecommerce.servlet;

import java.io.IOException;
import java.util.List;
import ecommerce.dao.ProductDAO;
import ecommerce.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDAO dao = new ProductDAO();
            String searchQuery = request.getParameter("search");
            List<Product> products;
            
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                products = dao.searchProducts(searchQuery.trim());
                request.setAttribute("searchQuery", searchQuery.trim());
            } else {
                products = dao.getAllProducts();
            }
            
            request.setAttribute("productList", products);
            request.getRequestDispatcher("/views/home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
        }
    }
}