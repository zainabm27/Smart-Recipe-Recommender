package com.recipeapp.model;

public class SubstitutionRule {
    private Ingredient original;
    private Ingredient replacement;
    private float ratio;
    private String preparationNote;
    private boolean requiresCombination;
    
    public SubstitutionRule(Ingredient original, Ingredient replacement, float ratio) {
        this(original, replacement, ratio, null, false);
    }
    
    public SubstitutionRule(Ingredient original, Ingredient replacement, float ratio, 
                           String preparationNote, boolean requiresCombination) {
        this.original = original;
        this.replacement = replacement;
        this.ratio = ratio;
        this.preparationNote = preparationNote;
        this.requiresCombination = requiresCombination;
    }
    
    public float applyRatio(float originalQuantity) {
        return originalQuantity * this.ratio;
    }
    
    public Ingredient getOriginal() { return original; }
    public Ingredient getReplacement() { return replacement; }
    public float getRatio() { return ratio; }
    public String getPreparationNote() { return preparationNote; }
    public boolean requiresCombination() { return requiresCombination; }
}