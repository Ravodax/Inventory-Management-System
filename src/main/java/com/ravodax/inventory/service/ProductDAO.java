package com.ravodax.inventory.service;

import com.ravodax.inventory.model.Product;
import com.ravodax.inventory.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private final String url = "jdbc:sqlite:test2.db"; // путь к БД

    // Получить список всех продуктов
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT p.id AS product_id, p.name AS product_name, p.quantity, p.price,
                       c.id AS category_id, c.name AS category_name
                FROM products p
                JOIN categories c ON p.category_id = c.id
                """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getLong("category_id"),
                        rs.getString("category_name")
                );

                Product product = new Product(
                        rs.getLong("product_id"),
                        rs.getString("product_name"),
                        category,
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Добавить новый продукт
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products(name, category_id, quantity, price) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setLong(2, product.getCategory().getId());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setDouble(4, product.getPrice());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Обновить существующий продукт
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, category_id = ?, quantity = ?, price = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setLong(2, product.getCategory().getId());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setLong(5, product.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Product getProductById(long id) {
        String sql = """
            SELECT p.id AS product_id, p.name AS product_name, p.quantity, p.price,
                   c.id AS category_id, c.name AS category_name
            FROM products p
            JOIN categories c ON p.category_id = c.id
            WHERE p.id = ?
            """;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Category category = new Category(
                        rs.getLong("category_id"),
                        rs.getString("category_name")
                );

                return new Product(
                        rs.getLong("product_id"),
                        rs.getString("product_name"),
                        category,
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // если продукт не найден
    }

}