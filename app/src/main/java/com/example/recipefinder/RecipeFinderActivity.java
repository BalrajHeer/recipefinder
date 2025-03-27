package com.example.recipefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFinderActivity extends AppCompatActivity {
    private EditText searchInput;
    private Button searchButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private static final String API_KEY = "826ebe345fcf4124ac1a7c40fe637ec9"; // Replace with your Spoonacular API Key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter();
        recyclerView.setAdapter(recipeAdapter);

        // Set up the click listener for recipe images
        recipeAdapter.setOnRecipeImageClickListener(recipeId -> {
            // Fetch and handle recipe ingredients
            fetchRecipeIngredients(recipeId);
        });

        searchButton.setOnClickListener(v -> searchRecipes());
    }

    private void searchRecipes() {
        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(this, "Enter a recipe name", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        RecipeApiService apiService = ApiClient.getRecipeApiService();
        Call<RecipeResponse> call = apiService.searchRecipes(query, API_KEY);

        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Recipe> recipes = response.body().getRecipes();
                    recipeAdapter.setRecipes(recipes);
                } else {
                    Toast.makeText(RecipeFinderActivity.this, "No recipes found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RecipeFinderActivity.this, "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchRecipeIngredients(int recipeId) {
        progressBar.setVisibility(View.VISIBLE);
        RecipeApiService apiService = ApiClient.getRecipeApiService();
        Call<Recipe> call = apiService.getRecipeDetails(recipeId, API_KEY);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    // Pass ingredients to a new Activity or Fragment
                    List<ExtendedIngredient> ingredients = response.body().getExtendedIngredients();
                    openIngredientsActivity(ingredients);
                } else {
                    Toast.makeText(RecipeFinderActivity.this, "No ingredients found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RecipeFinderActivity.this, "Failed to fetch ingredients", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openIngredientsActivity(List<ExtendedIngredient> ingredients) {
        Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtra("ingredients", new ArrayList<>(ingredients)); // Pass ingredients as a Parcelable or Serializable list
        startActivity(intent);
    }
}