package com.company.itinfra.practika4.Models;

import java.io.Serializable;

public class ComponentDTO implements Serializable {
    private int id;
    private String inventoryNumber;
    private String serialNumber;
    private String name;
    private String model;
    private Manufacturer manufacturer;
    private Seller seller;
    private ComponentType type;
    private String purchaseDate;
    private String warrantyEndDate;
    private Cabinet cabinet;
    private String status;

    // Конструктор, геттеры и сеттеры

    public ComponentDTO(Commponent component) {
        this.id = component.getId();
        this.inventoryNumber = component.getInventoryNumber();
        this.serialNumber = component.getSerialNumber();
        this.name = component.getName();
        this.model = component.getModel();
        this.manufacturer = component.getManufacturer();
        this.seller = component.getSeller();
        this.purchaseDate = component.getPurchaseDate();
        this.warrantyEndDate = component.getWarrantyEndDate();
        this.cabinet = component.getCabinet();
        this.status = component.getStatus();
        this.type = component.getType();
        // ... остальные поля
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        System.out.println(manufacturer.getName());
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(String warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}