package com.example.recipeappv3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/*This class concerns itself with retrieving the title/recipe name as well its ingredients by
taking the information/data by getting the intent sent by other classes and displays it in a textview*/

public class Recipe extends AppCompatActivity {

    private TextView titleText;
    private TextView ingredientsText;

    private String title, ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        titleText = (TextView) findViewById(R.id.titleView);
        ingredientsText = (TextView) findViewById(R.id.ingredientsView);

        Bundle bundle = getIntent().getExtras();

        titleText.setText(bundle.getString("Name"));
        ingredientsText.setText(bundle.getString("Ingredient"));
    }
}