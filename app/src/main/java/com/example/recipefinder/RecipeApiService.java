package com.example.recipefinder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApiService {
    @GET("recipes/complexSearch")
    Call<RecipeResponse> searchRecipes(
            @Query("query") String query,
            @Query("apiKey") String apiKey
    );
    @GET("recipes/{id}/information")
    Call<Recipe> getRecipeDetails(
            @Path("id") int recipeId,
            @Query("apiKey") String apiKey
    );

}
