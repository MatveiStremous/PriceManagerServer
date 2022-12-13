package com.example.pricemanager.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ChartDto implements Serializable {
    private double value;
    private LocalDate date;

    public ChartDto() {
    }

    public ChartDto(double value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public double getPrice() {
        return value;
    }

    public void setPrice(double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
