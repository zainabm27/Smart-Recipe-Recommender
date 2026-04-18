package com.recipeapp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class DietaryProfile {
    private String profileType; // "Vegan", "Keto", "Vegetarian", "None"
    private String strictnessLevel; // "Strict", "Moderate", "Flexible"
    private List<HealthRestriction> restrictions;
    private Map<String, Integer> flavorWeights; // -10 (hate) to +10 (love)
    private List<String> dislikedIngredients;
    
    public DietaryProfile(String profileType, String strictnessLevel) {
        this.profileType = profileType;
        this.strictnessLevel = strictnessLevel;
        this.restrictions = new ArrayList<>();
        this.flavorWeights = new HashMap<>();
        this.dislikedIngredients = new ArrayList<>();
    }
    
    public boolean isRecipeSafe(Recipe recipe) {
        // Check lethal allergies first
        for (HealthRestriction restriction : restrictions) {
            if (restriction.getSeverityLevel() >= 10) { // Lethal
                if (recipe.containsIngredient(restriction.getAllergenName())) {
                    return false;
                }
            }
        }
        
        // Check dietary profile constraints
        for (RecipeIngredient ri : recipe.getIngredients()) {
            Ingredient ingredient = ri.getIngredient();
            
            switch (profileType.toLowerCase()) {
                case "vegan":
                    if (isAnimalProduct(ingredient)) return false;
                    break;
                case "vegetarian":
                    if (isMeat(ingredient)) return false;
                    break;
                case "keto":
                    if (isHighCarb(ingredient)) return false;
                    break;
            }
        }
        
        // Check mild allergies/intolerances (non-lethal)
        for (HealthRestriction restriction : restrictions) {
            if (restriction.getSeverityLevel() < 10) {
                if (recipe.containsIngredient(restriction.getAllergenName())) {
                    if (strictnessLevel.equalsIgnoreCase("Strict")) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public int calculatePreferenceScore(Recipe recipe) {
        int score = 0;
        
        // Add flavor weights
        for (RecipeIngredient ri : recipe.getIngredients()) {
            String ingredientName = ri.getIngredient().getName();
            score += flavorWeights.getOrDefault(ingredientName, 0);
            
            // Penalize disliked ingredients
            if (dislikedIngredients.contains(ingredientName)) {
                score -= 5;
            }
        }
        
        return score;
    }
    
    private boolean isAnimalProduct(Ingredient ingredient) {
        String category = ingredient.getCategory().toLowerCase();
        return category.equals("meat") || category.equals("dairy") || 
               category.equals("eggs") || category.equals("seafood");
    }
    
    private boolean isMeat(Ingredient ingredient) {
        String category = ingredient.getCategory().toLowerCase();
        return category.equals("meat") || category.equals("seafood");
    }
    
    private boolean isHighCarb(Ingredient ingredient) {
        String category = ingredient.getCategory().toLowerCase();
        return category.equals("grains") || category.equals("sugars") || 
               ingredient.getName().equalsIgnoreCase("potato");
    }
    
    public void addRestriction(HealthRestriction restriction) {
        restrictions.add(restriction);
    }
    
    public void setFlavorPreference(String ingredientName, int weight) {
        flavorWeights.put(ingredientName, weight);
    }
    
    public void addDislikedIngredient(String ingredientName) {
        dislikedIngredients.add(ingredientName);
    }
    
    public String getProfileType() { return profileType; }
    public String getStrictnessLevel() { return strictnessLevel; }
}