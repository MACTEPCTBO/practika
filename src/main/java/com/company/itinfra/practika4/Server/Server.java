package com.company.itinfra.practika4.Server;

import com.company.itinfra.practika4.DataBase.AuthManager;
import com.company.itinfra.practika4.DataBase.DatabaseManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private static final int PORT = 3000;
    private static DatabaseManager dbManager;
    private static AuthManager authManager;

    public static void main(String[] args) {
        dbManager = new DatabaseManager();
        authManager = new AuthManager(dbManager);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новое подключение: " + clientSocket.getInetAddress());

                ServerHandler handler = new ServerHandler(clientSocket, dbManager, authManager);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        } finally {
            if (dbManager != null) {
                dbManager.close();
            }
        }
    }
}