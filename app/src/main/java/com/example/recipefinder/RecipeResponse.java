package com.example.recipefinder;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecipeResponse {
    @SerializedName("results")
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
