package com.recipeapp.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import com.recipeapp.model.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {
    
    private RecommenderController recommender;
    private RecipeRepository recipeRepo;
    private User currentUser;
    
    public ApiController() {
        this.recipeRepo = new RecipeRepository();
        initializeUser();
        this.recommender = new RecommenderController(new WasteReductionStrategy(), recipeRepo);
    }
    
    private void initializeUser() {
        DietaryProfile profile = new DietaryProfile("None", "Flexible");
        this.currentUser = new User("Chef", profile);
        
        // Add sample pantry items
        Pantry pantry = currentUser.getPantry();
        pantry.addPantryItem(new Ingredient("Egg", "Eggs", 30), 6, "pcs", LocalDate.now().plusDays(5));
        pantry.addPantryItem(new Ingredient("Milk", "Dairy", 7), 1, "L", LocalDate.now().plusDays(3));
        pantry.addPantryItem(new Ingredient("Garlic", "Vegetable", 30), 5, "cloves", LocalDate.now().plusDays(2));
    }
    
    //RECIPE ENDPOINTS
    
    @GetMapping("/recipes")
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepo.fetchAllRecipes().stream()
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/recipes/{name}")
    public ResponseEntity<RecipeDTO> getRecipeByName(@PathVariable String name) {
        return recipeRepo.fetchAllRecipes().stream()
                .filter(r -> r.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(RecipeDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    //RECOMMENDATION ENDPOINTS
    
    @GetMapping("/recommendations")
    public List<RecommendationDTO> getRecommendations(
            @RequestParam(defaultValue = "waste") String strategy) {
        
        if (strategy.equals("dietary")) {
            recommender.setStrategy(new DietaryMatchStrategy());
        } else {
            recommender.setStrategy(new WasteReductionStrategy());
        }
        
        List<RecommenderController.RecipeRecommendation> recs = 
                recommender.getDetailedRecommendations(currentUser);
        
        return recs.stream()
                .map(RecommendationDTO::new)
                .collect(Collectors.toList());
    }
    
    //PANTRY ENDPOINTS
    
    @GetMapping("/pantry")
    public List<PantryItemDTO> getPantryItems() {
        return currentUser.getPantry().getAllItems().stream()
                .map(PantryItemDTO::new)
                .collect(Collectors.toList());
    }
    
    @PostMapping("/pantry")
    public ResponseEntity<PantryItemDTO> addToPantry(@RequestBody PantryItemRequest request) {
        Ingredient ingredient = new Ingredient(
            request.name, 
            request.category != null ? request.category : "Other", 
            30
        );
        
        LocalDate expiry = request.expiryDays != null ? 
            LocalDate.now().plusDays(request.expiryDays) : 
            LocalDate.now().plusDays(7);
        
        currentUser.getPantry().addPantryItem(
            ingredient, request.quantity, request.unit, expiry
        );
        
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/pantry/{ingredientName}")
    public ResponseEntity<Void> removeFromPantry(@PathVariable String ingredientName) {
        Pantry pantry = currentUser.getPantry();
        PantryItem toRemove = pantry.getAllItems().stream()
                .filter(item -> item.getIngredient().getName().equalsIgnoreCase(ingredientName))
                .findFirst()
                .orElse(null);
        
        if (toRemove != null) {
            pantry.removeItem(toRemove);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/pantry/expiring")
    public List<PantryItemDTO> getExpiringItems(@RequestParam(defaultValue = "3") int days) {
        return currentUser.getPantry().getExpiringItems(days).stream()
                .map(PantryItemDTO::new)
                .collect(Collectors.toList());
    }
    
    //FAVORITES ENDPOINTS
    
    @GetMapping("/favorites")
    public List<RecipeDTO> getFavorites() {
        return currentUser.getFavorites().stream()
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }
    
    @PostMapping("/favorites/{recipeName}")
    public ResponseEntity<Void> addToFavorites(@PathVariable String recipeName) {
        return recipeRepo.fetchAllRecipes().stream()
                .filter(r -> r.getName().equalsIgnoreCase(recipeName))
                .findFirst()
                .map(recipe -> {
                    currentUser.addToFavorites(recipe);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    //SHOPPING LIST ENDPOINTS
    
    @GetMapping("/shopping-list")
    public List<ShoppingItemDTO> getShoppingList() {
        ShoppingList list = new ShoppingList();
        
        // Generate from recommendations
        var recs = recommender.getDetailedRecommendations(currentUser);
        for (var rec : recs) {
            for (var entry : rec.getMissingQuantities().entrySet()) {
                list.addItem(new ShoppingListItem(
                    entry.getKey(), 
                    entry.getValue(), 
                    "units"
                ));
            }
        }
        
        return list.getItems().stream()
                .map(ShoppingItemDTO::new)
                .collect(Collectors.toList());
    }
    
    //NOTIFICATION ENDPOINTS
    
    @GetMapping("/notifications")
    public List<String> getNotifications() {
        NotificationManager notifier = new NotificationManager();
        List<String> notifications = new ArrayList<>();
        
        List<PantryItem> expiringItems = currentUser.getPantry().getExpiringItems(3);
        for (PantryItem item : expiringItems) {
            notifications.add(item.getIngredient().getName() + " expires on " + item.getExpiryDate());
        }
        
        return notifications;
    }
    
    //USER PROFILE ENDPOINTS
    
    @GetMapping("/profile")
    public ProfileDTO getProfile() {
        return new ProfileDTO(currentUser);
    }
    
    @PutMapping("/profile/dietary")
    public ResponseEntity<Void> updateDietaryProfile(@RequestBody DietaryProfileRequest request) {
        // Update user's dietary profile
        return ResponseEntity.ok().build();
    }
    
    //DTO CLASSES
    
    static class RecipeDTO {
        public String name;
        public String cuisine;
        public int baseServings;
        public int totalTime;
        public List<IngredientDTO> ingredients;
        public List<InstructionStepDTO> steps;  // ← Must be present
        
        public RecipeDTO(Recipe recipe) {
            this.name = recipe.getName();
            this.cuisine = recipe.getCuisine();
            this.baseServings = recipe.getBaseServings();
            this.totalTime = recipe.getTotalTime();
            
            this.ingredients = new ArrayList<>();
            for (RecipeIngredient ri : recipe.getIngredients()) {
                this.ingredients.add(new IngredientDTO(
                    ri.getIngredient().getName(),
                    ri.getQuantity(),
                    ri.getUnit(),
                    ri.isOptional()
                ));
            }
            
            // Map steps
            this.steps = new ArrayList<>();
            for (InstructionStep step : recipe.getSteps()) {
                this.steps.add(new InstructionStepDTO(
                    step.getStepNumber(),
                    step.getDescription(),
                    step.getEstimatedTime()
                ));
            }
        }
    }

    static class InstructionStepDTO {
        public int stepNumber;
        public String description;
        public int durationMinutes;
        
        public InstructionStepDTO(int stepNumber, String description, int durationMinutes) {
            this.stepNumber = stepNumber;
            this.description = description;
            this.durationMinutes = durationMinutes;
        }
    }
    
    static class IngredientDTO {
        public String name;
        public float quantity;
        public String unit;
        public boolean optional;
        
        public IngredientDTO(String name, float quantity, String unit, boolean optional) {
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
            this.optional = optional;
        }
    }
    
    static class RecommendationDTO {
        public RecipeDTO recipe;
        public int wasteReductionScore;
        public int preferenceScore;
        public boolean canMake;
        public List<String> missingItems;
        public Map<Ingredient, String> substitutions;
        
        public RecommendationDTO(RecommenderController.RecipeRecommendation rec) {
            this.recipe = new RecipeDTO(rec.getRecipe());
            this.wasteReductionScore = rec.getWasteReductionScore();
            this.preferenceScore = rec.getPreferenceScore();
            this.canMake = rec.canMakeWithSubstitutions();
            this.missingItems = rec.getShoppingList();
            this.substitutions = rec.getSubstitutions();
        }
    }
    
    static class PantryItemDTO {
        public String name;
        public float quantity;
        public String unit;
        public String expiryDate;
        public int wasteRiskScore;
        
        public PantryItemDTO(PantryItem item) {
            this.name = item.getIngredient().getName();
            this.quantity = item.getQuantity();
            this.unit = item.getUnit();
            this.expiryDate = item.getExpiryDate() != null ? 
                item.getExpiryDate().toString() : "No expiry";
            this.wasteRiskScore = item.getWasteRiskScore();
        }
    }
    
    static class ShoppingItemDTO {
        public String name;
        public float quantity;
        public String unit;
        public boolean purchased;
        
        public ShoppingItemDTO(ShoppingListItem item) {
            this.name = item.getIngredient().getName();
            this.quantity = item.getQuantity();
            this.unit = item.getUnit();
            this.purchased = item.isPurchased();
        }
    }
    
    static class ProfileDTO {
        public String username;
        public String dietaryType;
        public int pantryItemCount;
        public int favoriteCount;
        
        public ProfileDTO(User user) {
            this.username = user.getUsername();
            this.dietaryType = user.getProfile().getProfileType();
            this.pantryItemCount = user.getPantry().getAllItems().size();
            this.favoriteCount = user.getFavorites().size();
        }
    }
    
    // Request DTOs
    static class PantryItemRequest {
        public String name;
        public String category;
        public float quantity;
        public String unit;
        public Integer expiryDays;
    }
    
    static class DietaryProfileRequest {
        public String profileType;
        public String strictnessLevel;
    }
}