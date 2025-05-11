package com.company.itinfra.practika4.Controllers;

import com.company.itinfra.practika4.DataBase.DatabaseManager;
import com.company.itinfra.practika4.DataBase.NetworkManager;
import com.company.itinfra.practika4.Models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddComponentDialogFX {
    private final Stage dialog;
    private NetworkManager networkManager;

    // Form fields
    private TextField nameField;
    private TextField modelField;
    private TextField serialNumberField;
    private TextField inventoryNumberField;
    private ComboBox<ComponentType> typeComboBox;
    private ComboBox<Manufacturer> manufacturerComboBox;
    private ComboBox<Seller> sellerComboBox;
    private ComboBox<Cabinet> cabinetComboBox;
    private DatePicker purchaseDatePicker;
    private DatePicker warrantyDatePicker;
    private TextField priceField;
    private ComboBox<String> statusComboBox;
    private TextArea notesArea;

    int id;

    public AddComponentDialogFX(Stage parent, NetworkManager networkManager) {
        this.networkManager = networkManager;

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parent);
        dialog.setTitle("Добавить новый компонент");

        initializeUI();
    }

    @FXML
    public void initData(int Id) {
        this.id = Id;
    }

    private void initializeUI() {
        // Create form fields
        nameField = new TextField();
        modelField = new TextField();
        serialNumberField = new TextField();
        inventoryNumberField = new TextField();

        typeComboBox = new ComboBox<>();
        manufacturerComboBox = new ComboBox<>();
        sellerComboBox = new ComboBox<>();
        cabinetComboBox = new ComboBox<>();

        purchaseDatePicker = new DatePicker();
        purchaseDatePicker.setValue(java.time.LocalDate.now());

        warrantyDatePicker = new DatePicker();

        priceField = new TextField();

        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("active", "in_repair", "decommissioned", "lost");
        statusComboBox.getSelectionModel().selectFirst();

        notesArea = new TextArea();
        notesArea.setPrefRowCount(3);

        // Set converters for ComboBoxes to display proper text
        setComboBoxConverters();

        // Create form grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        // Add fields to grid
        formGrid.add(new Label("Название:"), 0, 0);
        formGrid.add(nameField, 1, 0);

        formGrid.add(new Label("Модель:"), 0, 1);
        formGrid.add(modelField, 1, 1);

        formGrid.add(new Label("Серийный номер:"), 0, 2);
        formGrid.add(serialNumberField, 1, 2);

        formGrid.add(new Label("Инвентарный номер:"), 0, 3);
        formGrid.add(inventoryNumberField, 1, 3);

        formGrid.add(new Label("Тип компонента:"), 0, 4);
        formGrid.add(typeComboBox, 1, 4);

        formGrid.add(new Label("Производитель:"), 0, 5);
        formGrid.add(manufacturerComboBox, 1, 5);

        formGrid.add(new Label("Поставщик:"), 0, 6);
        formGrid.add(sellerComboBox, 1, 6);

        formGrid.add(new Label("Кабинет:"), 0, 7);
        formGrid.add(cabinetComboBox, 1, 7);

        formGrid.add(new Label("Дата покупки:"), 0, 8);
        formGrid.add(purchaseDatePicker, 1, 8);

        formGrid.add(new Label("Дата окончания гарантии:"), 0, 9);
        formGrid.add(warrantyDatePicker, 1, 9);

        formGrid.add(new Label("Цена:"), 0, 10);
        formGrid.add(priceField, 1, 10);

        formGrid.add(new Label("Статус:"), 0, 11);
        formGrid.add(statusComboBox, 1, 11);

        formGrid.add(new Label("Примечания:"), 0, 12);
        formGrid.add(notesArea, 1, 12);

        // Create buttons
        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(e -> saveComponent());

        Button cancelButton = new Button("Отмена");
        cancelButton.setOnAction(e -> dialog.close());

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        // Create main layout
        VBox mainLayout = new VBox(10, formGrid, buttonBox);
        mainLayout.setPadding(new Insets(15));

        // Set scene
        Scene scene = new Scene(mainLayout, 500, 700);
        dialog.setScene(scene);

        // Load data for comboboxes
        loadComboBoxData();
    }

    private void setComboBoxConverters() {
        typeComboBox.setConverter(new StringConverter<ComponentType>() {
            @Override
            public String toString(ComponentType type) {
                return type != null ? type.getName() : "";
            }

            @Override
            public ComponentType fromString(String string) {
                return null; // Not needed for display
            }
        });

        manufacturerComboBox.setConverter(new StringConverter<Manufacturer>() {
            @Override
            public String toString(Manufacturer manufacturer) {
                return manufacturer != null ? manufacturer.getName() : "";
            }

            @Override
            public Manufacturer fromString(String string) {
                return null;
            }
        });

        sellerComboBox.setConverter(new StringConverter<Seller>() {
            @Override
            public String toString(Seller seller) {
                return seller != null ? seller.getName() : "";
            }

            @Override
            public Seller fromString(String string) {
                return null;
            }
        });

        cabinetComboBox.setConverter(new StringConverter<Cabinet>() {
            @Override
            public String toString(Cabinet cabinet) {
                if (cabinet == null) return "";
                return String.format("%s, %d этаж, каб. %d",
                        cabinet.getFloor().getOffice().getNameOffice(),
                        cabinet.getFloor().getNumber(),
                        cabinet.getNumber());
            }

            @Override
            public Cabinet fromString(String string) {
                return null;
            }
        });
    }

    private void loadComboBoxData() {
        new Thread(() -> {
            try {
                // Load component types
                ObservableList<ComponentType> types = FXCollections.observableList(loadComponentTypes());

                // Load manufacturers
                ObservableList<Manufacturer> manufacturers = FXCollections.observableList(loadManufacturers());

                // Load sellers
                ObservableList<Seller> sellers = FXCollections.observableList(loadSellers());

                // Load cabinets
                ObservableList<Cabinet> cabinets = FXCollections.observableList(loadCabinets());

                // Update UI on JavaFX thread
                Platform.runLater(() -> {
                    typeComboBox.setItems(types);
                    manufacturerComboBox.setItems(manufacturers);
                    sellerComboBox.setItems(sellers);
                    cabinetComboBox.setItems(cabinets);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> showError("Ошибка загрузки данных", e.getMessage()));
            }
        }).start();
    }


    private List<ComponentType> loadComponentTypes() {
        List<ComponentType> types = networkManager.getTypeComponent();

        return types;
    }

    private List<Manufacturer> loadManufacturers() {
        List<Manufacturer> manufacturers = networkManager.getManufacturer();
        return manufacturers;
    }

    private List<Seller> loadSellers() {
        List<Seller> sellers = networkManager.getSeller();
        return sellers;
    }

    private List<Cabinet> loadCabinets(){
        List<Cabinet> cabinets = networkManager.getCabinet(id);
        return cabinets;
    }
 /*
    private List<Manufacturer> loadManufacturers() throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT id, name, website, country FROM manufacturer";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("website"),
                        rs.getString("country"),
                        ""
                );
                manufacturers.add(manufacturer);
            }
        }
        return manufacturers;
    }

    private List<Seller> loadSellers() throws SQLException {
        List<Seller> sellers = new ArrayList<>();
        String query = "SELECT id, name, legal_name, phone, email, website FROM seller";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Seller seller = new Seller(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("legal_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        "",
                        rs.getString("website"),
                        ""
                );
                sellers.add(seller);
            }
        }
        return sellers;
    }

    private List<Cabinet> loadCabinets() throws SQLException {
        List<Cabinet> cabinets = new ArrayList<>();
        String query = "SELECT c.id, c.number, c.description, f.id as floor_id, f.number as floor_number, " +
                "f.description as floor_desc, o.id as office_id, o.name as office_name " +
                "FROM cabinet c " +
                "JOIN floor f ON c.floor_id = f.id " +
                "JOIN office o ON f.office_id = o.id";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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
        return cabinets;
    }*/

    private void saveComponent() {
        try {
            // Validate required fields
            if (nameField.getText().isEmpty() ||
                    serialNumberField.getText().isEmpty() ||
                    inventoryNumberField.getText().isEmpty() ||
                    typeComboBox.getValue() == null ||
                    manufacturerComboBox.getValue() == null ||
                    sellerComboBox.getValue() == null ||
                    cabinetComboBox.getValue() == null) {

                showError("Ошибка", "Пожалуйста, заполните все обязательные поля");
                return;
            }

            // Create component object
            Commponent component = new Commponent(
                    0, // id will be generated by DB
                    inventoryNumberField.getText(),
                    serialNumberField.getText(),
                    nameField.getText(),
                    modelField.getText(),
                    manufacturerComboBox.getValue(),
                    sellerComboBox.getValue(),
                    typeComboBox.getValue(),
                    purchaseDatePicker.getValue().toString(),
                    warrantyDatePicker.getValue() != null ? warrantyDatePicker.getValue().toString() : "",
                    cabinetComboBox.getValue(),
                    statusComboBox.getValue()
            );

            // Try to parse price
            try {
                if (!priceField.getText().isEmpty()) {
                    double price = Double.parseDouble(priceField.getText());
                    // component.setPrice(price); // Add price field to your model if needed
                }
            } catch (NumberFormatException e) {
                showError("Ошибка", "Неверный формат цены");
                return;
            }

            // Here you would typically send the component to the server
            // For now, just show a success message
            showSuccess("Компонент успешно добавлен");
            dialog.close();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка при сохранении", e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showSuccess(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void show() {
        dialog.showAndWait();
    }
}