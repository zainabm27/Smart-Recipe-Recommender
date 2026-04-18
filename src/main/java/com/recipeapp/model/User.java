package com.recipeapp.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private DietaryProfile profile;
    private Pantry pantry;
    private List<MealPlan> mealPlans;
    private List<Recipe> favorites;
    
    public User(String username, DietaryProfile profile) {
        this.username = username;
        this.profile = profile;
        this.pantry = new Pantry();
        this.mealPlans = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }
    
    public boolean canEat(Recipe recipe) {
        return profile.isRecipeSafe(recipe);
    }
    
    public void addToFavorites(Recipe recipe) {
        if (!favorites.contains(recipe)) {
            favorites.add(recipe);
        }
    }
    
    public void addMealPlan(MealPlan mealPlan) {
        mealPlans.add(mealPlan);
    }
    
    public String getUsername() { return username; }
    public DietaryProfile getProfile() { return profile; }
    public Pantry getPantry() { return pantry; }
    public List<MealPlan> getMealPlans() { return mealPlans; }
    public List<Recipe> getFavorites() { return favorites; }
}