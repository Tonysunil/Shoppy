package ecommerce.dao;

import java.sql.*;
import java.util.*;
import ecommerce.model.CartItem;
import ecommerce.model.Product;
import ecommerce.util.DBConnection;

public class CartDAO {

    public void addOrUpdateCart(int userId, int productId, int quantity) throws SQLException {
        String checkSQL = "SELECT * FROM cart WHERE user_id = ? AND product_id = ?";
        String updateSQL = "UPDATE cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";
        String insertSQL = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            // Check if exists
            try (PreparedStatement psCheck = conn.prepareStatement(checkSQL)) {
                psCheck.setInt(1, userId);
                psCheck.setInt(2, productId);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    // Update
                    try (PreparedStatement psUp = conn.prepareStatement(updateSQL)) {
                        psUp.setInt(1, quantity);
                        psUp.setInt(2, userId);
                        psUp.setInt(3, productId);
                        psUp.executeUpdate();
                    }
                } else {
                    // Insert
                    try (PreparedStatement psIns = conn.prepareStatement(insertSQL)) {
                        psIns.setInt(1, userId);
                        psIns.setInt(2, productId);
                        psIns.setInt(3, quantity);
                        psIns.executeUpdate();
                    }
                }
            }
        }
    }

    public List<CartItem> getCartItems(int userId) throws SQLException {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT c.*, p.product_name, p.price, p.image_url, p.description " +
                     "FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem ci = new CartItem();
                ci.setCartId(rs.getInt("cart_id"));
                ci.setUserId(rs.getInt("user_id"));
                ci.setProductId(rs.getInt("product_id"));
                ci.setQuantity(rs.getInt("quantity"));

                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                p.setDescription(rs.getString("description"));
                ci.setProduct(p);
                items.add(ci);
            }
        }
        return items;
    }

    public void updateQuantity(int userId, int productId, int newQuantity) throws SQLException {
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);
            ps.executeUpdate();
        }
    }

    public void removeCartItem(int userId, int productId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public int getCartItemCount(int userId) throws SQLException {
        String sql = "SELECT SUM(quantity) AS total FROM cart WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
            return 0;
        }
    }
}