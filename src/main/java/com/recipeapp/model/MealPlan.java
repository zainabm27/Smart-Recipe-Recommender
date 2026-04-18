package com.recipeapp.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class MealPlan {
    private List<Recipe> scheduledRecipes;
    private LocalDate startDate;
    private LocalDate endDate;
    private String planName;
    
    public MealPlan(String planName, LocalDate startDate, LocalDate endDate) {
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scheduledRecipes = new ArrayList<>();
    }
    
    public ShoppingList generateGroceryList(Pantry currentPantry) {
        ShoppingList shoppingList = new ShoppingList();
        
        for (Recipe recipe : scheduledRecipes) {
            for (RecipeIngredient ri : recipe.getIngredients()) {
                if (!ri.isOptional()) {
                    PantryItem existingItem = currentPantry.findItem(ri.getIngredient());
                    if (existingItem == null || existingItem.getQuantity() < ri.getQuantity()) {
                        float needed = ri.getQuantity();
                        if (existingItem != null) {
                            needed -= existingItem.getQuantity();
                        }
                        shoppingList.addItem(new ShoppingListItem(ri.getIngredient(), needed, ri.getUnit()));
                    }
                }
            }
        }
        
        return shoppingList;
    }
    
    public void addRecipe(Recipe recipe) {
        scheduledRecipes.add(recipe);
    }
    
    public List<Recipe> getScheduledRecipes() { return scheduledRecipes; }
    public String getPlanName() { return planName; }
}