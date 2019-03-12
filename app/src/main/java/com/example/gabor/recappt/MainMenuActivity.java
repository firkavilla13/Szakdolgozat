package com.example.gabor.recappt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {





    private DrawerLayout drawer;
    GridLayout mainGrid;
    TextView navUsername,navEmail;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        init();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.mainMenu_toolbar);
        setSupportActionBar(toolbar);

        setSingleEvent(mainGrid);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        navUsername = (TextView)header.findViewById(R.id.textView_nav_fullname);
        navEmail = (TextView)header.findViewById(R.id.textView_nav_email);

        SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String segedUser = sharedPreferences.getString("sharedUsername","Empty");

       // Cursor eredmeny2 = db.getUsername(segedUser); Nem használtam

        navUsername.setText("Welcome");
        navEmail.setText(segedUser.toString());


    }

    public void init()
    {

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        drawer = findViewById(R.id.drawer_layout);
        db = new DatabaseHelper(this);

    }

    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_recipe:
                Intent newrecipe = new Intent(MainMenuActivity.this,NewRecipeActivity.class);
                startActivity(newrecipe);
                finish();
                break;
            case R.id.nav_logout:
                Intent logout = new Intent(MainMenuActivity.this,MainActivity.class);
                startActivity(logout);
                finish();
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_fomenu:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void setSingleEvent(GridLayout mainGrid){
        //Minden leszármazottat loopolni

        for (int i=0;i<mainGrid.getChildCount();i++)
        {
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI==0)
                    {

                        Bundle bundle = new Bundle();
                        String category = "Breakfast";
                        bundle.putString("category", category);
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    }
                    else if(finalI==1)
                    {
                        Bundle bundle = new Bundle();
                        String category = "Lunch";
                        bundle.putString("category", category);
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    else if(finalI==2)
                    {
                        Bundle bundle = new Bundle();
                        String category = "Dinner";
                        bundle.putString("category", category);
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    else if(finalI==3)
                    {
                        Bundle bundle = new Bundle();
                        String category = "Dessert";
                        bundle.putString("category", category);
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(MainMenuActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
