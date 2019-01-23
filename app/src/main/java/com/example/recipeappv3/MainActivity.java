package com.example.recipeappv3;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 Main activity contains all that is needed for the addition of a new recipe and
 the button to advance to the list of recipes. It focuses on retrieving data from the
 textboxes to be used later in populating the database and eventually the listview.
*/

public class MainActivity extends AppCompatActivity {

    EditText RecipeName; // editText used to take text and store as variables in content provider
    EditText Ingedients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeName = (EditText) findViewById(R.id.RecipeName);
        Ingedients = (EditText) findViewById(R.id.Ingredients);

        RecipeListButton();// open recipelist activity
        test();//checks if recipe was added successfully
    }

    private void test() {
        Button test = (Button)findViewById(R.id.addButton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = RecipeName.getText().toString();
                String ingredient = Ingedients.getText().toString();
                Log.d("ggg", "" + name);
                ContentValues values = new ContentValues();
                values.put(MyContentProvider.name,name);
                values.put(MyContentProvider.ingredient, ingredient);
                Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URL,values);
                Toast.makeText(getBaseContext(), "New Recipe added successfully", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void RecipeListButton() {
        Button Recipes = (Button)findViewById(R.id.RecipeListButton);
        Recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecipeList.class);
                startActivity(intent);
            }
        });
    }
}
