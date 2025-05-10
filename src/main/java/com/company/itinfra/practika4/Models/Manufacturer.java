package com.company.itinfra.practika4.Models;

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private int id;
    private String name;
    private String website;
    private String country;
    private String created_at;
    private String updated_at;

    public Manufacturer(int id, String name, String website, String country, String created_at) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.country = country;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getWebsite() {
        return website;
    }
    public String getCountry() {
        return country;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
