package com.recipeapp.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private List<ShoppingListItem> items;
    private String status; // "Active", "Completed"
    private String name;
    
    public ShoppingList() {
        this("Shopping List");
    }
    
    public ShoppingList(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.status = "Active";
    }
    
    public void addItem(ShoppingListItem item) {
        // Check if item already exists
        for (ShoppingListItem existing : items) {
            if (existing.getIngredient().equals(item.getIngredient())) {
                existing.addQuantity(item.getQuantity());
                return;
            }
        }
        items.add(item);
    }
    
    public void markAsCompleted() {
        this.status = "Completed";
    }
    
    public List<ShoppingListItem> getUnpurchasedItems() {
        List<ShoppingListItem> unpurchased = new ArrayList<>();
        for (ShoppingListItem item : items) {
            if (!item.isPurchased()) {
                unpurchased.add(item);
            }
        }
        return unpurchased;
    }
    
    public List<ShoppingListItem> getItems() { return items; }
    public String getStatus() { return status; }
    public String getName() { return name; }
}