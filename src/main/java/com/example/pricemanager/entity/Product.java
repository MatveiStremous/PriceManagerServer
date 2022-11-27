package com.example.pricemanager.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int amount;
    private double averageCost;
    private double averageSellingPrice;
    private int companyId;

    public Product(String name, int amount, double averageCost, double averageSellingPrice, int companyId) {
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

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }

    public double getAverageSellingPrice() {
        return averageSellingPrice;
    }

    public void setAverageSellingPrice(double averageSellingPrice) {
        this.averageSellingPrice = averageSellingPrice;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
