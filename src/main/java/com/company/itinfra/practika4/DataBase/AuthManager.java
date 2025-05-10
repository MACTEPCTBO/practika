package com.company.itinfra.practika4.DataBase;

import com.company.itinfra.practika4.Models.Employee;
import com.company.itinfra.practika4.Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManager {
    private final DatabaseManager dbManager;

    public AuthManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public User authenticate(String username, String password) {
        System.out.println(password + " " + username);
        String query = String.format(
                "SELECT * FROM user WHERE username = '%s' AND password_hash = '%s'",
                username, password
        );

        try (ResultSet rs = dbManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                System.out.println(12345);
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password_hash"));

                System.out.println(user.getUsername() + " " + user.getPassword());

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка аутентификации: " + e.getMessage());
        }

        return null;
    }

//    public boolean register(User user, String password) {
//        String query = String.format(
//                "INSERT INTO users (username, password, full_name, role) VALUES ('%s', '%s', '%s', '%s')",
//                user.getUsername(), password, user.getFullName(), user.getRole()
//        );
//
//        return dbManager.executeUpdate(query);
//    }
}