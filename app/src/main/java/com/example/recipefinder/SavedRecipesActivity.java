package com.example.recipefinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        recyclerView = findViewById(R.id.recyclerViewSavedRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);

        // Load saved recipes
        List<Recipe> savedRecipes = loadSavedRecipes();
        if (!savedRecipes.isEmpty()) {
            recipeAdapter.setRecipes(savedRecipes);
        } else {
            Toast.makeText(this, "No saved recipes found.", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Recipe> loadSavedRecipes() {
        SharedPreferences sharedPreferences = getSharedPreferences("SavedRecipes", MODE_PRIVATE);
        Map<String, ?> savedRecipesMap = sharedPreferences.getAll();

        List<Recipe> recipes = new ArrayList<>();
        for (Map.Entry<String, ?> entry : savedRecipesMap.entrySet()) {
            String title = entry.getKey();
            String imageUrl = (String) entry.getValue();

            // Create a Recipe object
            Recipe recipe = new Recipe();
            recipe.setTitle(title);
            recipe.setImageUrl(imageUrl);

            recipes.add(recipe);
        }
        return recipes;
    }
}