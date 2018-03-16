package com.example.andrewvalenzuela.miniapp2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by AndrewValenzuela on 3/10/18.
 */

public class Recipe {
    // title
    public String title;
    public String imageURL;
    public String URL;
    public String description;
    public String servings;
    public String prepTime;
    public String dietLabel;



    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context) {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        try {
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            //for loop to go through each recipe in your recipes array

            for (int i = 0; i < recipes.length(); i++) {
                Recipe recipe = new Recipe();
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.imageURL = recipes.getJSONObject(i).getString("image");
                recipe.URL = recipes.getJSONObject(i).getString("url");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.servings = recipes.getJSONObject(i).getString("servings");
                recipe.prepTime = recipes.getJSONObject(i).getString("prepTime");
                recipe.dietLabel = recipes.getJSONObject(i).getString("dietLabel");

                //add to arrayList

                recipeList.add(recipe);


            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        // return arrayList
        return recipeList;
    }
        // helper method that loads from any Json file
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
