package com.recipeapp.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PantryItem {
    private Ingredient ingredient;
    private float quantity;
    private String unit;
    private LocalDate expiryDate;
    private LocalDate dateAdded;
    
    public PantryItem(Ingredient ingredient, float quantity, String unit, LocalDate expiryDate) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
        this.dateAdded = LocalDate.now();
    }
    
    public boolean isExpiringSoon(int thresholdDays) {
        if (expiryDate == null) return false;
        long daysUntilExpiry = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        return daysUntilExpiry <= thresholdDays && daysUntilExpiry >= 0;
    }
    
    public int getWasteRiskScore() {
        if (expiryDate == null) return 0;
        long daysUntilExpiry = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        if (daysUntilExpiry <= 1) return 100;
        if (daysUntilExpiry <= 3) return 70;
        if (daysUntilExpiry <= 5) return 40;
        if (daysUntilExpiry <= 7) return 20;
        return 0;
    }
    
    public void reduceQuantity(float amount) {
        this.quantity = Math.max(0, this.quantity - amount);
    }
    
    public Ingredient getIngredient() { return ingredient; }
    public float getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public LocalDate getExpiryDate() { return expiryDate; }
}