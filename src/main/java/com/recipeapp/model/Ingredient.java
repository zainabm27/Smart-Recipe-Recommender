package com.recipeapp.model;

public class Ingredient {
    private String name;
    private String category;
    private int averageShelfLife; // in days
    
    public Ingredient(String name, String category, int averageShelfLife) {
        this.name = name;
        this.category = category;
        this.averageShelfLife = averageShelfLife;
    }
    
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getAverageShelfLife() { return averageShelfLife; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingredient that = (Ingredient) obj;
        return name.equalsIgnoreCase(that.name);
    }
    
    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}