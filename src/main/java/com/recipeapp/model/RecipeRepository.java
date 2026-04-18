package com.recipeapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeRepository {
    private List<Recipe> recipes;
    
    public RecipeRepository() {
        this.recipes = new ArrayList<>();
        initializeSampleRecipes();
    }
    
    public List<Recipe> fetchRecipesByCuisine(String cuisine) {
        return recipes.stream()
            .filter(r -> r.getCuisine().equalsIgnoreCase(cuisine))
            .collect(Collectors.toList());
    }
    
    public List<Recipe> fetchAllRecipes() {
        return new ArrayList<>(recipes);
    }
    
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
    
    private void initializeSampleRecipes() {
        
        // ==================== 1. TOMATO PASTA ====================
        Recipe pasta = new Recipe("Tomato Pasta", 4, "Italian");
        pasta.addIngredient(new RecipeIngredient(new Ingredient("Pasta", "Grains", 365), 200, "g"));
        pasta.addIngredient(new RecipeIngredient(new Ingredient("Tomato Sauce", "Canned", 365), 400, "ml"));
        pasta.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 2, "cloves"));
        pasta.addIngredient(new RecipeIngredient(new Ingredient("Olive Oil", "Oil", 365), 2, "tbsp"));
        pasta.addStep(new InstructionStep(1, "Bring a large pot of salted water to a boil", 5));
        pasta.addStep(new InstructionStep(2, "Cook pasta according to package instructions until al dente", 10));
        pasta.addStep(new InstructionStep(3, "Meanwhile, heat olive oil in a pan over medium heat", 1));
        pasta.addStep(new InstructionStep(4, "Add minced garlic and sauté until fragrant", 2));
        pasta.addStep(new InstructionStep(5, "Pour in tomato sauce and simmer for 5 minutes", 5));
        pasta.addStep(new InstructionStep(6, "Drain pasta and toss with the sauce", 2));
        pasta.addStep(new InstructionStep(7, "Serve hot with grated cheese if desired", 1));
        recipes.add(pasta);

        // ==================== 2. CHEESE OMELETTE ====================
        Recipe omelette = new Recipe("Cheese Omelette", 2, "French");
        omelette.addIngredient(new RecipeIngredient(new Ingredient("Egg", "Eggs", 30), 3, "pcs"));
        omelette.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 14), 50, "g"));
        omelette.addIngredient(new RecipeIngredient(new Ingredient("Butter", "Dairy", 60), 10, "g"));
        omelette.addStep(new InstructionStep(1, "Crack eggs into a bowl, add a pinch of salt and pepper", 1));
        omelette.addStep(new InstructionStep(2, "Whisk vigorously until eggs are frothy and uniform", 1));
        omelette.addStep(new InstructionStep(3, "Heat butter in a non-stick pan over medium heat until melted", 2));
        omelette.addStep(new InstructionStep(4, "Pour eggs into the pan and let set for 30 seconds", 1));
        omelette.addStep(new InstructionStep(5, "Gently push cooked edges toward center, tilting pan", 2));
        omelette.addStep(new InstructionStep(6, "Sprinkle cheese over half the omelette", 1));
        omelette.addStep(new InstructionStep(7, "Fold omelette in half and cook 1 more minute", 1));
        omelette.addStep(new InstructionStep(8, "Slide onto plate and serve immediately", 1));
        recipes.add(omelette);

        // ==================== 3. GARLIC BROCCOLI STIR-FRY ====================
        Recipe stirFry = new Recipe("Garlic Broccoli Stir-Fry", 2, "Asian");
        stirFry.addIngredient(new RecipeIngredient(new Ingredient("Broccoli", "Vegetable", 7), 1, "head"));
        stirFry.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 3, "cloves"));
        stirFry.addIngredient(new RecipeIngredient(new Ingredient("Soy Sauce", "Pantry", 365), 2, "tbsp"));
        stirFry.addIngredient(new RecipeIngredient(new Ingredient("Olive Oil", "Oil", 365), 1, "tbsp"));
        stirFry.addStep(new InstructionStep(1, "Cut broccoli into bite-sized florets", 3));
        stirFry.addStep(new InstructionStep(2, "Mince garlic cloves finely", 1));
        stirFry.addStep(new InstructionStep(3, "Heat oil in a wok or large pan over high heat", 1));
        stirFry.addStep(new InstructionStep(4, "Add garlic and stir-fry for 30 seconds until fragrant", 1));
        stirFry.addStep(new InstructionStep(5, "Add broccoli and stir-fry for 3-4 minutes", 4));
        stirFry.addStep(new InstructionStep(6, "Add soy sauce and toss to coat evenly", 1));
        stirFry.addStep(new InstructionStep(7, "Cook for 1 more minute until broccoli is tender-crisp", 1));
        stirFry.addStep(new InstructionStep(8, "Serve hot as a side or over rice", 1));
        recipes.add(stirFry);

        // ==================== 4. MARGHERITA PIZZA ====================
        Recipe pizza = new Recipe("Margherita Pizza", 2, "Italian");
        pizza.addIngredient(new RecipeIngredient(new Ingredient("Flour", "Grains", 365), 250, "g"));
        pizza.addIngredient(new RecipeIngredient(new Ingredient("Tomato Sauce", "Canned", 365), 150, "ml"));
        pizza.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 200, "g"));
        pizza.addIngredient(new RecipeIngredient(new Ingredient("Olive Oil", "Oil", 365), 1, "tbsp"));
        pizza.addStep(new InstructionStep(1, "Preheat oven to 220°C (425°F)", 10));
        pizza.addStep(new InstructionStep(2, "Mix flour with water and yeast to form dough, knead for 10 minutes", 10));
        pizza.addStep(new InstructionStep(3, "Let dough rise for 1 hour until doubled", 60));
        pizza.addStep(new InstructionStep(4, "Roll out dough into a thin circle", 5));
        pizza.addStep(new InstructionStep(5, "Spread tomato sauce evenly over the base", 2));
        pizza.addStep(new InstructionStep(6, "Tear cheese and distribute over the sauce", 2));
        pizza.addStep(new InstructionStep(7, "Drizzle with olive oil", 1));
        pizza.addStep(new InstructionStep(8, "Bake for 12-15 minutes until crust is golden and cheese bubbles", 15));
        pizza.addStep(new InstructionStep(9, "Remove from oven, slice, and serve hot", 2));
        recipes.add(pizza);

        // ==================== 5. QUICK BEEF TACOS ====================
        Recipe tacos = new Recipe("Quick Beef Tacos", 3, "Mexican");
        tacos.addIngredient(new RecipeIngredient(new Ingredient("Ground Beef", "Meat", 3), 400, "g"));
        tacos.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 1, "pc"));
        tacos.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 100, "g"));
        tacos.addIngredient(new RecipeIngredient(new Ingredient("Tomato", "Vegetable", 7), 2, "pcs"));
        tacos.addStep(new InstructionStep(1, "Finely chop the onion", 3));
        tacos.addStep(new InstructionStep(2, "Dice the tomatoes into small cubes", 2));
        tacos.addStep(new InstructionStep(3, "Heat a pan over medium-high heat and add ground beef", 1));
        tacos.addStep(new InstructionStep(4, "Cook beef, breaking it up with a spoon, until browned (8-10 minutes)", 10));
        tacos.addStep(new InstructionStep(5, "Add half the chopped onion and cook for 2 more minutes", 2));
        tacos.addStep(new InstructionStep(6, "Season with salt, pepper, and taco seasoning", 1));
        tacos.addStep(new InstructionStep(7, "Warm taco shells according to package instructions", 3));
        tacos.addStep(new InstructionStep(8, "Assemble tacos with beef, remaining onion, tomatoes, and cheese", 3));
        tacos.addStep(new InstructionStep(9, "Serve immediately with your favorite toppings", 1));
        recipes.add(tacos);

        // ==================== 6. CLASSIC FRENCH TOAST ====================
        Recipe frenchToast = new Recipe("Classic French Toast", 2, "Breakfast");
        frenchToast.addIngredient(new RecipeIngredient(new Ingredient("Bread", "Grains", 7), 4, "slices"));
        frenchToast.addIngredient(new RecipeIngredient(new Ingredient("Egg", "Eggs", 30), 2, "pcs"));
        frenchToast.addIngredient(new RecipeIngredient(new Ingredient("Milk", "Dairy", 7), 100, "ml"));
        frenchToast.addIngredient(new RecipeIngredient(new Ingredient("Sugar", "Pantry", 365), 1, "tbsp"));
        frenchToast.addStep(new InstructionStep(1, "In a shallow bowl, whisk together eggs, milk, and sugar", 2));
        frenchToast.addStep(new InstructionStep(2, "Add a pinch of cinnamon or vanilla if desired", 1));
        frenchToast.addStep(new InstructionStep(3, "Heat a non-stick pan or griddle over medium heat", 2));
        frenchToast.addStep(new InstructionStep(4, "Dip each bread slice in egg mixture, coating both sides", 2));
        frenchToast.addStep(new InstructionStep(5, "Let excess drip off before placing on pan", 1));
        frenchToast.addStep(new InstructionStep(6, "Cook for 2-3 minutes per side until golden brown", 5));
        frenchToast.addStep(new InstructionStep(7, "Serve warm with maple syrup, fruit, or powdered sugar", 1));
        recipes.add(frenchToast);

        // ==================== 7. SIMPLE CHICKEN CURRY ====================
        Recipe curry = new Recipe("Simple Chicken Curry", 4, "Indian");
        curry.addIngredient(new RecipeIngredient(new Ingredient("Chicken Breast", "Meat", 3), 500, "g"));
        curry.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 2, "pcs"));
        curry.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 3, "cloves"));
        curry.addIngredient(new RecipeIngredient(new Ingredient("Tomato Sauce", "Canned", 365), 200, "ml"));
        curry.addStep(new InstructionStep(1, "Cut chicken into bite-sized cubes", 5));
        curry.addStep(new InstructionStep(2, "Finely chop onions and mince garlic", 5));
        curry.addStep(new InstructionStep(3, "Heat oil in a large pot over medium heat", 1));
        curry.addStep(new InstructionStep(4, "Add onions and cook until golden brown (8-10 minutes)", 10));
        curry.addStep(new InstructionStep(5, "Add garlic and curry spices, cook for 1 minute until fragrant", 1));
        curry.addStep(new InstructionStep(6, "Add chicken and cook until lightly browned", 5));
        curry.addStep(new InstructionStep(7, "Pour in tomato sauce and 1/2 cup water, stir well", 2));
        curry.addStep(new InstructionStep(8, "Simmer covered for 20 minutes until chicken is cooked through", 20));
        curry.addStep(new InstructionStep(9, "Serve hot with rice or naan bread", 2));
        recipes.add(curry);

        // ==================== 8. GREEK SALAD ====================
        Recipe salad = new Recipe("Greek Salad", 2, "Mediterranean");
        salad.addIngredient(new RecipeIngredient(new Ingredient("Cucumber", "Vegetable", 7), 1, "pc"));
        salad.addIngredient(new RecipeIngredient(new Ingredient("Tomato", "Vegetable", 7), 2, "pcs"));
        salad.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 0.5f, "pc"));
        salad.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 50, "g"));
        salad.addStep(new InstructionStep(1, "Wash all vegetables thoroughly", 2));
        salad.addStep(new InstructionStep(2, "Chop cucumber into half-moon slices", 2));
        salad.addStep(new InstructionStep(3, "Cut tomatoes into wedges or chunks", 2));
        salad.addStep(new InstructionStep(4, "Thinly slice the onion", 2));
        salad.addStep(new InstructionStep(5, "Combine all vegetables in a large bowl", 1));
        salad.addStep(new InstructionStep(6, "Crumble cheese over the top", 1));
        salad.addStep(new InstructionStep(7, "Drizzle with olive oil and sprinkle with oregano", 1));
        salad.addStep(new InstructionStep(8, "Toss gently and serve immediately", 1));
        recipes.add(salad);

        // ==================== 9. GARLIC BUTTER SHRIMP ====================
        Recipe shrimp = new Recipe("Garlic Butter Shrimp", 2, "Seafood");
        shrimp.addIngredient(new RecipeIngredient(new Ingredient("Shrimp", "Seafood", 2), 300, "g"));
        shrimp.addIngredient(new RecipeIngredient(new Ingredient("Butter", "Dairy", 60), 50, "g"));
        shrimp.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 4, "cloves"));
        shrimp.addIngredient(new RecipeIngredient(new Ingredient("Lemon", "Produce", 14), 1, "pc"));
        shrimp.addStep(new InstructionStep(1, "Peel and devein shrimp if not already done, pat dry", 5));
        shrimp.addStep(new InstructionStep(2, "Mince garlic cloves finely", 2));
        shrimp.addStep(new InstructionStep(3, "Cut lemon in half, juice one half and slice the other for garnish", 2));
        shrimp.addStep(new InstructionStep(4, "Melt butter in a large skillet over medium heat", 2));
        shrimp.addStep(new InstructionStep(5, "Add garlic and cook until fragrant (30 seconds)", 1));
        shrimp.addStep(new InstructionStep(6, "Add shrimp in a single layer, cook 2 minutes per side", 4));
        shrimp.addStep(new InstructionStep(7, "Squeeze lemon juice over shrimp and toss", 1));
        shrimp.addStep(new InstructionStep(8, "Garnish with lemon slices and parsley, serve immediately", 1));
        recipes.add(shrimp);

        // ==================== 10. RED LENTIL SOUP ====================
        Recipe lentilSoup = new Recipe("Red Lentil Soup", 4, "Middle Eastern");
        lentilSoup.addIngredient(new RecipeIngredient(new Ingredient("Lentils", "Legumes", 365), 200, "g"));
        lentilSoup.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 1, "pc"));
        lentilSoup.addIngredient(new RecipeIngredient(new Ingredient("Carrot", "Vegetable", 21), 2, "pcs"));
        lentilSoup.addIngredient(new RecipeIngredient(new Ingredient("Olive Oil", "Oil", 365), 2, "tbsp"));
        lentilSoup.addStep(new InstructionStep(1, "Rinse red lentils thoroughly in cold water until water runs clear", 3));
        lentilSoup.addStep(new InstructionStep(2, "Finely chop onion and dice carrots into small cubes", 5));
        lentilSoup.addStep(new InstructionStep(3, "Heat olive oil in a large pot over medium heat", 1));
        lentilSoup.addStep(new InstructionStep(4, "Add onion and carrot, sauté for 5-7 minutes until softened", 7));
        lentilSoup.addStep(new InstructionStep(5, "Add lentils and 4 cups of water or broth", 2));
        lentilSoup.addStep(new InstructionStep(6, "Bring to a boil, then reduce heat and simmer for 20 minutes", 20));
        lentilSoup.addStep(new InstructionStep(7, "Season with salt, pepper, and cumin", 1));
        lentilSoup.addStep(new InstructionStep(8, "Blend until smooth (optional) and serve hot with lemon wedge", 3));
        recipes.add(lentilSoup);

        // ==================== 11. GOURMET GRILLED CHEESE ====================
        Recipe grilledCheese = new Recipe("Gourmet Grilled Cheese", 1, "American");
        grilledCheese.addIngredient(new RecipeIngredient(new Ingredient("Bread", "Grains", 7), 2, "slices"));
        grilledCheese.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 2, "slices"));
        grilledCheese.addIngredient(new RecipeIngredient(new Ingredient("Butter", "Dairy", 60), 1, "tbsp"));
        grilledCheese.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 0.5f, "clove"));
        grilledCheese.addStep(new InstructionStep(1, "Butter one side of each bread slice generously", 1));
        grilledCheese.addStep(new InstructionStep(2, "Rub the unbuttered side of one slice with cut garlic clove", 1));
        grilledCheese.addStep(new InstructionStep(3, "Place cheese slices between bread (buttered sides facing out)", 1));
        grilledCheese.addStep(new InstructionStep(4, "Heat a skillet over medium-low heat", 2));
        grilledCheese.addStep(new InstructionStep(5, "Place sandwich in skillet and cook 3-4 minutes until golden", 4));
        grilledCheese.addStep(new InstructionStep(6, "Flip carefully and cook another 3-4 minutes", 4));
        grilledCheese.addStep(new InstructionStep(7, "Cheese should be fully melted and bread golden brown", 1));
        grilledCheese.addStep(new InstructionStep(8, "Slice diagonally and serve immediately", 1));
        recipes.add(grilledCheese);

        // ==================== 12. CREAMY MUSHROOM PASTA ====================
        Recipe mushroomPasta = new Recipe("Creamy Mushroom Pasta", 2, "Italian");
        mushroomPasta.addIngredient(new RecipeIngredient(new Ingredient("Pasta", "Grains", 365), 250, "g"));
        mushroomPasta.addIngredient(new RecipeIngredient(new Ingredient("Mushrooms", "Vegetable", 5), 200, "g"));
        mushroomPasta.addIngredient(new RecipeIngredient(new Ingredient("Milk", "Dairy", 7), 150, "ml"));
        mushroomPasta.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 2, "cloves"));
        mushroomPasta.addStep(new InstructionStep(1, "Bring a pot of salted water to boil and cook pasta", 10));
        mushroomPasta.addStep(new InstructionStep(2, "Clean and slice mushrooms", 3));
        mushroomPasta.addStep(new InstructionStep(3, "Mince garlic cloves", 1));
        mushroomPasta.addStep(new InstructionStep(4, "Heat oil in a pan, sauté garlic for 30 seconds", 1));
        mushroomPasta.addStep(new InstructionStep(5, "Add mushrooms and cook until they release moisture and brown (8-10 min)", 10));
        mushroomPasta.addStep(new InstructionStep(6, "Pour in milk and simmer until slightly thickened", 5));
        mushroomPasta.addStep(new InstructionStep(7, "Season with salt, pepper, and herbs", 1));
        mushroomPasta.addStep(new InstructionStep(8, "Drain pasta and toss with mushroom sauce", 2));
        mushroomPasta.addStep(new InstructionStep(9, "Serve hot with grated cheese", 1));
        recipes.add(mushroomPasta);

        // ==================== 13. BEEF AND PEPPER STIR-FRY ====================
        Recipe beefStirFry = new Recipe("Beef and Pepper Stir-Fry", 2, "Asian");
        beefStirFry.addIngredient(new RecipeIngredient(new Ingredient("Ground Beef", "Meat", 3), 300, "g"));
        beefStirFry.addIngredient(new RecipeIngredient(new Ingredient("Bell Pepper", "Vegetable", 7), 1, "pc"));
        beefStirFry.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 1, "pc"));
        beefStirFry.addIngredient(new RecipeIngredient(new Ingredient("Soy Sauce", "Pantry", 365), 3, "tbsp"));
        beefStirFry.addStep(new InstructionStep(1, "Slice bell pepper into thin strips", 3));
        beefStirFry.addStep(new InstructionStep(2, "Slice onion into thin wedges", 2));
        beefStirFry.addStep(new InstructionStep(3, "Heat oil in a wok or large skillet over high heat", 1));
        beefStirFry.addStep(new InstructionStep(4, "Add ground beef and cook until browned, breaking it up (6-8 min)", 8));
        beefStirFry.addStep(new InstructionStep(5, "Push beef to side, add onion and pepper, stir-fry 3-4 minutes", 4));
        beefStirFry.addStep(new InstructionStep(6, "Add soy sauce and mix everything together", 1));
        beefStirFry.addStep(new InstructionStep(7, "Cook for 2 more minutes until vegetables are tender-crisp", 2));
        beefStirFry.addStep(new InstructionStep(8, "Serve hot over steamed rice", 1));
        recipes.add(beefStirFry);

        // ==================== 14. FLUFFY PANCAKES ====================
        Recipe pancakes = new Recipe("Fluffy Pancakes", 3, "Breakfast");
        pancakes.addIngredient(new RecipeIngredient(new Ingredient("Flour", "Grains", 365), 200, "g"));
        pancakes.addIngredient(new RecipeIngredient(new Ingredient("Milk", "Dairy", 7), 250, "ml"));
        pancakes.addIngredient(new RecipeIngredient(new Ingredient("Egg", "Eggs", 30), 1, "pc"));
        pancakes.addIngredient(new RecipeIngredient(new Ingredient("Sugar", "Pantry", 365), 2, "tbsp"));
        pancakes.addStep(new InstructionStep(1, "In a large bowl, whisk together flour, sugar, baking powder, and salt", 2));
        pancakes.addStep(new InstructionStep(2, "In another bowl, whisk egg and milk until combined", 1));
        pancakes.addStep(new InstructionStep(3, "Pour wet ingredients into dry and stir until just combined (lumpy is okay)", 2));
        pancakes.addStep(new InstructionStep(4, "Let batter rest for 5 minutes", 5));
        pancakes.addStep(new InstructionStep(5, "Heat a non-stick pan or griddle over medium heat, lightly grease", 2));
        pancakes.addStep(new InstructionStep(6, "Pour 1/4 cup batter per pancake", 2));
        pancakes.addStep(new InstructionStep(7, "Cook until bubbles form on surface, then flip (2-3 minutes per side)", 5));
        pancakes.addStep(new InstructionStep(8, "Keep warm and serve with butter and maple syrup", 1));
        recipes.add(pancakes);

        // ==================== 15. LOADED POTATO SKINS ====================
        Recipe potatoSkins = new Recipe("Loaded Potato Skins", 4, "Appetizer");
        potatoSkins.addIngredient(new RecipeIngredient(new Ingredient("Potato", "Vegetable", 60), 4, "pcs"));
        potatoSkins.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 100, "g"));
        potatoSkins.addIngredient(new RecipeIngredient(new Ingredient("Bacon", "Meat", 14), 4, "slices"));
        potatoSkins.addIngredient(new RecipeIngredient(new Ingredient("Olive Oil", "Oil", 365), 1, "tbsp"));
        potatoSkins.addStep(new InstructionStep(1, "Preheat oven to 200°C (400°F)", 10));
        potatoSkins.addStep(new InstructionStep(2, "Scrub potatoes clean and pierce with a fork", 3));
        potatoSkins.addStep(new InstructionStep(3, "Bake potatoes for 50-60 minutes until tender", 60));
        potatoSkins.addStep(new InstructionStep(4, "Meanwhile, cook bacon until crispy, then crumble", 10));
        potatoSkins.addStep(new InstructionStep(5, "Let potatoes cool slightly, then cut in half lengthwise", 5));
        potatoSkins.addStep(new InstructionStep(6, "Scoop out most of the flesh, leaving a 1/4 inch shell", 5));
        potatoSkins.addStep(new InstructionStep(7, "Brush shells with olive oil, bake 10 minutes until crisp", 10));
        potatoSkins.addStep(new InstructionStep(8, "Fill with cheese and bacon, bake 5 more minutes until cheese melts", 5));
        potatoSkins.addStep(new InstructionStep(9, "Top with sour cream and green onions, serve hot", 2));
        recipes.add(potatoSkins);

        // ==================== 16. SIMPLE SCRAMBLED EGGS ====================
        Recipe scrambledEggs = new Recipe("Simple Scrambled Eggs", 1, "Breakfast");
        scrambledEggs.addIngredient(new RecipeIngredient(new Ingredient("Egg", "Eggs", 30), 2, "pcs"));
        scrambledEggs.addIngredient(new RecipeIngredient(new Ingredient("Butter", "Dairy", 60), 1, "tbsp"));
        scrambledEggs.addIngredient(new RecipeIngredient(new Ingredient("Bread", "Grains", 7), 1, "slice"));
        scrambledEggs.addIngredient(new RecipeIngredient(new Ingredient("Milk", "Dairy", 7), 1, "tbsp"));
        scrambledEggs.addStep(new InstructionStep(1, "Crack eggs into a bowl, add milk, salt, and pepper", 1));
        scrambledEggs.addStep(new InstructionStep(2, "Whisk vigorously until fully combined and slightly frothy", 1));
        scrambledEggs.addStep(new InstructionStep(3, "Heat butter in a non-stick pan over medium-low heat", 2));
        scrambledEggs.addStep(new InstructionStep(4, "Pour in eggs and let sit undisturbed for 20 seconds", 1));
        scrambledEggs.addStep(new InstructionStep(5, "Gently push eggs from edges toward center with spatula", 2));
        scrambledEggs.addStep(new InstructionStep(6, "Continue folding until eggs are soft and creamy (3-4 minutes)", 4));
        scrambledEggs.addStep(new InstructionStep(7, "Remove from heat while still slightly wet (they continue cooking)", 1));
        scrambledEggs.addStep(new InstructionStep(8, "Toast bread and serve eggs on top or alongside", 3));
        recipes.add(scrambledEggs);

        // ==================== 17. HEARTY BEEF STEW ====================
        Recipe beefStew = new Recipe("Hearty Beef Stew", 4, "European");
        beefStew.addIngredient(new RecipeIngredient(new Ingredient("Ground Beef", "Meat", 3), 500, "g"));
        beefStew.addIngredient(new RecipeIngredient(new Ingredient("Potato", "Vegetable", 60), 3, "pcs"));
        beefStew.addIngredient(new RecipeIngredient(new Ingredient("Carrot", "Vegetable", 21), 2, "pcs"));
        beefStew.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 1, "pc"));
        beefStew.addStep(new InstructionStep(1, "Peel and dice potatoes into 1-inch cubes", 5));
        beefStew.addStep(new InstructionStep(2, "Peel and slice carrots into rounds", 3));
        beefStew.addStep(new InstructionStep(3, "Chop onion finely", 3));
        beefStew.addStep(new InstructionStep(4, "In a large pot, brown ground beef over medium-high heat (8-10 min)", 10));
        beefStew.addStep(new InstructionStep(5, "Add onion and cook until softened (3-4 min)", 4));
        beefStew.addStep(new InstructionStep(6, "Add potatoes, carrots, and enough water or broth to cover", 3));
        beefStew.addStep(new InstructionStep(7, "Bring to boil, then reduce heat and simmer covered for 45 minutes", 45));
        beefStew.addStep(new InstructionStep(8, "Season with salt, pepper, and herbs, simmer 15 more minutes", 15));
        beefStew.addStep(new InstructionStep(9, "Serve hot with crusty bread", 2));
        recipes.add(beefStew);

        // ==================== 18. LEMON GARLIC CHICKEN ====================
        Recipe lemonChicken = new Recipe("Lemon Garlic Chicken", 2, "Mediterranean");
        lemonChicken.addIngredient(new RecipeIngredient(new Ingredient("Chicken Breast", "Meat", 3), 400, "g"));
        lemonChicken.addIngredient(new RecipeIngredient(new Ingredient("Lemon", "Produce", 14), 1, "pc"));
        lemonChicken.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 3, "cloves"));
        lemonChicken.addIngredient(new RecipeIngredient(new Ingredient("Butter", "Dairy", 60), 2, "tbsp"));
        lemonChicken.addStep(new InstructionStep(1, "Slice chicken breasts in half horizontally to make thinner cutlets", 3));
        lemonChicken.addStep(new InstructionStep(2, "Season chicken with salt and pepper on both sides", 1));
        lemonChicken.addStep(new InstructionStep(3, "Mince garlic cloves", 1));
        lemonChicken.addStep(new InstructionStep(4, "Zest the lemon, then cut in half and juice one half", 2));
        lemonChicken.addStep(new InstructionStep(5, "Heat 1 tbsp butter in a skillet over medium-high heat", 2));
        lemonChicken.addStep(new InstructionStep(6, "Cook chicken 3-4 minutes per side until golden and cooked through", 8));
        lemonChicken.addStep(new InstructionStep(7, "Remove chicken, add remaining butter, garlic, lemon zest, and juice", 2));
        lemonChicken.addStep(new InstructionStep(8, "Cook sauce for 1 minute, then pour over chicken", 1));
        lemonChicken.addStep(new InstructionStep(9, "Garnish with lemon slices and serve", 1));
        recipes.add(lemonChicken);

        // ==================== 19. CHEESY GARLIC BREAD ====================
        Recipe garlicBread = new Recipe("Cheesy Garlic Bread", 4, "Appetizer");
        garlicBread.addIngredient(new RecipeIngredient(new Ingredient("Bread", "Grains", 7), 1, "loaf"));
        garlicBread.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 5, "cloves"));
        garlicBread.addIngredient(new RecipeIngredient(new Ingredient("Butter", "Dairy", 60), 100, "g"));
        garlicBread.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 150, "g"));
        garlicBread.addStep(new InstructionStep(1, "Preheat oven to 180°C (350°F)", 10));
        garlicBread.addStep(new InstructionStep(2, "Slice bread loaf in half lengthwise", 2));
        garlicBread.addStep(new InstructionStep(3, "Mince garlic cloves very finely", 2));
        garlicBread.addStep(new InstructionStep(4, "Soften butter and mix with minced garlic", 2));
        garlicBread.addStep(new InstructionStep(5, "Spread garlic butter evenly over both bread halves", 2));
        garlicBread.addStep(new InstructionStep(6, "Sprinkle cheese generously over the butter", 2));
        garlicBread.addStep(new InstructionStep(7, "Place on baking sheet and bake for 10-12 minutes", 12));
        garlicBread.addStep(new InstructionStep(8, "Broil for 1-2 minutes at the end for golden top (watch carefully)", 2));
        garlicBread.addStep(new InstructionStep(9, "Slice and serve warm", 2));
        recipes.add(garlicBread);

        // ==================== 20. BREAKFAST POTATO HASH ====================
        Recipe eggHash = new Recipe("Breakfast Potato Hash", 2, "Breakfast");
        eggHash.addIngredient(new RecipeIngredient(new Ingredient("Potato", "Vegetable", 60), 2, "pcs"));
        eggHash.addIngredient(new RecipeIngredient(new Ingredient("Egg", "Eggs", 30), 3, "pcs"));
        eggHash.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 0.5f, "pc"));
        eggHash.addIngredient(new RecipeIngredient(new Ingredient("Olive Oil", "Oil", 365), 1, "tbsp"));
        eggHash.addStep(new InstructionStep(1, "Wash and dice potatoes into 1/2 inch cubes", 5));
        eggHash.addStep(new InstructionStep(2, "Finely chop the onion", 2));
        eggHash.addStep(new InstructionStep(3, "Heat olive oil in a large skillet over medium heat", 1));
        eggHash.addStep(new InstructionStep(4, "Add potatoes and cook, stirring occasionally, for 12-15 minutes", 15));
        eggHash.addStep(new InstructionStep(5, "Add onion and continue cooking until potatoes are golden and tender", 5));
        eggHash.addStep(new InstructionStep(6, "Season with salt, pepper, and paprika", 1));
        eggHash.addStep(new InstructionStep(7, "Create 3 wells in the hash and crack an egg into each", 2));
        eggHash.addStep(new InstructionStep(8, "Cover and cook until egg whites are set (4-5 minutes)", 5));
        eggHash.addStep(new InstructionStep(9, "Serve immediately from the skillet", 1));
        recipes.add(eggHash);

        // ==================== 21. SOY GLAZED SALMON ====================
        Recipe salmon = new Recipe("Soy Glazed Salmon", 2, "Asian");
        salmon.addIngredient(new RecipeIngredient(new Ingredient("Salmon", "Seafood", 2), 300, "g"));
        salmon.addIngredient(new RecipeIngredient(new Ingredient("Soy Sauce", "Pantry", 365), 3, "tbsp"));
        salmon.addIngredient(new RecipeIngredient(new Ingredient("Sugar", "Pantry", 365), 1, "tbsp"));
        salmon.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 1, "clove"));
        salmon.addStep(new InstructionStep(1, "Pat salmon fillets dry with paper towels", 1));
        salmon.addStep(new InstructionStep(2, "Season lightly with salt and pepper", 1));
        salmon.addStep(new InstructionStep(3, "Mince garlic clove", 1));
        salmon.addStep(new InstructionStep(4, "Mix soy sauce, sugar, and garlic in a small bowl", 2));
        salmon.addStep(new InstructionStep(5, "Heat oil in a non-stick pan over medium-high heat", 1));
        salmon.addStep(new InstructionStep(6, "Place salmon skin-side down, cook 4-5 minutes", 5));
        salmon.addStep(new InstructionStep(7, "Flip salmon and cook 2 more minutes", 2));
        salmon.addStep(new InstructionStep(8, "Pour glaze over salmon and cook 1 minute until thickened", 1));
        salmon.addStep(new InstructionStep(9, "Spoon glaze over salmon and serve with rice", 2));
        recipes.add(salmon);

        // ==================== 22. CREAMY GARLIC MASH ====================
        Recipe mashedPotatoes = new Recipe("Creamy Garlic Mash", 4, "Side Dish");
        mashedPotatoes.addIngredient(new RecipeIngredient(new Ingredient("Potato", "Vegetable", 60), 6, "pcs"));
        mashedPotatoes.addIngredient(new RecipeIngredient(new Ingredient("Milk", "Dairy", 7), 100, "ml"));
        mashedPotatoes.addIngredient(new RecipeIngredient(new Ingredient("Butter", "Dairy", 60), 50, "g"));
        mashedPotatoes.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 3, "cloves"));
        mashedPotatoes.addStep(new InstructionStep(1, "Peel potatoes and cut into evenly sized chunks", 5));
        mashedPotatoes.addStep(new InstructionStep(2, "Place potatoes in a large pot and cover with cold salted water", 2));
        mashedPotatoes.addStep(new InstructionStep(3, "Add whole peeled garlic cloves to the pot", 1));
        mashedPotatoes.addStep(new InstructionStep(4, "Bring to boil and cook until potatoes are fork-tender (15-20 min)", 20));
        mashedPotatoes.addStep(new InstructionStep(5, "Drain potatoes and garlic, return to pot", 2));
        mashedPotatoes.addStep(new InstructionStep(6, "Add butter and mash until smooth", 3));
        mashedPotatoes.addStep(new InstructionStep(7, "Warm milk and gradually stir in until creamy consistency", 3));
        mashedPotatoes.addStep(new InstructionStep(8, "Season with salt and pepper, serve hot", 1));
        recipes.add(mashedPotatoes);

        // ==================== 23. CLASSIC BEEF BURGERS ====================
        Recipe burgers = new Recipe("Classic Beef Burgers", 2, "American");
        burgers.addIngredient(new RecipeIngredient(new Ingredient("Ground Beef", "Meat", 3), 300, "g"));
        burgers.addIngredient(new RecipeIngredient(new Ingredient("Bread", "Grains", 7), 2, "buns"));
        burgers.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 0.5f, "pc"));
        burgers.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 2, "slices"));
        burgers.addStep(new InstructionStep(1, "Divide beef into 2 equal portions and shape into patties", 3));
        burgers.addStep(new InstructionStep(2, "Make a small dimple in the center of each patty", 1));
        burgers.addStep(new InstructionStep(3, "Season both sides generously with salt and pepper", 1));
        burgers.addStep(new InstructionStep(4, "Heat a skillet or grill pan over high heat", 3));
        burgers.addStep(new InstructionStep(5, "Cook patties 3-4 minutes per side for medium", 8));
        burgers.addStep(new InstructionStep(6, "Add cheese slice on top during last minute of cooking", 1));
        burgers.addStep(new InstructionStep(7, "Toast burger buns lightly", 2));
        burgers.addStep(new InstructionStep(8, "Assemble burgers with sliced onion and desired toppings", 2));
        burgers.addStep(new InstructionStep(9, "Serve immediately with fries or salad", 1));
        recipes.add(burgers);

        // ==================== 24. TOMATO BRUSCHETTA ====================
        Recipe bruschetta = new Recipe("Tomato Bruschetta", 2, "Italian");
        bruschetta.addIngredient(new RecipeIngredient(new Ingredient("Bread", "Grains", 7), 4, "slices"));
        bruschetta.addIngredient(new RecipeIngredient(new Ingredient("Tomato", "Vegetable", 7), 2, "pcs"));
        bruschetta.addIngredient(new RecipeIngredient(new Ingredient("Garlic", "Vegetable", 30), 1, "clove"));
        bruschetta.addIngredient(new RecipeIngredient(new Ingredient("Olive Oil", "Oil", 365), 2, "tbsp"));
        bruschetta.addStep(new InstructionStep(1, "Dice tomatoes into small cubes", 3));
        bruschetta.addStep(new InstructionStep(2, "Mix tomatoes with 1 tbsp olive oil, salt, and pepper in a bowl", 1));
        bruschetta.addStep(new InstructionStep(3, "Let tomato mixture sit for 10 minutes to meld flavors", 10));
        bruschetta.addStep(new InstructionStep(4, "Toast bread slices until golden and crisp", 3));
        bruschetta.addStep(new InstructionStep(5, "Cut garlic clove in half and rub cut side on warm toast", 1));
        bruschetta.addStep(new InstructionStep(6, "Drizzle remaining olive oil over toast", 1));
        bruschetta.addStep(new InstructionStep(7, "Top each toast with tomato mixture", 2));
        bruschetta.addStep(new InstructionStep(8, "Garnish with fresh basil if available and serve immediately", 1));
        recipes.add(bruschetta);

        // ==================== 25. SIMPLE EGG FRIED RICE ====================
        Recipe friedRice = new Recipe("Simple Egg Fried Rice", 2, "Asian");
        friedRice.addIngredient(new RecipeIngredient(new Ingredient("Rice", "Grains", 365), 300, "g"));
        friedRice.addIngredient(new RecipeIngredient(new Ingredient("Egg", "Eggs", 30), 2, "pcs"));
        friedRice.addIngredient(new RecipeIngredient(new Ingredient("Soy Sauce", "Pantry", 365), 2, "tbsp"));
        friedRice.addIngredient(new RecipeIngredient(new Ingredient("Onion", "Vegetable", 30), 1, "pc"));
        friedRice.addStep(new InstructionStep(1, "Use day-old cooked rice for best results (cold rice works best)", 1));
        friedRice.addStep(new InstructionStep(2, "Finely chop the onion", 2));
        friedRice.addStep(new InstructionStep(3, "Beat eggs in a small bowl with a pinch of salt", 1));
        friedRice.addStep(new InstructionStep(4, "Heat oil in a wok or large pan over high heat", 1));
        friedRice.addStep(new InstructionStep(5, "Add onion and stir-fry for 2 minutes", 2));
        friedRice.addStep(new InstructionStep(6, "Push onion to side, pour eggs in and scramble until just set", 2));
        friedRice.addStep(new InstructionStep(7, "Add cold rice and break up any clumps", 2));
        friedRice.addStep(new InstructionStep(8, "Add soy sauce and stir-fry everything together for 3-4 minutes", 4));
        friedRice.addStep(new InstructionStep(9, "Serve hot as a main or side dish", 1));
        recipes.add(friedRice);

        // ==================== 26. CREAMY TOMATO PASTA ====================
        Recipe creamyPasta = new Recipe("Creamy Tomato Pasta", 2, "Italian");
        creamyPasta.addIngredient(new RecipeIngredient(new Ingredient("Pasta", "Grains", 365), 200, "g"));
        creamyPasta.addIngredient(new RecipeIngredient(new Ingredient("Tomato Sauce", "Canned", 365), 200, "ml"));
        creamyPasta.addIngredient(new RecipeIngredient(new Ingredient("Milk", "Dairy", 7), 100, "ml"));
        creamyPasta.addIngredient(new RecipeIngredient(new Ingredient("Cheese", "Dairy", 21), 50, "g"));
        creamyPasta.addStep(new InstructionStep(1, "Bring a large pot of salted water to boil and cook pasta", 10));
        creamyPasta.addStep(new InstructionStep(2, "While pasta cooks, heat tomato sauce in a pan over medium heat", 3));
        creamyPasta.addStep(new InstructionStep(3, "Stir in milk and bring to a gentle simmer", 3));
        creamyPasta.addStep(new InstructionStep(4, "Add grated cheese and stir until melted and sauce is creamy", 2));
        creamyPasta.addStep(new InstructionStep(5, "Season with salt, pepper, and dried herbs", 1));
        creamyPasta.addStep(new InstructionStep(6, "Drain pasta, reserving a splash of pasta water", 1));
        creamyPasta.addStep(new InstructionStep(7, "Add pasta to sauce and toss to coat, adding pasta water if needed", 2));
        creamyPasta.addStep(new InstructionStep(8, "Serve hot with extra cheese on top", 1));
        recipes.add(creamyPasta);
    }}