package com.example.pricemanager.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Sale implements Serializable, Comparable<Sale> {
    private int id;
    private int amount;
    private double totalPrice;
    private LocalDate date;
    private int productId;

    public Sale() {
    }

    public Sale(int id, int amount, double totalPrice, LocalDate date, int productId) {
        this.id = id;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.date = date;
        this.productId = productId;
    }

    public Sale(int amount, double totalPrice, LocalDate date, int productId) {
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
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

    @Override
    public int compareTo(Sale sale) {
        if (sale.getDate().isEqual(date)) {
            return 0;
        }
        if (sale.getDate().isAfter(date)) {
            return -1;
        }
        return 1;
    }
}
