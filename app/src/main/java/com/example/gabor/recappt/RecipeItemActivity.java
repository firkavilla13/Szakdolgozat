package com.example.gabor.recappt;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        init();
        initBunde();
        recipePicture = db.getImage(id);
        getSupportActionBar().setTitle(recipeName);


        textRecipeItemNameDb.setText(recipeName);
        textRecipeItemCategoryDb.setText(recipeCategory);
        textRecipeItemTimeDb.setText(recipeTime);
        textRecipeItemIngredientsDb.setText(recipeIngredients);
        textRecipeItemStepsDb.setText(recipeSteps);
        String ide = Integer.toString(id);

        textRecipeID.setText(ide);
        imageViewRecipeItemPicture.setImageBitmap(recipePicture);

        textRecipeItemIngredientsDb.setMovementMethod(new ScrollingMovementMethod());

    }

    public void init(){


        toolbarRecipeItem = (Toolbar) findViewById(R.id.recipeItem_toolbar);
        toolbarRecipeItem.setLogo(R.drawable.recipes2);
        setSupportActionBar(toolbarRecipeItem);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
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
       // recipePicture = getIntent().getExtras().getParcelable("recipePicture");
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.recipe_item_menu, menu);   //Ez kellet a Pipához
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String msg=" ";

        Bundle bundle;
        switch(item.getItemId()) {

            case android.R.id.home:




               bundle = new Bundle();
                String category = recipeCategory;
                bundle.putString("category", category);
                Intent intent = new Intent(RecipeItemActivity.this, BreakfastActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;

            case R.id.action_delete:


                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Delete "+recipeName);
                builder.setMessage("Are you sure you want to delete "+recipeName+" ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        db.delete(id);

                        Toast.makeText(RecipeItemActivity.this, recipeName+" is deleted !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Bundle bundle = new Bundle();
                        String category = recipeCategory;
                        bundle.putString("category", category);
                        Intent intent = new Intent(RecipeItemActivity.this,BreakfastActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });


                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();

                    }

                });

                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.action_update:


                 bundle = new Bundle();
                bundle.putString("recipeName", recipeName);
                bundle.putString("recipeCategory", recipeCategory);
                bundle.putString("recipeTime", recipeTime);
                bundle.putString("recipeIngredients", recipeIngredients);
                bundle.putString("recipeSteps", recipeSteps);
                bundle.putInt("id",id);
               // bundle.putParcelable("recipePicture", recipePicture);

                Intent intent3 = new Intent(RecipeItemActivity.this, UpdateRecipeActivity.class);
                intent3.putExtras(bundle);
                startActivity(intent3);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


}
