package ecommerce.dao;

import java.sql.*;
import java.util.*;
import ecommerce.model.CartItem;
import ecommerce.model.Order;
import ecommerce.model.OrderItem;
import ecommerce.util.DBConnection;

public class OrderDAO {

    public boolean placeOrder(int userId, String shippingAddress, String paymentMethod, List<CartItem> cartItems) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            double total = 0;
            for (CartItem ci : cartItems) total += ci.getQuantity() * ci.getProduct().getPrice();

            String orderSQL = "INSERT INTO orders (user_id, total_amount, shipping_address, payment_method, status) VALUES (?, ?, ?, ?, 'PENDING')";
            int orderId = 0;
            try (PreparedStatement ps = conn.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setDouble(2, total);
                ps.setString(3, shippingAddress);
                ps.setString(4, paymentMethod);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) orderId = rs.getInt(1);
            }
            if (orderId == 0) { conn.rollback(); return false; }

            String itemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(itemSQL)) {
                for (CartItem ci : cartItems) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, ci.getProductId());
                    ps.setInt(3, ci.getQuantity());
                    ps.setDouble(4, ci.getProduct().getPrice());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Reduce stock
            String stockSQL = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(stockSQL)) {
                for (CartItem ci : cartItems) {
                    ps.setInt(1, ci.getQuantity());
                    ps.setInt(2, ci.getProductId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Clear cart
            new CartDAO().clearCart(userId);  // but we already have connection, could be improved
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) { conn.setAutoCommit(true); conn.close(); }
        }
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setUserId(rs.getInt("user_id"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setStatus(rs.getString("status"));
                list.add(o);
            }
        }
        return list;
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setUserId(rs.getInt("user_id"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setStatus(rs.getString("status"));
                list.add(o);
            }
        }
        return list;
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }

    public List<OrderItem> getOrderItems(int orderId) throws SQLException {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT oi.*, p.product_name, p.image_url FROM order_items oi JOIN products p ON oi.product_id = p.product_id WHERE oi.order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem oi = new OrderItem();
                oi.setOrderItemId(rs.getInt("order_item_id"));
                oi.setOrderId(rs.getInt("order_id"));
                oi.setProductId(rs.getInt("product_id"));
                oi.setQuantity(rs.getInt("quantity"));
                oi.setPrice(rs.getDouble("price"));
                oi.setProductName(rs.getString("product_name"));
                oi.setImageUrl(rs.getString("image_url"));
                list.add(oi);
            }
        }
        return list;
    }
}