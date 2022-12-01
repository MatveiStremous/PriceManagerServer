package com.example.pricemanager.entity;

import java.io.Serializable;

public class PriceCalculation implements Serializable {
    private int id;
    private int userId;
    private double averageCost;
    private float increasePerc;
    private float taxPerc;
    private double result;

    public PriceCalculation() {
    }

    public PriceCalculation(int userId, double averageCost, float increasePerc, float taxPerc) {
        this.userId = userId;
        this.averageCost = averageCost;
        this.increasePerc = increasePerc;
        this.taxPerc = taxPerc;
    }

    public PriceCalculation(int userId, double averageCost, float increasePerc, float taxPerc, double result) {
        this.userId = userId;
        this.averageCost = averageCost;
        this.increasePerc = increasePerc;
        this.taxPerc = taxPerc;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }

    public float getIncreasePerc() {
        return increasePerc;
    }

    public void setIncreasePerc(float increasePerc) {
        this.increasePerc = increasePerc;
    }

    public float getTaxPerc() {
        return taxPerc;
    }

    public void setTaxPerc(float taxPerc) {
        this.taxPerc = taxPerc;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
