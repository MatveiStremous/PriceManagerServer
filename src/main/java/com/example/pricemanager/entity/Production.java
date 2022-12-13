package com.example.pricemanager.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Production implements Serializable, Comparable<Production>{
    private int id;
    private int amount;
    private double totalCosts;
    private LocalDate date;
    private int productId;

    public Production(int id, double totalCosts, LocalDate date, int productId) {
        this.id = id;
        this.totalCosts = totalCosts;
        this.date = date;
        this.productId = productId;
    }

    public Production(int id, int amount, double totalCosts, LocalDate date, int productId) {
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

    public double getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(double totalCosts) {
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

    @Override
    public int compareTo(Production production) {
        if (production.getDate().isEqual(date)) {
            return 0;
        }
        if (production.getDate().isAfter(date)) {
            return -1;
        }
        return 1;
    }
}
