package com.recipeapp.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class RecommenderController {
    private RecommendationStrategy strategy;
    private RecipeRepository repository;
    private SubstitutionEngine substitutionEngine;
    
    public RecommenderController(RecommendationStrategy strategy, RecipeRepository repository) {
        this.strategy = strategy;
        this.repository = repository;
        this.substitutionEngine = new SubstitutionEngine();
    }
    
    public List<Recipe> requestRecommendations(User user) {
        List<Recipe> allRecipes = repository.fetchAllRecipes();
        List<Recipe> recommended = strategy.generate(user.getPantry(), user.getProfile(), allRecipes);
        
        // Check for missing ingredients and suggest substitutions
        for (Recipe recipe : recommended) {
            checkAndSuggestSubstitutes(recipe, user.getPantry());
        }
        
        return recommended;
    }
    
    public List<RecipeRecommendation> getDetailedRecommendations(User user) {
        List<Recipe> allRecipes = repository.fetchAllRecipes();
        List<Recipe> recommended = strategy.generate(user.getPantry(), user.getProfile(), allRecipes);
        List<RecipeRecommendation> detailed = new ArrayList<>();
        
        for (Recipe recipe : recommended) {
            RecipeRecommendation rec = new RecipeRecommendation(recipe);
            
            // Check each ingredient
            for (RecipeIngredient ri : recipe.getIngredients()) {
                Ingredient ingredient = ri.getIngredient();
                PantryItem item = user.getPantry().findItem(ingredient);
                
                if (item != null && item.getQuantity() >= ri.getQuantity()) {
                    rec.addIngredientStatus(ingredient, IngredientStatus.HAVE_ENOUGH);
                } else if (item != null) {
                    rec.addIngredientStatus(ingredient, IngredientStatus.NEED_MORE);
                    rec.addMissingQuantity(ingredient, ri.getQuantity() - item.getQuantity());
                } else {
                    // Check for substitution
                    Ingredient substitute = substitutionEngine.findSubstitute(ingredient, user.getPantry());
                    if (substitute != null) {
                        rec.addIngredientStatus(ingredient, IngredientStatus.CAN_SUBSTITUTE);
                        rec.addSubstitutionNote(ingredient, "Use " + substitute.getName() + " instead");
                    } else if (ri.isOptional()) {
                        rec.addIngredientStatus(ingredient, IngredientStatus.OPTIONAL);
                    } else {
                        rec.addIngredientStatus(ingredient, IngredientStatus.MISSING);
                    }
                }
            }
            
            // Calculate waste reduction score
            int wasteScore = calculateWasteReductionScore(recipe, user.getPantry());
            rec.setWasteReductionScore(wasteScore);
            
            // Calculate preference score
            int preferenceScore = user.getProfile().calculatePreferenceScore(recipe);
            rec.setPreferenceScore(preferenceScore);
            
            detailed.add(rec);
        }
        
        return detailed;
    }
    
    private int calculateWasteReductionScore(Recipe recipe, Pantry pantry) {
        return pantry.getExpiringItems(3).stream()
            .filter(item -> recipe.containsIngredient(item.getIngredient().getName()))
            .mapToInt(PantryItem::getWasteRiskScore)
            .sum();
    }
    
    private void checkAndSuggestSubstitutes(Recipe recipe, Pantry pantry) {
        for (RecipeIngredient ri : recipe.getIngredients()) {
            if (!pantry.hasIngredient(ri.getIngredient())) {
                Ingredient substitute = substitutionEngine.findSubstitute(ri.getIngredient(), pantry);
                if (substitute != null) {
                    System.out.println("Tip for '" + recipe.getName() + "': Use " + 
                                     substitute.getName() + " instead of " + 
                                     ri.getIngredient().getName());
                }
            }
        }
    }
    
    public void setStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public enum IngredientStatus {
        HAVE_ENOUGH, NEED_MORE, MISSING, CAN_SUBSTITUTE, OPTIONAL
    }
    
    public class RecipeRecommendation {
        private Recipe recipe;
        private Map<Ingredient, IngredientStatus> ingredientStatus;
        private Map<Ingredient, Float> missingQuantities;
        private Map<Ingredient, String> substitutionNotes;
        private int wasteReductionScore;
        private int preferenceScore;
        
        public RecipeRecommendation(Recipe recipe) {
            this.recipe = recipe;
            this.ingredientStatus = new HashMap<>();
            this.missingQuantities = new HashMap<>();
            this.substitutionNotes = new HashMap<>();
        }
        
        public void addIngredientStatus(Ingredient ingredient, IngredientStatus status) {
            ingredientStatus.put(ingredient, status);
        }
        
        public void addMissingQuantity(Ingredient ingredient, float quantity) {
            missingQuantities.put(ingredient, quantity);
        }
        
        public void addSubstitutionNote(Ingredient ingredient, String note) {
            substitutionNotes.put(ingredient, note);
        }
        
        public boolean canMakeWithSubstitutions() {
            for (Map.Entry<Ingredient, IngredientStatus> entry : ingredientStatus.entrySet()) {
                if (entry.getValue() == IngredientStatus.MISSING) {
                    return false;
                }
            }
            return true;
        }
        
        public List<String> getShoppingList() {
            List<String> shoppingList = new ArrayList<>();
            for (Map.Entry<Ingredient, Float> entry : missingQuantities.entrySet()) {
                shoppingList.add(entry.getKey().getName() + " (" + entry.getValue() + " units needed)");
            }
            return shoppingList;
        }
        
        // Get substitutions as a Map of Ingredient to substitution note string
        public Map<Ingredient, String> getSubstitutions() {
            return substitutionNotes;
        }
        
        // Alternative: Get substitutions as a formatted list
        public List<String> getSubstitutionList() {
            List<String> substitutions = new ArrayList<>();
            for (Map.Entry<Ingredient, String> entry : substitutionNotes.entrySet()) {
                substitutions.add(entry.getKey().getName() + " → " + entry.getValue());
            }
            return substitutions;
        }
        
        // Getters
        public Recipe getRecipe() { return recipe; }
        public int getWasteReductionScore() { return wasteReductionScore; }
        public int getPreferenceScore() { return preferenceScore; }
        public void setWasteReductionScore(int score) { this.wasteReductionScore = score; }
        public void setPreferenceScore(int score) { this.preferenceScore = score; }
        public Map<Ingredient, IngredientStatus> getIngredientStatus() { return ingredientStatus; }
        public Map<Ingredient, Float> getMissingQuantities() { return missingQuantities; }
    }
}