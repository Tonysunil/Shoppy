package ecommerce.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import ecommerce.dao.CategoryDAO;
import ecommerce.dao.OrderDAO;
import ecommerce.dao.ProductDAO;
import ecommerce.model.Category;
import ecommerce.model.Order;
import ecommerce.model.Product;
import ecommerce.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/admin/*")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10,
    fileSizeThreshold = 1024 * 1024
)
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("home");
            return;
        }
        String path = request.getPathInfo();
        if (path == null) path = "/";
        try {
            if (path.equals("/products")) {
                ProductDAO productDAO = new ProductDAO();
                List<Product> products = productDAO.getAllProducts();
                request.setAttribute("products", products);

                // Load categories for the update dropdown
                CategoryDAO categoryDAO = new CategoryDAO();
                List<Category> categories = categoryDAO.getAllCategories();
                request.setAttribute("categories", categories);

                request.getRequestDispatcher("/views/admin/manage-products.jsp").forward(request, response);

            } else if (path.equals("/orders")) {
                OrderDAO dao = new OrderDAO();
                List<Order> orders = dao.getAllOrders();
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/views/admin/view-orders.jsp").forward(request, response);

            } else if (path.equals("/add-product")) {
                // Load categories for the dropdown
                CategoryDAO categoryDAO = new CategoryDAO();
                List<Category> categories = categoryDAO.getAllCategories();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/views/admin/add-product.jsp").forward(request, response);

            } else {
                response.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("home");
            return;
        }
        String path = request.getPathInfo();
        if (path == null) path = "/";
        try {
            if (path.equals("/add-product")) {
                addProduct(request, response);
            } else if (path.equals("/update-product")) {
                updateProduct(request, response);
            } else if (path.equals("/delete-product")) {
                deleteProduct(request, response);
            } else if (path.equals("/update-order-status")) {
                updateOrderStatus(request, response);
            } else {
                response.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/add-product.jsp").forward(request, response);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("productName");
        String desc = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Part filePart = request.getPart("imageFile");
        String imageUrl;
        if (filePart != null && filePart.getSize() > 0) {
            imageUrl = saveProductImage(filePart, name);
        } else {
            imageUrl = "images/default.jpg";
        }
        Product p = new Product();
        p.setProductName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setCategoryId(categoryId);
        p.setImageUrl(imageUrl);
        ProductDAO dao = new ProductDAO();
        dao.addProduct(p);
        response.sendRedirect("products");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("productName");
        String desc = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String imageUrl = request.getParameter("imageUrl");
        Part filePart = request.getPart("imageFile");
        if (filePart != null && filePart.getSize() > 0) {
            imageUrl = saveProductImage(filePart, name);
        }
        Product p = new Product();
        p.setProductId(productId);
        p.setProductName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setCategoryId(categoryId);
        p.setImageUrl(imageUrl);
        ProductDAO dao = new ProductDAO();
        dao.updateProduct(p);
        response.sendRedirect("products");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        ProductDAO dao = new ProductDAO();
        dao.deleteProduct(productId);
        response.sendRedirect("products");
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        OrderDAO dao = new OrderDAO();
        dao.updateOrderStatus(orderId, status);
        response.sendRedirect("orders");
    }

    private String saveProductImage(Part filePart, String productName) throws IOException {
        String baseName = productName.replaceAll("[^a-zA-Z0-9]", "_");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) extension = fileName.substring(dotIndex);
        else extension = ".jpg";
        String finalFileName = baseName + extension;
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        String filePath = uploadPath + File.separator + finalFileName;
        filePart.write(filePath);
        return "images/" + finalFileName;
    }
}