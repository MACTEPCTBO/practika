package com.company.itinfra.practika4.Models;

import java.io.Serializable;

public class Seller implements Serializable {
    private int id;
    private String name;
    private String legalName;
    private String phone;
    private String email;
    private String address;
    private String website;

    private String created_at;
    private String updated_at;

    public Seller(int id, String name, String legalName, String phone, String email, String address, String website, String created_at) {
        this.id = id;
        this.name = name;
        this.legalName = legalName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.website = website;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLegalName() {
        return legalName;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getWebsite() {
        return website;
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
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
