package com.example.gabor.recappt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="registerUser1.db";

    public static final String TABLE_USER="registeruser";
    public static final String USER_ID="ID";
    public static final String USER_USERNAME="username";
    public static final String USER_PASSWORD="password";
    public static final String USER_FULLNAME="fullname";
    public static final String USER_EMAIL="email";

    public static final String TABLE_RECIPE="recipes";
    public static final String RECIPE_ID="ID";
    public static final String RECIPE_NAME="recipe_name";
    public static final String RECIPE_CATEGORY="recipe_category";
    public static final String RECIPE_TIME="recipe_time";
    public static final String RECIPE_INGREDIENTS="recipe_ingredients";
    public static final String RECIPE_STEPS="recipe_steps";
    public static final String RECIPE_USER="recipe_user";

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_USER+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, fullname TEXT, email TEXT)");
        sqLiteDatabase.execSQL("create table "+TABLE_RECIPE+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, recipe_name TEXT UNIQUE, recipe_category TEXT, recipe_time TEXT, recipe_ingredients TEXT,recipe_steps TEXT, recipe_user TEXT)");
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
    public long addRecipe(String recipeName, String recipeCategory, String recipeTime, String recipeIngredients, String recipeSteps, String recipeUser){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("recipe_name", recipeName);
        contentValues.put("recipe_category", recipeCategory);
        contentValues.put("recipe_time", recipeTime);
        contentValues.put("recipe_ingredients", recipeIngredients);
        contentValues.put("recipe_steps", recipeSteps);
        contentValues.put("recipe_user", recipeUser);
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