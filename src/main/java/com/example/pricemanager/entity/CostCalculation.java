package com.example.pricemanager.entity;

import java.io.Serializable;

public class CostCalculation implements Serializable {
    private int id;
    private int userId;
    private double materials;
    private double production;
    private double deprecation;
    private double salary;
    private double others;
    private double result;

    public CostCalculation(int userId, double materials, double production, double deprecation, double salary, double others, double result) {
        this.userId = userId;
        this.materials = materials;
        this.production = production;
        this.deprecation = deprecation;
        this.salary = salary;
        this.others = others;
        this.result = result;
    }

    public CostCalculation() {
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

    public double getMaterials() {
        return materials;
    }

    public void setMaterials(double materials) {
        this.materials = materials;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public double getDeprecation() {
        return deprecation;
    }

    public void setDeprecation(double deprecation) {
        this.deprecation = deprecation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getOthers() {
        return others;
    }

    public void setOthers(double others) {
        this.others = others;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
