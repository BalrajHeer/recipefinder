package com.example.recipefinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private List<ExtendedIngredient> ingredients = new ArrayList<>();

    public void setIngredients(List<ExtendedIngredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        ExtendedIngredient ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getOriginal());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;

        IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
        }
    }
}