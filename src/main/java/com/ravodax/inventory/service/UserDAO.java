package com.ravodax.inventory.service;

import com.ravodax.inventory.model.User;
import com.ravodax.inventory.model.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final String url = "jdbc:sqlite:test2.db"; // путь к твоей БД

    // Получить список всех пользователей
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = """
                SELECT u.id AS user_id, u.username, u.password,
                       r.id AS role_id, r.name AS role_name
                FROM users u
                JOIN roles r ON u.role_id = r.id
                """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Создаём объект роли
                Role role = new Role(
                        rs.getLong("role_id"),
                        rs.getString("role_name")
                );

                // Создаём объект пользователя
                User user = new User(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role
                );

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Пример использования

}
