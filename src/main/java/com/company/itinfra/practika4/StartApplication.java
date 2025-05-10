package com.company.itinfra.practika4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(StartApplication.class.getResource("Controllers/Login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("Controllers/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Управление IT-инфраструктурой предприятия");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}