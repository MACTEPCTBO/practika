package com.company.itinfra.practika4.Models;

import javafx.beans.property.ObjectProperty;

import java.io.Serializable;

public class Floor implements Serializable {
    private int Id;
    private Office office;
    private int number;
    private String description;

    private String date_at;
    private String time_at;

    public Floor(int id, Office office, int number, String description, String date_at, String time_at) {
        Id = id;
        this.office = office;
        this.number = number;
        this.description = description;
        this.date_at = date_at;
        this.time_at = time_at;
    }

    public int getId() {
        return Id;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_at() {
        return date_at;
    }

    public void setDate_at(String date_at) {
        this.date_at = date_at;
    }

    public String getTime_at() {
        return time_at;
    }

    public void setTime_at(String time_at) {
        this.time_at = time_at;
    }
}
