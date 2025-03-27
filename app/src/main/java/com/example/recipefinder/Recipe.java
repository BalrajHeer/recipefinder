package com.example.recipefinder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {
    @SerializedName("id")  // Ensure it matches Spoonacular API JSON response
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("extendedIngredients") // Maps the list of ingredients from the API response
    private List<ExtendedIngredient> extendedIngredients;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }
}