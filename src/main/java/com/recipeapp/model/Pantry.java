package com.recipeapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pantry {
    private List<PantryItem> items;
    
    public Pantry() {
        this.items = new ArrayList<>();
    }
    
    public void addPantryItem(Ingredient ingredient, float quantity, String unit, LocalDate expiryDate) {
        this.items.add(new PantryItem(ingredient, quantity, unit, expiryDate));
    }
    
    public List<PantryItem> getExpiringItems(int daysThreshold) {
        return items.stream()
            .filter(item -> item.isExpiringSoon(daysThreshold))
            .collect(Collectors.toList());
    }
    
    public List<PantryItem> getItemsByWasteRisk() {
        return items.stream()
            .sorted((a, b) -> Integer.compare(b.getWasteRiskScore(), a.getWasteRiskScore()))
            .collect(Collectors.toList());
    }
    
    public boolean hasIngredient(Ingredient ingredient) {
        return items.stream()
            .anyMatch(item -> item.getIngredient().equals(ingredient) && item.getQuantity() > 0);
    }
    
    public PantryItem findItem(Ingredient ingredient) {
        return items.stream()
            .filter(item -> item.getIngredient().equals(ingredient) && item.getQuantity() > 0)
            .findFirst()
            .orElse(null);
    }
    
    public int calculateTotalWasteRiskScore() {
        return items.stream()
            .mapToInt(PantryItem::getWasteRiskScore)
            .sum();
    }
    
    public List<PantryItem> getAllItems() { return new ArrayList<>(items); }
    
    public void removeItem(PantryItem item) {
        items.remove(item);
    }
}