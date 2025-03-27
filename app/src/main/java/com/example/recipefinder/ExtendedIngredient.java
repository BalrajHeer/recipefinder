package com.example.recipefinder;

import java.io.Serializable;

public class ExtendedIngredient implements Serializable {
    private String name;
    private double amount;
    private String unit;
    private String original;

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getOriginal() {
        return original;
    }
}