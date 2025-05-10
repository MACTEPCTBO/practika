package com.company.itinfra.practika4.Models;

import javafx.beans.property.*;

import java.io.Serializable;

public class Commponent implements Serializable {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty inventoryNumber = new SimpleStringProperty();
    private final StringProperty serialNumber = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty model = new SimpleStringProperty();

    private final ObjectProperty<Manufacturer> manufacturer = new SimpleObjectProperty<>();
    private final ObjectProperty<Seller> seller = new SimpleObjectProperty<>();
    private final ObjectProperty<ComponentType> type = new SimpleObjectProperty<>();
    private final StringProperty purchaseDate = new SimpleStringProperty();
    private final StringProperty warrantyEndDate = new SimpleStringProperty();
    private final ObjectProperty<Cabinet> cabinet = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();

    public Commponent(int id, String inventoryNumber, String serialNumber, String name, String model,
                      Manufacturer manufacturer, Seller seller, ComponentType type,
                      String purchaseDate, String warrantyEndDate, Cabinet cabinet, String status) {
        setId(id);
        setInventoryNumber(inventoryNumber);
        setSerialNumber(serialNumber);
        setName(name);
        setModel(model);
        setManufacturer(manufacturer);
        setSeller(seller);
        setType(type);
        setPurchaseDate(purchaseDate);
        setWarrantyEndDate(warrantyEndDate);
        setCabinet(cabinet);
        setStatus(status);
    }

    // Геттеры для свойств
    public IntegerProperty idProperty() { return id; }
    public StringProperty inventoryNumberProperty() { return inventoryNumber; }
    public StringProperty serialNumberProperty() { return serialNumber; }
    public StringProperty nameProperty() { return name; }
    public StringProperty modelProperty() { return model; }
    public ObjectProperty<Manufacturer> manufacturerProperty() { return manufacturer; }
    public ObjectProperty<Seller> sellerProperty() { return seller; }
    public ObjectProperty<ComponentType> typeProperty() { return type; }
    public StringProperty purchaseDateProperty() { return purchaseDate; }
    public StringProperty warrantyEndDateProperty() { return warrantyEndDate; }
    public ObjectProperty<Cabinet> cabinetProperty() { return cabinet; }
    public StringProperty statusProperty() { return status; }

    // Стандартные геттеры
    public int getId() { return id.get(); }
    public String getInventoryNumber() { return inventoryNumber.get(); }
    public String getSerialNumber() { return serialNumber.get(); }
    public String getName() { return name.get(); }
    public String getModel() { return model.get(); }
    public Manufacturer getManufacturer() { return manufacturer.get(); }
    public Seller getSeller() { return seller.get(); }
    public ComponentType getType() { return type.get(); }
    public String getPurchaseDate() { return purchaseDate.get(); }
    public String getWarrantyEndDate() { return warrantyEndDate.get(); }
    public Cabinet getCabinet() { return cabinet.get(); }
    public String getStatus() { return status.get(); }

    // Сеттеры
    public void setId(int id) { this.id.set(id); }
    public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber.set(inventoryNumber); }
    public void setSerialNumber(String serialNumber) { this.serialNumber.set(serialNumber); }
    public void setName(String name) { this.name.set(name); }
    public void setModel(String model) { this.model.set(model); }
    public void setManufacturer(Manufacturer manufacturer) { this.manufacturer.set(manufacturer); }
    public void setSeller(Seller seller) { this.seller.set(seller); }
    public void setType(ComponentType type) { this.type.set(type); }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate.set(purchaseDate); }
    public void setWarrantyEndDate(String warrantyEndDate) { this.warrantyEndDate.set(warrantyEndDate); }
    public void setCabinet(Cabinet cabinet) { this.cabinet.set(cabinet); }
    public void setStatus(String status) { this.status.set(status); }


}