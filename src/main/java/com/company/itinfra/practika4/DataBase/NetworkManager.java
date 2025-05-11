package com.company.itinfra.practika4.DataBase;

import com.company.itinfra.practika4.Models.*;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkManager {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 3000;

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public boolean connect() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу: " + e.getMessage());
            return false;
        }
    }

    public User login(String username, String password) {
        try {
            output.writeObject("Login");
            output.writeObject(username);
            output.writeObject(password);

            String response = (String) input.readObject();
            if ("SUCCESS".equals(response)) {
                return (User) input.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка входа: " + e.getMessage());
        }
        return null;
    }

    public boolean register(User user, String password) {
        try {
            output.writeObject("REGISTER");
            output.writeObject(user);
            output.writeObject(password);

            String response = (String) input.readObject();
            return "SUCCESS".equals(response);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка регистрации: " + e.getMessage());
            return false;
        }
    }

    public List<Commponent> getComponents(){
        try {
            output.writeObject("UpdateTableDevices");
            output.writeObject(Integer.valueOf(1));
            List<ComponentDTO> componentDTOs = (List<ComponentDTO>) input.readObject();
            List<Commponent> components = componentDTOs.stream()
                    .map(dto -> new Commponent(
                            dto.getId(),
                            dto.getInventoryNumber(),
                            dto.getSerialNumber(),
                            dto.getName(),
                            dto.getModel(),
                            dto.getManufacturer(),
                            dto.getSeller(),
                            dto.getType(),
                            dto.getPurchaseDate(),
                            dto.getWarrantyEndDate(),
                            dto.getCabinet(),
                            dto.getStatus()
                    ))
                    .collect(Collectors.toList());
            return components;
        }catch (IOException e){
            System.err.println("Ошибка получения товаров: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка получения товаров: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<ComponentType> getTypeComponent() {
        try {
            output.writeObject("GetComponentType");
            List<ComponentType> componentTypes = (List<ComponentType>) input.readObject();

            return componentTypes;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Manufacturer> getManufacturer() {
        try {
            output.writeObject("GetManufacturer");
            List<Manufacturer> manufacturers = (List<Manufacturer>) input.readObject();

            return manufacturers;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Seller> getSeller() {
        try {
            output.writeObject("GetSeller");
            List<Seller> sellers = (List<Seller>) input.readObject();

            return sellers;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cabinet> getCabinet(int id) {
        try {
            output.writeObject("GetCabinet");
            output.writeObject(id);
            List<Cabinet> cabinets = (List<Cabinet>) input.readObject();

            return cabinets;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Product> getProducts() {
//        try {
//            output.writeObject("GET_PRODUCTS");
//            return (List<Product>) input.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Ошибка получения товаров: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public boolean addProduct(Product product) {
//        try {
//            output.writeObject("ADD_PRODUCT");
//            output.writeObject(product);
//
//            String response = (String) input.readObject();
//            return "SUCCESS".equals(response);
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Ошибка добавления товара: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean updateProduct(Product product) {
//        try {
//            output.writeObject("UPDATE_PRODUCT");
//            output.writeObject(product);
//
//            String response = (String) input.readObject();
//            return "SUCCESS".equals(response);
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Ошибка обновления товара: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean deleteProduct(int productId) {
//        try {
//            output.writeObject("DELETE_PRODUCT");
//            output.writeObject(productId);
//
//            String response = (String) input.readObject();
//            return "SUCCESS".equals(response);
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Ошибка удаления товара: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean addSale(Sale sale) {
//        try {
//            output.writeObject("ADD_SALE");
//            output.writeObject(sale);
//
//            String response = (String) input.readObject();
//            return "SUCCESS".equals(response);
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Ошибка добавления продажи: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public List<Sale> getSales() {
//        try {
//            output.writeObject("GET_SALES");
//            return (List<Sale>) input.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Ошибка получения продаж: " + e.getMessage());
//            return null;
//        }
//    }

    public boolean logout() {
        try {
            output.writeObject("LOGOUT");
            String response = (String) input.readObject();
            return "LOGOUT_SUCCESS".equals(response);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка выхода: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Ошибка отключения: " + e.getMessage());
        }
    }
}