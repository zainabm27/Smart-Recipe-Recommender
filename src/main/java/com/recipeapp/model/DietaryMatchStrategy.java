package com.recipeapp.model;

import java.util.List;
import java.util.stream.Collectors;

public class DietaryMatchStrategy implements RecommendationStrategy {
    @Override
    public List<Recipe> generate(Pantry p, DietaryProfile d, List<Recipe> r) {
        return r.stream()
                .filter(d::isRecipeSafe)
                .collect(Collectors.toList());
    }
}