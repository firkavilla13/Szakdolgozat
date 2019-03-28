package com.example.gabor.recappt;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.sql.Blob;

public class Recipe {

    public int id;
    public String name,category,time,ingredients,steps;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingridients) {
        this.ingredients = ingridients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }





}
