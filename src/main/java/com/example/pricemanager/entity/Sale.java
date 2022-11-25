package com.example.pricemanager.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Sale implements Serializable {
    private int id;
    private int amount;
    private float totalPrice;
    private LocalDate date;
    private int productId;

    public Sale() {
    }

    public Sale(int id, int amount, float totalPrice, LocalDate date, int productId) {
        this.id = id;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.date = date;
        this.productId = productId;
    }

    public Sale(int amount, float totalPrice, LocalDate date, int productId) {
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.date = date;
        this.productId = productId;
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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
