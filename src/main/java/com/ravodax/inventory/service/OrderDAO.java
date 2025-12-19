package com.ravodax.inventory.service;

import com.ravodax.inventory.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private final String url = "jdbc:sqlite:test2.db";
    public long createOrder(Order order, List<OrderItem> items) {
        String sqlOrder = """
                INSERT INTO orders (user_id, total_price, status, created_at)
                VALUES (?, ?, ?, ?)
                """;

        String sqlItem = """
                INSERT INTO order_items (order_id, product_id, quantity, price)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false);

            // ---------- 1. Рассчитываем общую сумму ----------
            double total = 0;
            for (OrderItem item : items) {
                total += item.getTotalPrice();
            }
            order.getTotalPrice(total);

            long orderId = -1;

            // ---------- 2. Создаем заказ ----------
            try (PreparedStatement stmt =
                         conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setLong(1, order.getUser().getId());
                stmt.setDouble(2, order.getTotalPrice());
                stmt.setString(3, order.getStatus().name());
                stmt.setString(4, LocalDateTime.now().toString());

                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    orderId = keys.getLong(1);
                } else {
                    throw new SQLException("Не удалось получить ID заказа!");
                }
            }

            // ---------- 3. Добавляем позиции ----------
            try (PreparedStatement stmt = conn.prepareStatement(sqlItem)) {
                for (OrderItem item : items) {
                    stmt.setLong(1, orderId);
                    stmt.setLong(2, item.getProductId());
                    stmt.setInt(3, item.getQuantity());
                    stmt.setDouble(4, item.getPricePerUnit());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
            return orderId;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // ------------------------
    // ПОЛУЧИТЬ ЗАКАЗ ПО ID
    // ------------------------
    public Order getOrderById(long id) {
        String sqlOrder = """
                SELECT o.id, o.user_id, o.total_price, o.status, u.username, u.password, r.id AS role_id, r.name AS role_name
                FROM orders o
                JOIN users u ON o.user_id = u.id
                JOIN roles r ON u.role_id = r.id
                WHERE o.id = ?
                """;

        String sqlItems = """
                SELECT id, order_id, product_id, quantity, price
                FROM order_items
                WHERE order_id = ?
                """;

        try (Connection conn = DriverManager.getConnection(url)) {

            Order order = null;

            // ---------- Основной заказ ----------
            try (PreparedStatement stmt = conn.prepareStatement(sqlOrder)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) return null;

                Role role = new Role(rs.getLong("role_id"), rs.getString("role_name"));
                User user = new User(rs.getLong("user_id"), rs.getString("username"), rs.getString("password"), role);

                order = new Order(
                        rs.getLong("id"),
                        user,
                        rs.getDouble("total_price"),
                        OrderStatus.valueOf(rs.getString("status"))
                );
            }

            // ---------- Позиции заказа ----------
            List<OrderItem> items = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement(sqlItems)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    OrderItem item = new OrderItem(
                            rs.getLong("order_id"),
                            rs.getLong("product_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    );
                    item.setId(rs.getLong("id"));
                    items.add(item);
                }
            }

            // Здесь можно добавить метод order.setItems(items), если добавишь поле в Order

            return order;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ------------------------
    // ПОЛУЧИТЬ ВСЕ ЗАКАЗЫ
    // ------------------------
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = """
                SELECT o.id, o.user_id, o.total_price, o.status, u.username, u.password, r.id AS role_id, r.name AS role_name
                FROM orders o
                JOIN users u ON o.user_id = u.id
                JOIN roles r ON u.role_id = r.id
                """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Role role = new Role(rs.getLong("role_id"), rs.getString("role_name"));
                User user = new User(rs.getLong("user_id"), rs.getString("username"), rs.getString("password"), role);

                Order order = new Order(
                        rs.getLong("id"),
                        user,
                        rs.getDouble("total_price"),
                        OrderStatus.valueOf(rs.getString("status"))
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
