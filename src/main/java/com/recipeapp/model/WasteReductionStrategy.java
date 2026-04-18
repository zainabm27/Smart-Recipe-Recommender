package com.recipeapp.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WasteReductionStrategy implements RecommendationStrategy {
    
    @Override
    public List<Recipe> generate(Pantry pantry, DietaryProfile profile, List<Recipe> allRecipes) {
        List<PantryItem> expiringItems = pantry.getExpiringItems(3);
        
        return allRecipes.stream()
            .filter(profile::isRecipeSafe)
            .map(recipe -> new RecipeWithWasteScore(recipe, calculateWasteReductionScore(recipe, pantry)))
            .sorted(Comparator.comparingInt(RecipeWithWasteScore::getWasteScore).reversed())
            .map(RecipeWithWasteScore::getRecipe)
            .collect(Collectors.toList());
    }
    
    private int calculateWasteReductionScore(Recipe recipe, Pantry pantry) {
        int score = 0;
        
        for (RecipeIngredient ri : recipe.getIngredients()) {
            PantryItem item = pantry.findItem(ri.getIngredient());
            if (item != null) {
                // Higher score for using items that expire soon
                score += item.getWasteRiskScore();
            }
        }
        
        return score;
    }
    
    private class RecipeWithWasteScore {
        private Recipe recipe;
        private int wasteScore;
        
        RecipeWithWasteScore(Recipe recipe, int wasteScore) {
            this.recipe = recipe;
            this.wasteScore = wasteScore;
        }
        
        Recipe getRecipe() { return recipe; }
        int getWasteScore() { return wasteScore; }
    }
}