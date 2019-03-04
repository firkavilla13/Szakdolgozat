package com.example.gabor.recappt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NewRecipeActivity extends AppCompatActivity {
    Toolbar newRecipeToolbar;
    Spinner spinnerCategory;
    EditText textRecipeName;
    EditText textRecipeTime;
    EditText textRecipeIngredients;
    EditText textRecipeSteps;
    Button buttonRecipePicture;
   // Button buttonRecipePictureGallery;

    ImageView imageViewCamera;
    DatabaseHelper db;
    Bitmap recipePicture;
    Bitmap defaultPicture;
    public static final int PICK_IMAGE = 0;
    public static final int IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        init();

        recipePicture =  BitmapFactory.decodeResource(getResources(), R.drawable.nopicture);
        imageViewCamera.setImageBitmap(recipePicture);

        buttonRecipePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture,IMAGE_CAPTURE);
            }
        });
       /* Valamiért galériából nem tudom lementeni normálisan, túl nagy a fájl akármit csinálok

       buttonRecipePicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent,PICK_IMAGE);
            }
        });*/
    }

    public void init()
    {
        textRecipeName = (EditText)findViewById(R.id.editText_recipeName);
        textRecipeTime = (EditText)findViewById(R.id.editText_recipeTime);
        textRecipeIngredients = (EditText)findViewById(R.id.editText_recipeIngredients);
        textRecipeSteps = (EditText)findViewById(R.id.editText_recipeSteps);
        buttonRecipePicture = (Button)findViewById(R.id.button_recipePicture);
        imageViewCamera = (ImageView)findViewById(R.id.imageView_cameraPicture);
        //buttonRecipePictureGallery = (Button)findViewById(R.id.button_recipePictureGallery);


        newRecipeToolbar = (Toolbar) findViewById(R.id.newRecipe_toolbar);
        spinnerCategory=(Spinner)findViewById(R.id.spinner_recipeCategory);
        newRecipeToolbar.setLogo(R.drawable.napteszt);
        setSupportActionBar(newRecipeToolbar);
        getSupportActionBar().setTitle("New Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Ez hozza be a vissza gombot

        String [] categories={"Breakfast","Lunch","Dinner","Dessert"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerCategory.setAdapter(adapter);

        db = new DatabaseHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

          if (requestCode == IMAGE_CAPTURE){
                 if (resultCode == RESULT_OK) {

                     recipePicture = (Bitmap) data.getExtras().get("data");
                     imageViewCamera.setImageBitmap(recipePicture);
                 }
         }
       else if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                try {
                    final Uri uri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(uri);
                    recipePicture = BitmapFactory.decodeStream(imageStream);

                    imageViewCamera.setImageBitmap(recipePicture);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }}

    public static byte[] getBytes(Bitmap recipePicture) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        recipePicture.compress(Bitmap.CompressFormat.PNG, 50, stream);
        return stream.toByteArray();
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


                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    String segedUser = sharedPreferences.getString("sharedUsername", "Empty");
                    String recipeName = textRecipeName.getText().toString().trim();
                    String recipeCategory = spinnerCategory.getSelectedItem().toString().trim();
                    String recipeTime = textRecipeTime.getText().toString().trim();
                    String recipeIngredients = textRecipeIngredients.getText().toString().trim();
                    String recipeSteps = textRecipeSteps.getText().toString().trim();
                    String recipeUser = segedUser.toString().trim();

                    if (recipeName.matches("") || recipeCategory.matches("") || recipeTime.matches("") || recipeIngredients.matches("") || recipeSteps.matches("")) {
                        Toast.makeText(NewRecipeActivity.this, "Field can not be empty!", Toast.LENGTH_SHORT).show();
                    } else
                        {
                        long val = db.addRecipe(recipeName, recipeCategory, recipeTime, recipeIngredients, recipeSteps, recipeUser, getBytes(recipePicture));

                        if (val > 0) {

                            Toast.makeText(NewRecipeActivity.this, "New Recipe Added!", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(NewRecipeActivity.this, MainMenuActivity    .class);
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

