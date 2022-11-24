package com.example.pricemanager.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Production implements Serializable {
    private int id;
    private int amount;
    private float totalCosts;
    private LocalDate date;
    private int productId;

    public Production(int id, float totalCosts, LocalDate date, int productId) {
        this.id = id;
        this.totalCosts = totalCosts;
        this.date = date;
        this.productId = productId;
    }

    public Production(int id, int amount, float totalCosts, LocalDate date, int productId) {
        this.id = id;
        this.amount = amount;
        this.totalCosts = totalCosts;
        this.date = date;
        this.productId = productId;
    }

    public Production() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(float totalCosts) {
        this.totalCosts = totalCosts;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
