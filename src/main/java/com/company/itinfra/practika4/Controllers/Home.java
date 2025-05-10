package com.company.itinfra.practika4.Controllers;

import com.company.itinfra.practika4.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Home {
    @FXML private Label userNameLabel;
    @FXML private Label userRoleLabel;
    @FXML private Label statusLabel;
    @FXML private VBox adminSection;
    @FXML private StackPane contentPane;

    private Map<String, String> fxmlPaths = new HashMap<>();
    private Stage primaryStage;
    private String currentUserRole;

    private int idUser;

    @FXML
    public void initialize() {
        // Инициализация путей к FXML файлам разделов
        fxmlPaths.put("devices", "Devices.fxml");
        fxmlPaths.put("software", "Software.fxml");
        fxmlPaths.put("maintenance", "Maintenance.fxml");
        fxmlPaths.put("reports", "Reports.fxml");
        fxmlPaths.put("users", "Users.fxml");
        fxmlPaths.put("settings", "Settings.fxml");

        // Установка информации о пользователе (должно приходить из модуля авторизации)
    }

    @FXML
    public void initData(int Id, String username, String role) {
        this.idUser = Id;
        setupUserInfo(username, role);

    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setupUserInfo(String userName, String role) {
        userNameLabel.setText(userName);
        userRoleLabel.setText("Роль: " + role);
        currentUserRole = role;

        // Показываем админские разделы только для администраторов
        adminSection.setVisible("Администратор".equals(role));
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = loader.load();

            // Если загружаемый контроллер требует Stage или другие параметры
            Object controller = loader.getController();
            if (controller instanceof StageAwareController) {
                ((StageAwareController) controller).setPrimaryStage(primaryStage);
            }

            contentPane.getChildren().setAll(content);
        } catch (IOException e) {
            showError("Ошибка загрузки раздела", "Не удалось загрузить содержимое раздела");
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Обработчики для кнопок меню
    @FXML
    private void showDevicesSection(ActionEvent event) {
        statusLabel.setText("Раздел: Управление оборудованием");
        loadContent(fxmlPaths.get("devices"));
    }

    @FXML
    private void showSoftwareSection(ActionEvent event) {
        statusLabel.setText("Раздел: Управление лицензиями ПО");
        loadContent(fxmlPaths.get("software"));
    }

    @FXML
    private void showMaintenanceSection(ActionEvent event) {
        statusLabel.setText("Раздел: Обслуживание оборудования");
        loadContent(fxmlPaths.get("maintenance"));
    }

    @FXML
    private void showReportsSection(ActionEvent event) {
        statusLabel.setText("Раздел: Отчеты");
        loadContent(fxmlPaths.get("reports"));
    }

    @FXML
    private void showUsersSection(ActionEvent event) {
        statusLabel.setText("Раздел: Управление пользователями");
        loadContent(fxmlPaths.get("users"));
    }

    @FXML
    private void showSettingsSection(ActionEvent event) {
        statusLabel.setText("Раздел: Настройки системы");
        loadContent(fxmlPaths.get("settings"));
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Загрузка экрана авторизации
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/itinfra/practika4/views/Login.fxml"));
            Parent root = loader.load();

            // Получение текущего Stage
            Stage stage = (Stage) contentPane.getScene().getWindow();

            // Установка новой сцены
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Авторизация");
            stage.show();
        } catch (IOException e) {
            showError("Ошибка выхода", "Не удалось загрузить экран авторизации");
            e.printStackTrace();
        }
    }
}

// Интерфейс для контроллеров, которым нужен доступ к Stage
interface StageAwareController {
    void setPrimaryStage(Stage stage);
}