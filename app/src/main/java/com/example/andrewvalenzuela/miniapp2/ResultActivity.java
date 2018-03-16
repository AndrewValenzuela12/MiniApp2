package com.example.andrewvalenzuela.miniapp2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AndrewValenzuela on 3/10/18.
 */

public class ResultActivity extends AppCompatActivity {
    private ListView myListView;
    private TextView myTextView;
    private ImageButton myImageButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_result_activity);


        mContext = this;
        Intent intent = getIntent();
        String diet = intent.getStringExtra("diet");
        String serving = intent.getStringExtra("serving");
        String time = intent.getStringExtra("time");

        // use 3 filtered lists, diet serving & time
        // and every recipe title that is in all three of the filtered lists gets displayed as the result

        ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);

        // diet type
        ArrayList<Recipe> filtered = new ArrayList<>();
        for (int i = 0; i < recipeList.size(); i++) {
            Recipe check = recipeList.get(i);

            if (diet.equals("Select One")) {
                filtered.add(check);
            }
            else if (check.dietLabel.equals(diet)) {
                filtered.add(check);
            }
        }

        // serving
        ArrayList<Recipe> filtered1 = new ArrayList<>();
        for (int i = 0; i < recipeList.size(); i++) {
            Recipe check = recipeList.get(i);
            int servings = Integer.parseInt(check.servings);

            // "Select One", "less than 4", "4-6", "7-9", "more than 10"
            if (serving.equals("Select One")) {
                filtered1.add(check);
            } else if (serving.equals("less than 4")) {
                if (servings < 4) {
                    filtered1.add(check);
                }
            } else if (serving.equals("4-6")) {
                if (servings <= 6 && servings >= 4) {
                    filtered1.add(check);
                }
            } else if (serving.equals("7-9")) {
                if (servings <= 9 && servings >= 7) {
                    filtered1.add(check);
                }
            } else if (serving.equals("more than 10")) {
                if (servings >= 10) {
                    filtered1.add(check);
                }
            }
        }

        // time
        // "Select One", "30 minutes or less", "less than 1 hour", "more than 1 hour"
        ArrayList<Recipe> filtered2 = new ArrayList<>();
        for (int i = 0; i < recipeList.size(); i++) {
            Recipe check = recipeList.get(i);
            if (time.equals("Select One")) {
                filtered2.add(check);
            }
            else if (time.equals("more than 1 hour")) {
                if (check.prepTime.contains("hour") == true) {
                    filtered2.add(check);
                }
            }
            else if (time.equals("less than 1 hour")) {
                if (check.prepTime.contains("minutes") == true && check.prepTime.contains("hour") == false) {
                    filtered2.add(check);
                }
            }
            else if (time.equals("30 minutes or less")) {
                if (check.prepTime.contains("minutes") == true && check.prepTime.contains("hour") == false) {
                   // String str="sdfvsdf68fsdfsf8999fsdf09";
                   // String numberOnly= str.replaceAll("[^0-9]", "");
                  String check1 = check.prepTime.replaceAll("[^0-9]", "");
                  int timecheck = Integer.parseInt(check1);
                  if (timecheck <= 30) {
                      filtered2.add(check);
                  }
                }
            }
        }

        ArrayList<Recipe> filtered3 = new ArrayList<>();
        ArrayList<Recipe> finalFiltered = new ArrayList<>();
        // check to which recipes are in all three arraylists
        // go through filtered recipes and see if they are in filtered1; if they are add them to filtered3
        // go through filtered3 recipes and see if they are in filtered2; if they are add them to finalFiltered
        // return finalFiltered as arraylist of acceptable recipes
        for (int i = 0; i < filtered.size(); i++) {
            Recipe check = filtered.get(i);
            if (filtered1.contains(check) == true) {
                filtered3.add(check);
            }
        }

        for (int i = 0; i < filtered3.size(); i++) {
            Recipe check = filtered3.get(i);
            if (filtered2.contains(check) == true) {
                finalFiltered.add(check);
            }
        }


        // note: data to display is finalFiltered

        // create the adapter
        RecipeAdapter adapter = new RecipeAdapter(this, finalFiltered);

        // find the listview in the actual result activity
        // set the adapter to listview
        myListView = findViewById(R.id.final_recipe_list_view);
        myListView.setAdapter(adapter);

        myTextView = findViewById(R.id.number_recipes_text_view);
        myTextView.setText("Found " + finalFiltered.size() + " recipes");
    }



}
