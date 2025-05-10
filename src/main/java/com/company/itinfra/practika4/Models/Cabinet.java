package com.company.itinfra.practika4.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Cabinet implements Serializable {
    private int id;
    private Floor floor;
    private int number;
    private String  description;

    private String date_at;
    private String time_at;

    public Cabinet(int id, Floor floor, int number, String description, String date_at, String time_at) {
        this.id = id;
        this.floor = floor;
        this.number = number;
        this.description = description;
        this.date_at = date_at;
        this.time_at = time_at;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

