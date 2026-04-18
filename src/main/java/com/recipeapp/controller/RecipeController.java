package com.recipeapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Arrays;

@RestController
public class RecipeController {

    @GetMapping("/recipes")
    public List<String> getRecipes() {
        return Arrays.asList("Spaghetti Carbonara", "Chicken Tikka Masala", "Avocado Toast");
    }
}