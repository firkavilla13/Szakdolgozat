package com.example.gabor.recappt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewRecipeActivity extends AppCompatActivity {
    Toolbar newRecipeToolbar;
    Spinner spinnerCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        newRecipeToolbar = (Toolbar) findViewById(R.id.newRecipe_toolbar);
        newRecipeToolbar.setLogo(R.drawable.napteszt);
        setSupportActionBar(newRecipeToolbar);
        getSupportActionBar().setTitle("New Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Ez hozza be a vissz agombot

        spinnerCategory=(Spinner)findViewById(R.id.spinner_Category);
        String [] categories={"Breakfast","Lunch","Dinner","Dessert"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerCategory.setAdapter(adapter);



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
                msg = "Pipa";
                Toast.makeText(this,msg+"Checked",Toast.LENGTH_LONG).show();
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

