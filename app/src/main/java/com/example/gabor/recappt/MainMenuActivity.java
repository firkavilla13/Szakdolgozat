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
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    //Új szédszedni ,hogy a főmenu is egy fragment legyen és akkor abba tenni bele a grid view-s szart ne fordítva

    private DrawerLayout drawer;
    GridLayout mainGrid;
    TextView navUsername,navEmail;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.mainMenu_toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.removeHeaderView(navigationView.getHeaderView(0));



        SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String segedUser = sharedPreferences.getString("sharedUsername","Empty");
        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        navUsername = (TextView)header.findViewById(R.id.textView_nav_fullname);
        navEmail = (TextView)header.findViewById(R.id.textView_nav_email);
        db = new DatabaseHelper(this);
        Cursor eredmeny2 = db.getMinden(segedUser);
        navUsername.setText("Welcome");
        navEmail.setText(segedUser.toString());
        StringBuilder stringBufferEmail = new StringBuilder();

        if (eredmeny2 != null && eredmeny2.getColumnCount()>0){

            while(eredmeny2.moveToNext())
            {

            }
        }

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
            case R.id.nav_profil:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_recipe:
                Intent h = new Intent(MainMenuActivity.this,NewRecipeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtra("info","This is Activity from card item with index "+finalI);
                        startActivity(intent);

                    }
                    else if(finalI==1)
                    {
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtra("info","This is Activity from card item with index "+finalI);
                        startActivity(intent);
                    }
                    else if(finalI==2)
                    {
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtra("info","This is Activity from card item with index "+finalI);
                        startActivity(intent);
                    }
                    else if(finalI==3)
                    {
                        Intent intent = new Intent(MainMenuActivity.this,BreakfastActivity.class);
                        intent.putExtra("info","This is Activity from card item with index "+finalI);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainMenuActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
