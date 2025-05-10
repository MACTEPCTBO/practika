package com.company.itinfra.practika4.Controllers;

import com.company.itinfra.practika4.DataBase.DatabaseManager;
import com.company.itinfra.practika4.DataBase.NetworkManager;
import com.company.itinfra.practika4.Log.Log;
import com.company.itinfra.practika4.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Login {

    @FXML
    private TextField LoginLogin, LoginPassword;
    private NetworkManager networkManager = new NetworkManager();

    @FXML
    private void Log(){

        if (!networkManager.connect()) {
            new Alert(Alert.AlertType.ERROR, "Не удалось подключиться к серверу").show();
            System.exit(1);
        }

        User user = networkManager.login(LoginLogin.getText().toString(), DatabaseManager.HashPassword(LoginPassword.getText().toString()));

        if (user != null) {
            Stage close = (Stage) LoginLogin.getScene().getWindow();
            close.close();

            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Home.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Home home = fxmlLoader.getController();
            home.initData(user.getId(), user.getUsername(), user.getRole());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            new Alert(Alert.AlertType.ERROR,"Неверный логин или пароль").show();
        }

    }

    @FXML private void Reg(){
        try {
            Stage close = (Stage) LoginLogin.getScene().getWindow();
            close.close();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Reg.class.getResource("Reg.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Управление IT-инфраструктурой предприятия");
            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            Log.showInfo(e.getMessage(), true);
        }
    }

}