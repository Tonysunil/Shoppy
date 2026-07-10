package ecommerce.servlet;

import java.io.IOException;
import ecommerce.dao.ProductDAO;
import ecommerce.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idText = request.getParameter("id");
        if (idText == null || idText.isEmpty()) {
            response.getWriter().println("Error: Product ID missing");
            return;
        }
        int productId = Integer.parseInt(idText);
        try {
            ProductDAO dao = new ProductDAO();
            Product product = dao.getProductById(productId);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/views/product-detail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
        }
    }
}