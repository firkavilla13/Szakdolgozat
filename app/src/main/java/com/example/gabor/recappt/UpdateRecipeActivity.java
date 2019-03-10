package com.example.gabor.recappt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class UpdateRecipeActivity extends AppCompatActivity {
    Toolbar newRecipeToolbar;
    Spinner spinnerCategory;
    EditText textRecipeName;
    EditText textRecipeTime;
    EditText textRecipeIngredients;
    EditText textRecipeSteps;
    Button buttonRecipePicture;
    ImageView imageViewCamera;
    DatabaseHelper db;
    Bitmap recipePicture;

    int id;
    String recipeName;
    String recipeCategory;
    String recipeTime;
    String recipeIngredients;
    String recipeSteps;
    String ide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);
        initBunde();
        init();
        ide = Integer.toString(id);



        imageViewCamera.setImageBitmap(recipePicture);

        textRecipeName.setText(recipeName);
        textRecipeTime.setText(recipeTime);
        textRecipeIngredients.setText(recipeIngredients);
        textRecipeSteps.setText(recipeSteps);

        buttonRecipePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture,0);
            }
        });
    }

    public void init()
    {
        textRecipeName = (EditText)findViewById(R.id.editText_recipeName);
        textRecipeTime = (EditText)findViewById(R.id.editText_recipeTime);
        textRecipeIngredients = (EditText)findViewById(R.id.editText_recipeIngredients);
        textRecipeSteps = (EditText)findViewById(R.id.editText_recipeSteps);
        buttonRecipePicture = (Button)findViewById(R.id.button_recipePicture);
        imageViewCamera = (ImageView)findViewById(R.id.imageView_cameraPicture);

        newRecipeToolbar = (Toolbar) findViewById(R.id.newRecipe_toolbar);
        spinnerCategory=(Spinner)findViewById(R.id.spinner_recipeCategory);
        newRecipeToolbar.setLogo(R.drawable.recipes2);
        setSupportActionBar(newRecipeToolbar);
        getSupportActionBar().setTitle("Update Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Ez hozza be a vissza gombot

        String [] categories={"Breakfast","Lunch","Dinner","Dessert"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerCategory.setAdapter(adapter);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requestCode && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            recipePicture = (Bitmap) data.getExtras().get("data");
            imageViewCamera.setImageBitmap(recipePicture);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_recipe_menu, menu);   //Ez kellet a Pipához
        return true;
    }
    public static byte[] getBytes(Bitmap recipePicture) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        recipePicture.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg=" ";
        switch(item.getItemId()) {
            case R.id.action_pipa:

                if (imageViewCamera.equals(R.drawable.napocska)) {
                    Toast.makeText(UpdateRecipeActivity.this, "Take a picture!", Toast.LENGTH_SHORT).show();
                }
                else{


                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    String segedUser = sharedPreferences.getString("sharedUsername", "Empty");
                    String recipeName = textRecipeName.getText().toString().trim();
                    String recipeCategory = spinnerCategory.getSelectedItem().toString().trim();
                    String recipeTime = textRecipeTime.getText().toString().trim();
                    String recipeIngredients = textRecipeIngredients.getText().toString().trim();
                    String recipeSteps = textRecipeSteps.getText().toString().trim();
                    String recipeUser = segedUser.toString().trim();

                    if (recipeName.matches("") || recipeCategory.matches("") || recipeTime.matches("") || recipeIngredients.matches("") || recipeSteps.matches("")) {
                        Toast.makeText(UpdateRecipeActivity.this, "Field can not be empty!", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean val = db.updateRecipe(ide, recipeName, recipeCategory, recipeTime, recipeIngredients, recipeSteps , getBytes(recipePicture));

                        if (val==true) {
                            Toast.makeText(UpdateRecipeActivity.this, "Recipe Updated!", Toast.LENGTH_SHORT).show();

                            Bundle bundle = new Bundle();
                            bundle.putString("recipeName", recipeName);
                            bundle.putString("recipeCategory", recipeCategory);
                            bundle.putString("recipeTime", recipeTime);
                            bundle.putString("recipeIngredients", recipeIngredients);
                            bundle.putString("recipeSteps", recipeSteps);
                            bundle.putInt("id",id);
                            bundle.putParcelable("recipePicture", recipePicture);

                            Intent intent = new Intent(UpdateRecipeActivity.this, RecipeItemActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdateRecipeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case android.R.id.home:
                Bundle bundle = new Bundle();
                bundle.putString("recipeName", recipeName);
                bundle.putString("recipeCategory", recipeCategory);
                bundle.putString("recipeTime", recipeTime);
                bundle.putString("recipeIngredients", recipeIngredients);
                bundle.putString("recipeSteps", recipeSteps);
                bundle.putInt("id",id);
                bundle.putParcelable("recipePicture", recipePicture);

                Intent intent = new Intent(UpdateRecipeActivity.this, RecipeItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

