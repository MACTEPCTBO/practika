package com.company.itinfra.practika4.Server;

import com.company.itinfra.practika4.DataBase.AuthManager;
import com.company.itinfra.practika4.DataBase.DatabaseManager;
import com.company.itinfra.practika4.Models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerHandler implements Runnable {
    private final Socket clientSocket;
    private DatabaseManager dbManager;
    private final AuthManager authManager;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private User currentUser;

    public ServerHandler(Socket socket, DatabaseManager dbManager, AuthManager authManager) {
        this.clientSocket = socket;
        this.dbManager = dbManager;
        this.authManager = authManager;
    }

    @Override
    public void run() {
        try {
            // Создаем потоки ввода/вывода
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

            // Обрабатываем команды клиента
            while (!clientSocket.isClosed()) {
                String command = (String) input.readObject();
                System.out.println("Получена команда: " + command);
                dbManager = new DatabaseManager();
                if (clientSocket.isClosed()) {
                    return;
                }

                switch (command) {
                    case "Login":
                        handleLogin();
                        break;
                    case "UpdateTableDevices":
                        handleGetDevices();
                        break;
                    case "GetComponentType":
                        handleGetComponentType();
                        break;
                    case "GetManufacturer":
                        handleGetManufacturer();
                        break;
                    case "GetSeller":
                        handleGetSeller();
                        break;
                    case "GetCabinet":
                        handleGetCabinet();
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException | SQLNonTransientConnectionException e) {
            System.out.println("Ошибка обработки клиента: " + e.getMessage());
        } finally {
            closeConnection();
            dbManager.close();
        }
    }

    private void handleGetComponentType() throws IOException, SQLNonTransientConnectionException {
        List<ComponentType> componentTypes = new ArrayList<>();
        String query = "SELECT * FROM component_type";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ComponentType type = new ComponentType(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("created_at"), // created_at
                        rs.getString("updated_at")  // updated_at
                );
                componentTypes.add(type);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке типов компонентов: " + e.getMessage());
            e.printStackTrace();
            // Можно добавить обработку ошибки, например, показать сообщение пользователю
        }


        output.writeObject(componentTypes);
    }

    private void handleGetManufacturer() throws IOException, SQLNonTransientConnectionException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT id, name, website, country, created_at, updated_at FROM manufacturer ORDER BY name";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("website"),
                        rs.getString("country"),
                        rs.getString("created_at")
                );
                manufacturers.add(manufacturer);
            }

            // Отправляем список производителей клиенту
            output.writeObject(manufacturers);

        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке производителей: " + e.getMessage());

            // Если соединение с БД потеряно, пробрасываем исключение выше
            if (e instanceof SQLNonTransientConnectionException) {
                throw (SQLNonTransientConnectionException) e;
            }

            // Для других SQL ошибок отправляем клиенту пустой список
            output.writeObject(new ArrayList<Manufacturer>());
        }
    }

    private void handleGetCabinet() throws IOException, SQLNonTransientConnectionException {
        List<Cabinet> cabinets = new ArrayList<>();
        String query = "SELECT c.id, c.number, c.description, f.id as floor_id, f.number as floor_number, " +
                "f.description as floor_desc, o.id as office_id, o.name as office_name " +
                "FROM cabinet c " +
                "JOIN floor f ON c.floor_id = f.id " +
                "JOIN office o ON f.office_id = o.id " +
                "JOIN employee e ON e.office_id = o.id " +
                "WHERE e.id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, (int) input.readObject());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Office office = new Office(
                            rs.getInt("office_id"),
                            rs.getString("office_name"),
                            "", "", ""
                    );

                    Floor floor = new Floor(
                            rs.getInt("floor_id"),
                            office,
                            rs.getInt("floor_number"),
                            rs.getString("floor_desc"),
                            "", ""
                    );

                    Cabinet cabinet = new Cabinet(
                            rs.getInt("id"),
                            floor,
                            Integer.parseInt(rs.getString("number")),
                            rs.getString("description"),
                            "", ""
                    );
                    cabinets.add(cabinet);
                }
            }
            System.out.println("\n" + cabinets.get(0).getFloor().getNumber());
            output.writeObject(cabinets);

        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке кабинетов: " + e.getMessage());
            if (e instanceof SQLNonTransientConnectionException) {
                throw (SQLNonTransientConnectionException) e;
            }
            output.writeObject(new ArrayList<Cabinet>());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGetSeller() throws IOException, SQLNonTransientConnectionException {
        List<Seller> sellers = new ArrayList<>();
        String query = "SELECT * FROM seller";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Seller seller = new Seller(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("legal_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("website"),
                        rs.getString("created_at")
                );
                sellers.add(seller);
            }

            // Отправляем список производителей клиенту
            output.writeObject(sellers);
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке типов Продавцов: " + e.getMessage());
            e.printStackTrace();
            // Можно добавить обработку ошибки, например, показать сообщение пользователю
        }
    }

    private void handleLogin() throws IOException, ClassNotFoundException{

        String username = (String) input.readObject();
        String password = (String) input.readObject();

        currentUser = authManager.authenticate(username, password);
        output.writeObject(currentUser != null ? "SUCCESS" : "FAILURE");

        if (currentUser != null) {
            output.writeObject(currentUser);
        }
    }

    private void handleGetDevices() throws IOException, ClassNotFoundException, SQLNonTransientConnectionException {
        System.out.println("Чтение данных");
        try {
            int employeeId = (int) input.readObject();
            System.out.println(employeeId);

            List<Commponent> components = new ArrayList<>();

            // Основной запрос с JOIN для получения всех данных сразу
            String query = "SELECT c.*, " +
                    "cab.id as cabinet_id, cab.number as cabinet_number, cab.description as cabinet_description, " +
                    "fl.id as floor_id, fl.number as floor_number, fl.description as floor_description, " +
                    "off.id as office_id, off.name as office_name, " +
                    "ct.id as component_type_id, ct.name as component_type_name, ct.description as component_type_description, " +
                    "m.id as manufacturer_id, m.name as manufacturer_name, m.website as manufacturer_website, m.country as manufacturer_country, " +
                    "s.id as seller_id, s.name as seller_name, s.legal_name as seller_legal_name, s.phone as seller_phone, " +
                    "s.email as seller_email, s.website as seller_website " +
                    "FROM component c " +
                    "JOIN component_maintenance cm ON c.id = cm.component_id " +
                    "JOIN cabinet cab ON c.cabinet_id = cab.id " +
                    "JOIN floor fl ON cab.floor_id = fl.id " +
                    "JOIN office off ON fl.office_id = off.id " +
                    "JOIN component_type ct ON c.type_id = ct.id " +
                    "JOIN manufacturer m ON c.manufacturer_id = m.id " +
                    "JOIN seller s ON c.seller_id = s.id " +
                    "WHERE cm.performed_by = ?"; // Используем параметризованный запрос

            System.out.println(dbManager.getConnection().isClosed());
            if (dbManager.getConnection().isClosed()) {
                dbManager = new DatabaseManager();
            }
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
                stmt.setInt(1, employeeId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Создаем объекты Office, Floor, Cabinet
                        Office office = new Office(
                                rs.getInt("office_id"),
                                rs.getString("office_name"),
                                "", // short_name
                                "", // description
                                "" // created_at
                        );

                        Floor floor = new Floor(
                                rs.getInt("floor_id"),
                                office,
                                rs.getInt("floor_number"),
                                rs.getString("floor_description"),
                                "", // created_at
                                ""  // updated_at
                        );

                        Cabinet cabinet = new Cabinet(
                                rs.getInt("cabinet_id"), // Исправлено с id на cabinet_id
                                floor,
                                Integer.parseInt(rs.getString("cabinet_number")), // Оставляем как String, если в БД VARCHAR
                                rs.getString("cabinet_description"),
                                "",
                                ""// date_at
                                  // time_at
                        );

                        // Создаем объект ComponentType
                        ComponentType componentType = new ComponentType(
                                rs.getInt("component_type_id"), // Исправлено с id на component_type_id
                                rs.getString("component_type_name"),
                                rs.getString("component_type_description"),
                                "", // date_at
                                ""  // time_at
                        );

                        // Создаем объект Manufacturer
                        Manufacturer manufacturer = new Manufacturer(
                                rs.getInt("manufacturer_id"), // Исправлено с id на manufacturer_id
                                rs.getString("manufacturer_name"),
                                rs.getString("manufacturer_website"),
                                rs.getString("manufacturer_country"),
                                ""  // created_at
                        );

                        // Создаем объект Seller
                        Seller seller = new Seller(
                                rs.getInt("seller_id"), // Исправлено с id на seller_id
                                rs.getString("seller_name"),
                                rs.getString("seller_legal_name"),
                                rs.getString("seller_phone"),
                                rs.getString("seller_email"),
                                rs.getString("seller_website"),
                                ""  // created_at
                        );

                        // Создаем основной объект Component
                        Commponent component = new Commponent(
                                rs.getInt("id"),
                                rs.getString("inventory_number"),
                                rs.getString("serial_number"),
                                rs.getString("name"),
                                rs.getString("model"),
                                manufacturer,
                                seller,
                                componentType,
                                rs.getString("purchase_date"),
                                rs.getString("warranty_end_date"),
                                cabinet,
                                rs.getString("status")
                        );
                        components.add(component);
                        // Добавляем component в коллекцию или обрабатываем дальше
                    }
                }
                List<ComponentDTO> componentDTOs = components.stream()
                        .map(ComponentDTO::new)
                        .collect(Collectors.toList());

                output.writeObject(componentDTOs);
            } catch (SQLException e) {
                System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println("Общая ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }
}


// Проверить получение компонентов