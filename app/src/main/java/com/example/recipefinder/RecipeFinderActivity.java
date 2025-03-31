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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFinderActivity extends AppCompatActivity {
    private EditText searchInput;
    private Button searchButton,showSavedRecipesButton, logoutButton; ;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private static final String API_KEY = "826ebe345fcf4124ac1a7c40fe637ec9"; // Replace with your Spoonacular API Key
    private String userId; // Firebase UID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

        userId = getIntent().getStringExtra("userId"); // Get userId from Intent
        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        showSavedRecipesButton = findViewById(R.id.showSavedRecipesButton);
        logoutButton = findViewById(R.id.logoutButton);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);


         //Set up the click listener for recipe images
        recipeAdapter.setOnRecipeImageClickListener(recipeId -> {
            // Fetch and handle recipe ingredients
            fetchRecipeIngredients(recipeId);
        });
        // Set up click listener for "Show Saved Recipes" button
        showSavedRecipesButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeFinderActivity.this, SavedRecipesActivity.class);
            intent.putExtra("userId", userId); // Pass userId to SavedRecipesActivity
            startActivity(intent); // Navigate to SavedRecipesActivity
        });


        searchButton.setOnClickListener(v -> searchRecipes());
        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {
        // Log out the current Firebase user
        FirebaseAuth.getInstance().signOut();

        // Navigate back to the login screen
        Intent intent = new Intent(RecipeFinderActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close RecipeFinderActivity to prevent returning to it
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
                    Recipe recipe = response.body();
                    String recipeTitle = recipe.getTitle(); // Get title
                    String recipeImageUrl = recipe.getImageUrl(); // Get image URL
                    List<ExtendedIngredient> ingredients = recipe.getExtendedIngredients(); // Get ingredients

                    // Pass data to the IngredientsActivity
                    openIngredientsActivity(ingredients, recipeTitle, recipeImageUrl);

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

    private void openIngredientsActivity(List<ExtendedIngredient> ingredients, String recipeTitle, String recipeImageUrl) {
        Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtra("ingredients", new ArrayList<>(ingredients)); // Pass ingredients as a Parcelable or Serializable list
        intent.putExtra("recipeTitle", recipeTitle); // Pass recipe title
        intent.putExtra("recipeImageUrl", recipeImageUrl); // Pass recipe image URL
        startActivity(intent);
    }
}
