package com.company.itinfra.practika4.DataBase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/practika";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private Connection connection;

    public DatabaseManager() {
        try {
            // Регистрация драйвера JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Установка соединения с БД
            Properties properties = new Properties();
            properties.setProperty("user", DB_USER);
            properties.setProperty("password", DB_PASSWORD);
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");

            connection = DriverManager.getConnection(DB_URL, properties);
            System.out.println("Подключение к БД установлено");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean executeUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
            return false;
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }


    public static String HashPassword(String password){
        try {
            // Создаем экземпляр MessageDigest для SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Преобразуем пароль в байты и хешируем
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // Преобразуем байты в шестнадцатеричную строку
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static void main(String[] args) {
        System.out.println((HashPassword("1234")));
    }
}