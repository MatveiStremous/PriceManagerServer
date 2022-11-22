package com.example.pricemanager.entity;

import java.io.Serializable;

public class Company implements Serializable {
    private int id;
    private String name;
    private float balance;
    private int amountOfProducts;
    private int userId;

    public Company() {
    }

    public Company(int id, String name, float balance, int userId) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.userId = userId;
    }

    public Company(String name, float balance, int userId) {
        this.name = name;
        this.balance = balance;
        this.userId = userId;
    }

    public int getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(int amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
