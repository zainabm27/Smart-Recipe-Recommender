package com.recipeapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstitutionEngine {
    private List<SubstitutionRule> universalRules;
    private Map<String, List<SubstitutionRule>> substitutionMap;
    
    public SubstitutionEngine() {
        this.universalRules = new ArrayList<>();
        this.substitutionMap = new HashMap<>();
        initializeCommonSubstitutions();
    }
    
    private void initializeCommonSubstitutions() {
        // Buttermilk substitution
        Ingredient buttermilk = new Ingredient("Buttermilk", "Dairy", 7);
        Ingredient milk = new Ingredient("Milk", "Dairy", 7);
        
        SubstitutionRule buttermilkRule = new SubstitutionRule(buttermilk, milk, 1.0f);
        universalRules.add(buttermilkRule);
        
        // Smoked Paprika substitution
        Ingredient smokedPaprika = new Ingredient("Smoked Paprika", "Spice", 365);
        Ingredient regularPaprika = new Ingredient("Paprika", "Spice", 365);
        
        universalRules.add(new SubstitutionRule(smokedPaprika, regularPaprika, 1.0f));
        
        // Egg substitution for baking
        Ingredient egg = new Ingredient("Egg", "Eggs", 30);
        Ingredient flaxseed = new Ingredient("Ground Flaxseed", "Grain", 365);
        
        universalRules.add(new SubstitutionRule(egg, flaxseed, 0.25f));
    }
    
    public Ingredient findSubstitute(Ingredient missing, Pantry pantry) {
        for (SubstitutionRule rule : universalRules) {
            if (rule.getOriginal().equals(missing)) {
                Ingredient replacement = rule.getReplacement();
                if (pantry.hasIngredient(replacement)) {
                    return replacement;
                }
            }
        }
        return null; // No available substitute
    }
    
    public List<Ingredient> findAllSubstitutes(Ingredient missing, Pantry pantry) {
        List<Ingredient> substitutes = new ArrayList<>();
        for (SubstitutionRule rule : universalRules) {
            if (rule.getOriginal().equals(missing)) {
                if (pantry.hasIngredient(rule.getReplacement())) {
                    substitutes.add(rule.getReplacement());
                }
            }
        }
        return substitutes;
    }
    
    public float getAdjustedQuantity(Ingredient original, Ingredient substitute, float originalQuantity) {
        for (SubstitutionRule rule : universalRules) {
            if (rule.getOriginal().equals(original) && rule.getReplacement().equals(substitute)) {
                return originalQuantity * rule.getRatio();
            }
        }
        return originalQuantity; // Default to same quantity
    }
}