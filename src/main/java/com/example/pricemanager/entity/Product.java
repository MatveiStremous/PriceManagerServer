package com.example.pricemanager.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int amount;
    private float averageCost;
    private float averageSellingPrice;
    private int companyId;

    public Product(String name, int amount, float averageCost, float averageSellingPrice, int companyId) {
        this.name = name;
        this.amount = amount;
        this.averageCost = averageCost;
        this.averageSellingPrice = averageSellingPrice;
        this.companyId = companyId;
    }

    public Product(String name, int amount, int companyId) {
        this.name = name;
        this.amount = amount;
        this.companyId = companyId;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(float averageCost) {
        this.averageCost = averageCost;
    }

    public float getAverageSellingPrice() {
        return averageSellingPrice;
    }

    public void setAverageSellingPrice(float averageSellingPrice) {
        this.averageSellingPrice = averageSellingPrice;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
