package com.recipeapp.model;

public class RecipeIngredient {
    private Ingredient ingredient;
    private float quantity;
    private String unit;
    private boolean isOptional;
    
    public RecipeIngredient(Ingredient ingredient, float quantity, String unit) {
        this(ingredient, quantity, unit, false);
    }
    
    public RecipeIngredient(Ingredient ingredient, float quantity, String unit, boolean isOptional) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.isOptional = isOptional;
    }
    
    public void scaleQuantity(float factor) {
        this.quantity *= factor;
    }
    
    public Ingredient getIngredient() { return ingredient; }
    public float getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public boolean isOptional() { return isOptional; }
}