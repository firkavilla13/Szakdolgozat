package com.example.gabor.recappt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="gabor.db";

    public static final String TABLE_USER="registeruser";
    public static final String USER_ID="ID";
    public static final String USER_USERNAME="username";
    public static final String USER_PASSWORD="password";
    public static final String USER_FULLNAME="fullname";
    public static final String USER_EMAIL="email";

    public static final String TABLE_RECIPE="recipes";
    public static final String RECIPE_ID="recipe_id";
    public static final String RECIPE_NAME="recipe_name";
    public static final String RECIPE_CATEGORY="recipe_category";
    public static final String RECIPE_TIME="recipe_time";
    public static final String RECIPE_INGREDIENTS="recipe_ingredients";
    public static final String RECIPE_STEPS="recipe_steps";
    public static final String RECIPE_USER="recipe_user";
    public static final String RECIPE_PICTURE="recipe_picture";

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_USER+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, fullname TEXT, email TEXT UNIQUE)");
        sqLiteDatabase.execSQL("create table "+TABLE_RECIPE+" (recipe_id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_name TEXT  , recipe_category TEXT, recipe_time TEXT, recipe_ingredients TEXT,recipe_steps TEXT, recipe_user TEXT, recipe_picture BLOB)");
    }
    public String getUsername(){
        return USER_USERNAME;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(sqLiteDatabase);
    }

    public boolean updateRecipe(String recipeId,String recipeName, String recipeCategory, String recipeTime, String recipeIngredients, String recipeSteps, byte [] recipePicture)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recipe_id",recipeId);
        contentValues.put("recipe_name", recipeName);
        contentValues.put("recipe_category", recipeCategory);
        contentValues.put("recipe_time", recipeTime);
        contentValues.put("recipe_ingredients", recipeIngredients);
        contentValues.put("recipe_steps", recipeSteps);
        contentValues.put("recipe_picture", recipePicture);
        db.update("recipes",contentValues,"recipe_id = ?",new String[] { recipeId});
        return true;
    }

    public long addRecipe(String recipeName, String recipeCategory, String recipeTime, String recipeIngredients, String recipeSteps, String recipeUser, byte [] recipePicture){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("recipe_name", recipeName);
        contentValues.put("recipe_category", recipeCategory);
        contentValues.put("recipe_time", recipeTime);
        contentValues.put("recipe_ingredients", recipeIngredients);
        contentValues.put("recipe_steps", recipeSteps);
        contentValues.put("recipe_user", recipeUser);
        contentValues.put("recipe_picture", recipePicture);
        long res = db.insert("recipes",null,contentValues);
        db.close();
        return res;
    }
    public long addUser(String user, String password, String fullname, String email){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return res;
    }
    public boolean checkUser(String username, String password){
        String [] columns = {USER_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USER_USERNAME + "=?" + " and " + USER_PASSWORD + "=?";
        String[]selectionArgs = { username, password};
        Cursor cursor = db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }

    public Bitmap getImage (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select "+RECIPE_PICTURE+" from "+TABLE_RECIPE+" where recipe_id=?",new String[]{String.valueOf(id)});
        if (cursor.moveToNext()){

            byte[] imag = cursor.getBlob(0);
            bt = BitmapFactory.decodeByteArray(imag,0,imag.length);
        }
        return bt;
    }

    public List<Recipe> getRecipe(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect ={"recipe_id","recipe_name","recipe_category","recipe_time","recipe_ingredients","recipe_steps","recipe_picture"};
        String tableName = "recipes";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<Recipe> result = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("recipe_name")));
                recipe.setCategory(cursor.getString(cursor.getColumnIndex("recipe_category")));
                recipe.setTime(cursor.getString(cursor.getColumnIndex("recipe_time")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("recipe_ingredients")));
                recipe.setSteps(cursor.getString(cursor.getColumnIndex("recipe_steps")));
             //   recipe.setPicture(cursor.getBlob(cursor.getColumnIndex("recipe_picture")));

                result.add(recipe);
            }while (cursor.moveToNext());
        }
        return result;
    }

    public void delete(int id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_RECIPE, RECIPE_ID + " = " + id, null);
        database.close();
    }

    public List<Recipe> getRecipesByCategory(String recipeCategory, String user){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect ={"recipe_id","recipe_name","recipe_category","recipe_time","recipe_ingredients","recipe_steps","recipe_user","recipe_picture"};
        String tableName = "recipes";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,"recipe_category LIKE ? AND recipe_user = ?",new String[]{"%"+recipeCategory+"%",user},null,null,null,null);
        List<Recipe> result = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("recipe_name")));
                recipe.setCategory(cursor.getString(cursor.getColumnIndex("recipe_category")));
                recipe.setTime(cursor.getString(cursor.getColumnIndex("recipe_time")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("recipe_ingredients")));
                recipe.setSteps(cursor.getString(cursor.getColumnIndex("recipe_steps")));
                //  recipe.setPicture(cursor.getBlob(cursor.getColumnIndex("recipe_picture")));

                result.add(recipe);
            }while (cursor.moveToNext());
        }
        return result;
    }

    public List<String> getName(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect ={"recipe_name"};
        String tableName = "recipes";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do{
                result.add(cursor.getString(cursor.getColumnIndex("recipe_name")));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public List<Recipe> getRecipeByName(String recipeName, String recipeCategory){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect ={"recipe_id","recipe_name","recipe_category","recipe_time","recipe_ingredients","recipe_steps","recipe_picture"};
        String tableName = "recipes";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,"recipe_name LIKE ? AND recipe_category = ?",new String[]{recipeName,recipeCategory},null,null,null,null);
        List<Recipe> result = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("recipe_name")));
                recipe.setCategory(cursor.getString(cursor.getColumnIndex("recipe_category")));
                recipe.setTime(cursor.getString(cursor.getColumnIndex("recipe_time")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("recipe_ingredients")));
                recipe.setSteps(cursor.getString(cursor.getColumnIndex("recipe_steps")));
              //  recipe.setPicture(cursor.getBlob(cursor.getColumnIndex("recipe_picture")));

                result.add(recipe);
            }while (cursor.moveToNext());
        }
        return result;
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+TABLE_RECIPE;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }



    public Cursor searchRecipe(String text)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+TABLE_RECIPE+" WHERE "+RECIPE_NAME+" Like '%"+text+"%'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getEmail(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select " +USER_FULLNAME+ " from "+TABLE_USER+" where "+USER_USERNAME+"="+"'username'",null);
        return res;
    }
    public Cursor getFullName(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select " +USER_FULLNAME+ " from "+TABLE_USER+" where "+USER_USERNAME+"="+"'username'",null);
        return res;
    }
    public Cursor getUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_USER+" where "+USER_USERNAME+"="+"'username'",null);
        return res;
    }



}