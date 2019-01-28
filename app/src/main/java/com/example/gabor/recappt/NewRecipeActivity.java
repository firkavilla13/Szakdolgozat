package com.example.gabor.recappt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewRecipeActivity extends AppCompatActivity {
    Toolbar newRecipeToolbar;
    Spinner spinnerCategory;
    EditText textRecipeName;
    EditText textRecipeTime;
    EditText textRecipeIngredients;
    EditText textRecipeSteps;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        db = new DatabaseHelper(this);
        newRecipeToolbar = (Toolbar) findViewById(R.id.newRecipe_toolbar);
        newRecipeToolbar.setLogo(R.drawable.napteszt);
        setSupportActionBar(newRecipeToolbar);
        getSupportActionBar().setTitle("New Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Ez hozza be a vissz agombot

        spinnerCategory=(Spinner)findViewById(R.id.spinner_recipeCategory);
        String [] categories={"Breakfast","Lunch","Dinner","Dessert"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerCategory.setAdapter(adapter);


        textRecipeName = (EditText)findViewById(R.id.editText_recipeName);
        textRecipeTime = (EditText)findViewById(R.id.editText_recipeTime);
        textRecipeIngredients = (EditText)findViewById(R.id.editText_recipeIngredients);
        textRecipeSteps = (EditText)findViewById(R.id.editText_recipeSteps);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_recipe_menu, menu);   //Ez kellet a Pipához
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg=" ";
        switch(item.getItemId()) {
            case R.id.action_pipa:
                SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
                String segedUser = sharedPreferences.getString("sharedUsername","Empty");

                String recipeName = textRecipeName.getText().toString().trim();
                String recipeCategory = spinnerCategory.getSelectedItem().toString().trim();
                String recipeTime = textRecipeTime.getText().toString().trim();
                String recipeIngredients = textRecipeIngredients.getText().toString().trim();
                String recipeSteps = textRecipeSteps.getText().toString().trim();
                String recipeUser = segedUser.toString().trim();



                if(recipeName.matches("")||recipeCategory.matches("")||recipeTime.matches("")||recipeIngredients.matches("")||recipeSteps.matches(""))
                {
                    Toast.makeText(NewRecipeActivity.this,"Field can not be empty!",Toast.LENGTH_SHORT).show();
                }
                else {
                    long val = db.addRecipe(recipeName, recipeCategory, recipeTime, recipeIngredients, recipeSteps, recipeUser);

                    if (val > 0) {
                        Toast.makeText(NewRecipeActivity.this, "New Recipe Added!", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(NewRecipeActivity.this, MainMenuActivity.class);
                        startActivity(moveToLogin);
                    } else {
                        Toast.makeText(NewRecipeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case android.R.id.home:
                Intent intent = new Intent(NewRecipeActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
                break;
        }
            return super.onOptionsItemSelected(item);
    }
}

