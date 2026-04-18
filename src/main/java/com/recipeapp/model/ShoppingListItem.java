package com.recipeapp.model;

public class ShoppingListItem {
    private Ingredient ingredient;
    private float quantity;
    private String unit;
    private boolean isPurchased;
    
    public ShoppingListItem(Ingredient ingredient, float quantity, String unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.isPurchased = false;
    }
    
    public void togglePurchasedStatus() {
        this.isPurchased = !this.isPurchased;
    }
    
    public void addQuantity(float additional) {
        this.quantity += additional;
    }
    
    public Ingredient getIngredient() { return ingredient; }
    public float getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public boolean isPurchased() { return isPurchased; }
}