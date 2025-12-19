package com.ravodax.inventory.service;

import java.sql.*;

public class InventoryTransactionDAO {

    private final String url = "jdbc:sqlite:test2.db";

    /**
     * Добавляет новую транзакцию: SALE или RESTOCK
     *
     * @param status    "SALE" или "RESTOCK"
     * @param productId ID продукта
     * @param userId    ID пользователя
     * @param quantity  количество
     * @param price     цена за единицу
     * @return true если успешно, false иначе
     */
    public boolean addTransaction(String status, long productId, long userId, int quantity, double price) {
        String sql = """
                INSERT INTO inventory_transactions (status, product_id, quantity, user_id, price)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setLong(2, productId);
            stmt.setLong(3, quantity);
            stmt.setLong(4, userId);
            stmt.setDouble(5, price);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void printAllTransactions() {
        String sql = "SELECT id, status, product_id, quantity, user_id, price, created_at FROM inventory_transactions";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Transactions on goods:");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getLong("id") +
                                ", Status: " + rs.getString("status") +
                                ", Product ID: " + rs.getLong("product_id") +
                                ", User ID: " + rs.getLong("user_id") +
                                ", Quantity: " + rs.getInt("quantity") +
                                ", Price: " + rs.getDouble("price") +
                                ", Created At: " + rs.getString("created_at")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printIncomeAndExpenses() {
        String sql = """
            SELECT status, SUM(quantity * price) AS total
            FROM inventory_transactions
            GROUP BY status
            """;

        double income = 0.0;
        double expenses = 0.0;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String status = rs.getString("status");
                double total = rs.getDouble("total");

                if ("SALE".equalsIgnoreCase(status)) {
                    income = total;
                } else if ("RESTOCK".equalsIgnoreCase(status)) {
                    expenses = total;
                }
            }

            System.out.println("=== Financial summary ===");
            System.out.println("Income (SALE): " + income);
            System.out.println("Expenses (RESTOCK): " + expenses);
            System.out.println("Profit: " + (income - expenses));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}