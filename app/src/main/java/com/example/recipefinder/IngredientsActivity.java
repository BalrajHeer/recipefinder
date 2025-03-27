package com.example.recipefinder;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        recyclerView = findViewById(R.id.recyclerViewIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter = new IngredientsAdapter();
        recyclerView.setAdapter(ingredientsAdapter);

        // Get the passed ingredients list from the Intent
        if (getIntent() != null && getIntent().hasExtra("ingredients")) {
            List<ExtendedIngredient> ingredients = (List<ExtendedIngredient>) getIntent().getSerializableExtra("ingredients");
            if (ingredients != null && !ingredients.isEmpty()) {
                ingredientsAdapter.setIngredients(ingredients);
            } else {
                Toast.makeText(this, "No ingredients available for this recipe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Failed to load ingredients", Toast.LENGTH_SHORT).show();
        }
    }
}