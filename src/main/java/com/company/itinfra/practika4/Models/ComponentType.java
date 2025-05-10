package com.company.itinfra.practika4.Models;

import java.io.Serializable;

public class ComponentType implements Serializable {
    private int Id;
    private String Name;
    private String Description;
    private String Date_at;
    private String Time_at;

    public ComponentType(int id, String name, String description, String date_at, String time_at) {
        Id = id;
        Name = name;
        Description = description;
        Date_at = date_at;
        Time_at = time_at;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate_at() {
        return Date_at;
    }

    public void setDate_at(String date_at) {
        Date_at = date_at;
    }

    public String getTime_at() {
        return Time_at;
    }

    public void setTime_at(String time_at) {
        Time_at = time_at;
    }
}
