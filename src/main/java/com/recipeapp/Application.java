package com.recipeapp;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.recipeapp.model.DietaryProfile;
import com.recipeapp.model.HealthRestriction;
import com.recipeapp.model.User;
import com.recipeapp.model.Pantry;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.RecipeRepository;
import com.recipeapp.model.WasteReductionStrategy;
import com.recipeapp.model.RecommenderController;
import com.recipeapp.model.Recipe;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            System.out.println("=== SMART RECIPE RECOMMENDER SYSTEM ===\n");
            
            // Create user
            DietaryProfile profile = new DietaryProfile("Vegetarian", "Moderate");
            profile.addRestriction(new HealthRestriction("Shellfish", "Mild"));
            profile.setFlavorPreference("Mushrooms", -8); 
            profile.setFlavorPreference("Garlic", 10); 
            
            User user = new User("Alice", profile);
            
            // Add items to pantry
            Pantry pantry = user.getPantry();
            pantry.addPantryItem(new Ingredient("Pasta", "Grains", 365), 500, "g", LocalDate.now().plusDays(30));
            pantry.addPantryItem(new Ingredient("Tomato Sauce", "Canned", 365), 400, "ml", LocalDate.now().plusDays(365));
            pantry.addPantryItem(new Ingredient("Garlic", "Vegetable", 30), 5, "cloves", LocalDate.now().plusDays(5));
            pantry.addPantryItem(new Ingredient("Egg", "Eggs", 30), 6, "pcs", LocalDate.now().plusDays(2)); 
            pantry.addPantryItem(new Ingredient("Cheese", "Dairy", 14), 100, "g", LocalDate.now().plusDays(3));
            
            // Initialize repository and strategies
            RecipeRepository repository = new RecipeRepository();
            
            // Test Waste Reduction Strategy
            System.out.println("📊 USING WASTE REDUCTION STRATEGY");
            System.out.println("===================================");
            WasteReductionStrategy wasteStrategy = new WasteReductionStrategy();
            RecommenderController controller = new RecommenderController(wasteStrategy, repository);
            
            List<RecommenderController.RecipeRecommendation> recommendations = 
                controller.getDetailedRecommendations(user);
            
            // Display recommendations
            if (recommendations != null) {
                for (int i = 0; i < Math.min(3, recommendations.size()); i++) {
                    RecommenderController.RecipeRecommendation rec = recommendations.get(i);
                    Recipe recipe = rec.getRecipe();
                    
                    System.out.println("\n🍽️ " + recipe.getName());
                    System.out.println("   Waste Reduction Score: " + rec.getWasteReductionScore());
                    System.out.println("   Preference Score: " + rec.getPreferenceScore());
                    System.out.println("   Can Make: " + (rec.canMakeWithSubstitutions() ? "✅ Yes" : "⚠️ Need items"));
                }
            }
            
            System.out.println("\n✅ System ready! All features working.");
        };
    } // This bracket closes the demo() method
} // This bracket closes the RecipeApplication class