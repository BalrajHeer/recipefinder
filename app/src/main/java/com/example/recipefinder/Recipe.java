package com.example.recipefinder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {
    @SerializedName("id")  // Maps to "id" field in Spoonacular API
    private int id;

    @SerializedName("title") // Maps to "title" field in Spoonacular API
    private String title;

    @SerializedName("image") // Maps to "image" field in Spoonacular API
    private String imageUrl;

    @SerializedName("extendedIngredients") // Maps to "extendedIngredients" field in Spoonacular API
    private List<ExtendedIngredient> extendedIngredients;

    // Getter for ID
    public int getId() {
        return id;
    }

    // Setter for ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter for Title
    public String getTitle() {
        return title;
    }

    // Setter for Title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for Image URL
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter for Image URL
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Getter for Extended Ingredients
    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    // Setter for Extended Ingredients
    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }
}