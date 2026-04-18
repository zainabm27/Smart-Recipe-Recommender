package com.recipeapp.model;

public class HealthRestriction {
    private String allergenName;
    private String severity; // "Lethal", "Mild", "Intolerance"
    
    public HealthRestriction(String allergenName, String severity) {
        this.allergenName = allergenName;
        this.severity = severity;
    }
    
    public int getSeverityLevel() {
        switch (severity.toLowerCase()) {
            case "lethal": return 10;
            case "severe": return 7;
            case "moderate": return 3;
            case "mild": return 1;
            case "intolerance": return 2;
            default: return 0;
        }
    }
    
    public String getAllergenName() { return allergenName; }
    public String getSeverity() { return severity; }
}