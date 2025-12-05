package com.ravodax.inventory.service;

import com.ravodax.inventory.model.Category;

import java.sql.*;

public class CategoryDAO {

    private final String url = "jdbc:sqlite:test2.db";

    /**
     * Получает ID категории по имени. Если нет — создаёт новую и возвращает её ID.
     */
    public long getOrCreateCategoryId(String categoryName) {
        try (Connection conn = DriverManager.getConnection(url)) {

            // 1. Попробовать найти категорию
            String sqlSelect = "SELECT id FROM categories WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlSelect)) {
                stmt.setString(1, categoryName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getLong("id");
                }
            }

            // 2. Если нет — добавить новую категорию
            String sqlInsert = "INSERT INTO categories (name) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, categoryName);
                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getLong(1);
                } else {
                    throw new SQLException("Не удалось получить ID новой категории!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }



}
