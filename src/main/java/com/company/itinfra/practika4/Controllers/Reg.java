package com.company.itinfra.practika4.Controllers;

import com.company.itinfra.practika4.Log.Log;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Reg {
    @FXML
    private TextField RegLogin, RegPassword;

    @FXML private void Reg(){
        if (RegLogin.getText().isEmpty() || RegPassword.getText().isEmpty()){
            Log.showInfo("Заполните поля логин и пароль", false);
        }
        else if (RegPassword.getText().length() < 8){
            Log.showInfo("Длинна пароля должна быть не меньше 8 символов", false);
        }


    }
}
