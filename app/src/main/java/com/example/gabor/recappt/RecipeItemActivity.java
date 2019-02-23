package com.example.gabor.recappt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeItemActivity extends AppCompatActivity {

    TextView textRecipeItemName;
    TextView textRecipeItemCategory;
    TextView textRecipeItemTime;
    TextView textRecipeItemIngredients;
    TextView textRecipeItemSteps;
    TextView textRecipeItemNameDb;
    TextView textRecipeItemCategoryDb;
    TextView textRecipeItemTimeDb;
    TextView textRecipeItemIngredientsDb;
    TextView textRecipeItemStepsDb;
    TextView textRecipeID;
    ImageView imageViewRecipeItemPicture;
    int id;
    String recipeName;
    String recipeCategory;
    String recipeTime;
    String recipeIngredients;
    String recipeSteps;
    Bitmap recipePicture;
    DatabaseHelper db;
    Toolbar toolbarRecipeItem;

    ImageView imageViewPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        init();
        initBunde();
        getSupportActionBar().setTitle(recipeName);

        textRecipeItemNameDb.setText(recipeName);
        textRecipeItemCategoryDb.setText(recipeCategory);
        textRecipeItemTimeDb.setText(recipeTime);
        textRecipeItemIngredientsDb.setText(recipeIngredients);
        textRecipeItemStepsDb.setText(recipeSteps);
        String ide = Integer.toString(id);
        textRecipeID.setText(ide);
        imageViewRecipeItemPicture.setImageBitmap(recipePicture);

    }

    public void init(){

        toolbarRecipeItem = (Toolbar) findViewById(R.id.recipeItem_toolbar);
        toolbarRecipeItem.setLogo(R.drawable.napteszt);
        setSupportActionBar(toolbarRecipeItem);
     

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Ez hozza be a vissz agombot
        textRecipeID = (TextView)findViewById(R.id.textView_recipeID);
        textRecipeItemName = (TextView) findViewById(R.id.textView_recipeItemName);
        textRecipeItemNameDb = (TextView) findViewById(R.id.textView_recipeItemNameDb);
        textRecipeItemCategory = (TextView) findViewById(R.id.textView_recipeItemCategory);
        textRecipeItemCategoryDb  = (TextView) findViewById(R.id.textView_recipeItemCategoryDb);
        textRecipeItemTime = (TextView) findViewById(R.id.textView_recipeItemTime);
        textRecipeItemTimeDb = (TextView) findViewById(R.id.textView_recipeItemTimeDb);
        textRecipeItemIngredients = (TextView) findViewById(R.id.textView_recipeItemIngredients);
        textRecipeItemIngredientsDb = (TextView) findViewById(R.id.textView_recipeItemIngredientsDb);
        textRecipeItemSteps = (TextView) findViewById(R.id.textView_recipeItemSteps);
        textRecipeItemStepsDb = (TextView) findViewById(R.id.textView_recipeItemStepsDb);
        imageViewRecipeItemPicture = (ImageView) findViewById(R.id.imageView_recipeItemPicture);

        db = new DatabaseHelper(this);

    }

    public void initBunde()
    {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        recipeName = bundle.getString("recipeName");
        recipeCategory = bundle.getString("recipeCategory");
        recipeTime = bundle.getString("recipeTime");
        recipeIngredients = bundle.getString("recipeIngredients");
        recipeSteps = bundle.getString("recipeSteps");
        recipePicture = getIntent().getExtras().getParcelable("recipePicture");
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.recipe_item_menu, menu);   //Ez kellet a Pipához
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String msg=" ";
        switch(item.getItemId()) {

            case android.R.id.home:
                Intent intent = new Intent(RecipeItemActivity.this, BreakfastActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.action_delete:

                db.delete(id);
                Intent intent2 = new Intent(RecipeItemActivity.this, BreakfastActivity.class);
                startActivity(intent2);
                finish();
                Toast.makeText(RecipeItemActivity.this, recipeName+" is deleted !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_update:


                Bundle bundle = new Bundle();
                bundle.putString("recipeName", recipeName);
                bundle.putString("recipeCategory", recipeCategory);
                bundle.putString("recipeTime", recipeTime);
                bundle.putString("recipeIngredients", recipeIngredients);
                bundle.putString("recipeSteps", recipeSteps);
                bundle.putInt("id",id);
                bundle.putParcelable("recipePicture", recipePicture);

                Intent intent3 = new Intent(RecipeItemActivity.this, UpdateRecipeActivity.class);
                intent3.putExtras(bundle);
                startActivity(intent3);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


}
