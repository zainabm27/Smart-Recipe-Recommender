package com.recipeapp.model;

public class FlavorPreference {
    private String ingredientName;
    private int weight; // -10 (hate) to +10 (love)
    
    public FlavorPreference(String ingredientName, int weight) {
        this.ingredientName = ingredientName;
        this.weight = weight;
    }
    
    public void updateWeight(int value) {
        if (value >= -10 && value <= 10) {
            this.weight = value;
        }
    }
    
    public String getIngredientName() { return ingredientName; }
    public int getWeight() { return weight; }
}