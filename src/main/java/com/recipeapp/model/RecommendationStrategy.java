package com.recipeapp.model;

import java.util.List;

public interface RecommendationStrategy {
    List<Recipe> generate(Pantry pantry, DietaryProfile profile, List<Recipe> allRecipes);
}