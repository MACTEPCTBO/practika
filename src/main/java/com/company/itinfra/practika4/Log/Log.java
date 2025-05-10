package com.company.itinfra.practika4.Log;

import javafx.scene.control.Alert;

public class Log {
    public static void showInfo(String msg, boolean error){
        try {
            if (error) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(msg);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(msg);
                alert.showAndWait();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (Settings.LOG_TEXT_CONSOLE){
            System.out.println(msg);
        }
    }
}
