package com.recipeapp.model;

import java.util.List;

public class Equipment {
    private String name;
    private String category;
    
    public Equipment(String name, String category) {
        this.name = name;
        this.category = category;
    }
    
    public boolean isAvailable(List<Equipment> kitchenInventory) {
        return kitchenInventory.stream().anyMatch(e -> e.name.equals(this.name));
    }
    
    public String getName() { return name; }
    public String getCategory() { return category; }
}