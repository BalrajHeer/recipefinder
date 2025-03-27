package com.example.recipefinder;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private ImageView recipeImage;
    private TextView recipeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        // Initialize UI elements
        recipeImage = findViewById(R.id.recipeImage);
        recipeTitle = findViewById(R.id.recipeTitle);
        recyclerView = findViewById(R.id.recyclerViewIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter = new IngredientsAdapter();
        recyclerView.setAdapter(ingredientsAdapter);

        // Retrieve and set data from the Intent
        if (getIntent() != null) {
            // Get ingredients list
            List<ExtendedIngredient> ingredients = (List<ExtendedIngredient>) getIntent().getSerializableExtra("ingredients");

            // Get recipe title
            String title = getIntent().getStringExtra("recipeTitle");

            // Get recipe image URL
            String imageUrl = getIntent().getStringExtra("recipeImageUrl");

            // Set recipe title
            if (title != null) {
                recipeTitle.setText(title);
            }

            // Load recipe image using Picasso
            if (imageUrl != null) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_placeholder) // Placeholder image
                        // Error image
                        .into(recipeImage);
            }

            // Populate ingredients list
            if (ingredients != null && !ingredients.isEmpty()) {
                ingredientsAdapter.setIngredients(ingredients);
            } else {
                Toast.makeText(this, "No ingredients available for this recipe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Failed to load recipe details", Toast.LENGTH_SHORT).show();
        }
    }
}