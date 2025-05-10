package com.company.itinfra.practika4.Models;

import java.io.Serializable;

public class Office implements Serializable {
    int id;
    String nameOffice;
    String shortNameOffice;
    String description;
    String created_at;
    String updated_at;

    public Office(int id, String nameOffice, String shortNameOffice, String description, String created_at) {
        this.id = id;
        this.nameOffice = nameOffice;
        this.shortNameOffice = shortNameOffice;
        this.description = description;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getNameOffice() {
        return nameOffice;
    }

    public void setNameOffice(String nameOffice) {
        this.nameOffice = nameOffice;
    }

    public String getShortNameOffice() {
        return shortNameOffice;
    }

    public void setShortNameOffice(String shortNameOffice) {
        this.shortNameOffice = shortNameOffice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
