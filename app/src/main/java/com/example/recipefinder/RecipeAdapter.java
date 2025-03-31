package com.example.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes = new ArrayList<>();
    private OnRecipeImageClickListener listener;
    private final Context context;

    // Constructor
    public RecipeAdapter(Context context) {
        this.context = context;
    }

    // Constructor or method to set the listener
    public void setOnRecipeImageClickListener(OnRecipeImageClickListener listener) {
        this.listener = listener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeTitle.setText(recipe.getTitle());
        Picasso.get().load(recipe.getImageUrl()).placeholder(R.drawable.ic_placeholder).into(holder.recipeImage);

        // Handle click event for the image
        holder.recipeImage.setOnClickListener(v -> {
            // Navigate to IngredientsActivity when image is clicked

            if (listener != null) {

                int recipeId = recipe.getId();
                listener.onRecipeImageClick(recipeId);
            }
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }



    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;
        ImageView recipeImage;

        RecipeViewHolder(View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);
        }
    }
    // Listener interface for click events
    public interface OnRecipeImageClickListener {
        void onRecipeImageClick(int recipeId);
    }

}

