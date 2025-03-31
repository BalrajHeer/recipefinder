package com.example.recipefinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;


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

        //String userId = getIntent().getStringExtra("userId"); // Get userId from Intent
        recyclerView = findViewById(R.id.recyclerViewSavedRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);

        // Load saved recipes
        List<Recipe> savedRecipes = loadSavedRecipes();
        if (!savedRecipes.isEmpty()) {
            recipeAdapter.setRecipes(savedRecipes);

//            // Handle recipe clicks
//            recipeAdapter.setOnRecipeImageClickListener(getTitle -> {
//                // Navigate to IngredientsActivity and pass ingredients
//                navigateToIngredientsActivity(getTitle);
//            });

        } else {
            Toast.makeText(this, "No saved recipes found.", Toast.LENGTH_SHORT).show();
        }



        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedRecipesActivity.this, RecipeFinderActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

    }

    private List<Recipe> loadSavedRecipes() {
        SharedPreferences sharedPreferences = getSharedPreferences("SavedRecipes", MODE_PRIVATE);
        Map<String, ?> savedRecipesMap = sharedPreferences.getAll();

        List<Recipe> recipes = new ArrayList<>();
        for (Map.Entry<String, ?> entry : savedRecipesMap.entrySet()) {
            String key = entry.getKey();

//            String title = entry.getKey();
//            String imageUrl = (String) entry.getValue();

            // Filter for recipe titles (avoid loading "_imageUrl" or "_ingredients" keys)
            if (key.endsWith("_imageUrl")) {
                String title = key.replace("_imageUrl", "");
                String imageUrl = (String) entry.getValue();

                Recipe recipe = new Recipe();
                recipe.setTitle(title);
                recipe.setImageUrl(imageUrl);

                recipes.add(recipe);
            }

        }
        return recipes;
    }

}