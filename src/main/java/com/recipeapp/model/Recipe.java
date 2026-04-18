package com.recipeapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Recipe {
    private String name;
    private List<RecipeIngredient> ingredients;
    private int baseServings;
    private List<InstructionStep> steps;
    private String cuisine;
    private int prepTime;
    private int cookTime;
    
    public Recipe(String name, int baseServings, String cuisine) {
        this.name = name;
        this.baseServings = baseServings;
        this.cuisine = cuisine;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }
    
    public List<InstructionStep> getSteps() { 
        return steps; 
    }
    
    public Recipe scaleRecipe(int targetServings) {
        float factor = (float) targetServings / baseServings;
        Recipe scaledRecipe = new Recipe(this.name, targetServings, this.cuisine);
        
        for (RecipeIngredient ri : ingredients) {
            RecipeIngredient scaledRI = new RecipeIngredient(
                ri.getIngredient(),
                ri.getQuantity() * factor,
                ri.getUnit(),
                ri.isOptional()
            );
            scaledRecipe.addIngredient(scaledRI);
        }
        
        scaledRecipe.steps = new ArrayList<>(this.steps);
        scaledRecipe.prepTime = this.prepTime;
        scaledRecipe.cookTime = this.cookTime;
        
        return scaledRecipe;
    }
    
    public void addIngredient(RecipeIngredient ingredient) {
        ingredients.add(ingredient);
    }
    
    public void addStep(InstructionStep step) {
        steps.add(step);
    }
    
    public boolean containsIngredient(String ingredientName) {
        return ingredients.stream()
            .anyMatch(ri -> ri.getIngredient().getName().equalsIgnoreCase(ingredientName));
    }
    
    public List<Ingredient> getRequiredIngredients() {
        return ingredients.stream()
            .filter(ri -> !ri.isOptional())
            .map(RecipeIngredient::getIngredient)
            .collect(Collectors.toList());
    }
    
    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public String getName() { return name; }
    public int getBaseServings() { return baseServings; }
    public String getCuisine() { return cuisine; }
    public int getTotalTime() { return prepTime + cookTime; }
}