package com.example.gabor.recappt;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.gabor.recappt.Adapter.SearchAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class AllRecipeActivity extends AppCompatActivity {

    Toolbar newRecipeToolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    MaterialSearchBar materialSearchBar;

    List<String> suggestList = new ArrayList<>();
    DatabaseHelper db;
    String recipeUser;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipe);
        init();


        //Username kinyerése hogy csak azokat listázza ami ahhoz az userhez tartozik akivel beléptünk
        SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String segedUser = sharedPreferences.getString("sharedUsername","Empty");
        recipeUser = segedUser.toString().trim();

        //Setup search bar
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar);
        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);

        //loadSuggestList(); Erre nincs szükségem hogy lássam az előző kereséseket arra lenne jó

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for (String search:suggestList)
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                {
                    adapter = new SearchAdapter(getBaseContext(),db.getRecipesByCategory(category,recipeUser));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }
            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
        //Init adapter

        adapter = new SearchAdapter(this,db.getAllRecipesByUser(recipeUser));
        recyclerView.setAdapter(adapter);

    }

    public void init(){

        //init toolbar
        newRecipeToolbar = (Toolbar) findViewById(R.id.recipeCategory_toolbar);
        newRecipeToolbar.setLogo(R.drawable.recipes2);
        setSupportActionBar(newRecipeToolbar);
        newRecipeToolbar.setBackgroundColor(Color.YELLOW);
        getSupportActionBar().setTitle("All Recipes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //init view
        recyclerView = (RecyclerView)findViewById(R.id.recycler_search);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //init Db
        db = new DatabaseHelper(this);



    }


    private void startSearch(String text) {

        adapter = new SearchAdapter(this,db.getRecipeByName(text,category));
        recyclerView.setAdapter(adapter);
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        String msg=" ";
        switch(item.getItemId()) {

            case android.R.id.home:
                Intent intent = new Intent(AllRecipeActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadSuggestList()  {
        // suggestList = db.getName();
        // materialSearchBar.setLastSuggestions(suggestList);

    }
    private void killActivity()
    {
        finish();
    }

}
