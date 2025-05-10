package com.company.itinfra.practika4.Controllers;

import com.company.itinfra.practika4.DataBase.NetworkManager;
import com.company.itinfra.practika4.Models.Commponent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Devices implements StageAwareController {
    @FXML private TableView<Commponent> componentsTable;

    @FXML private TableColumn<Commponent, Integer> colId;
    @FXML private TableColumn<Commponent, String> colInventoryNumber;
    @FXML private TableColumn<Commponent, String> colSerialNumber;
    @FXML private TableColumn<Commponent, String> colName;
    @FXML private TableColumn<Commponent, String> colModel;
    @FXML private TableColumn<Commponent, String> colManufacturer;
    @FXML private TableColumn<Commponent, String> colSeller;
    @FXML private TableColumn<Commponent, String> colType;
    @FXML private TableColumn<Commponent, String> colPurchaseDate;
    @FXML private TableColumn<Commponent, String> colWarrantyDate;
    @FXML private TableColumn<Commponent, String> colOffice;
    @FXML private TableColumn<Commponent, Integer> colFloor;
    @FXML private TableColumn<Commponent, String> colCabinet;
    @FXML private TableColumn<Commponent, String> colStatus;

    private NetworkManager networkManager = new NetworkManager();


    private Stage primaryStage;

    @FXML
    public void initialize() {
        // Инициализация колонок таблицы
        if (!networkManager.connect()) {
            new Alert(Alert.AlertType.ERROR, "Не удалось подключиться к серверу").show();
            System.exit(1);
        }


        loadComponentsData();
        setupTableColumns();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInventoryNumber.setCellValueFactory(new PropertyValueFactory<>("inventoryNumber"));
        colSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colManufacturer.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getManufacturer().getName()));
        colSeller.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSeller().getName()));
        colType.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getType().getName()));
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        colWarrantyDate.setCellValueFactory(new PropertyValueFactory<>("warrantyEndDate"));
        colOffice.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCabinet().getFloor().getOffice().getNameOffice()));
        colFloor.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCabinet().getFloor().getNumber()).asObject());
        colCabinet.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCabinet().getNumber())));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void loadComponentsData() {
        try{
            List<Commponent> components = networkManager.getComponents();
            ObservableList<Commponent> data = FXCollections.observableArrayList(components);
            componentsTable.setItems(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void handleAddDevice() {
        /*AddComponentDialog dialog = new AddComponentDialog();
        boolean isSaved = dialog.showAndWait();

        if (isSaved) {
            refreshComponentsData();
        }*/
    }

    private void refreshComponentsData() {
        /*
        componentsTable.getItems().clear();
        loadComponentsData();*/
    }
}