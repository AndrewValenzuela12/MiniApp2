package com.example.andrewvalenzuela.miniapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AndrewValenzuela on 3/10/18.
 */

public class SearchActivity extends AppCompatActivity {
    Spinner dietOptions;
    Spinner servingOptions;
    Spinner timeOptions;
    Button searchButton;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        dietOptions = findViewById(R.id.diet_options);
        servingOptions = findViewById(R.id.serving_options);
        timeOptions = findViewById(R.id.time_options);
        searchButton = findViewById(R.id.search_button);
        mContext = this;


        String[] mservingOptions = new String[]{"Select One", "less than 4", "4-6", "7-9", "more than 10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mservingOptions);
        servingOptions.setAdapter(adapter);

        String[] mtimeOptions = new String[]{"Select One", "30 minutes or less", "less than 1 hour", "more than 1 hour"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mtimeOptions);
        timeOptions.setAdapter(adapter1);

        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", mContext);
        ArrayList<String> mdietOptions = new ArrayList<>();
        mdietOptions.add("Select One");

        for (int i = 0; i < recipeList.size(); i++) {
            String holder = recipeList.get(i).dietLabel;
            if (mdietOptions.contains(holder) == false){
                mdietOptions.add(holder);
            }
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mdietOptions);
        dietOptions.setAdapter(adapter2);

    }

    public void onClickSearch (View view) {
        Intent myIntent = new Intent(SearchActivity.this, ResultActivity.class);
        myIntent.putExtra("diet", dietOptions.getSelectedItem().toString());
        myIntent.putExtra("serving", servingOptions.getSelectedItem().toString());
        myIntent.putExtra("time", timeOptions.getSelectedItem().toString());
        SearchActivity.this.startActivity(myIntent);
    }

}
