package com.recipeapp.model;

public class InstructionStep {
    private int stepNumber;
    private String description;
    private int duration; // minutes
    
    public InstructionStep(int stepNumber, String description, int duration) {
        this.stepNumber = stepNumber;
        this.description = description;
        this.duration = duration;
    }
    
    public int getEstimatedTime() { return this.duration; }
    
    public int getStepNumber() { return stepNumber; }
    public String getDescription() { return description; }
}