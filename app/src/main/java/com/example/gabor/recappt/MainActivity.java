package com.example.gabor.recappt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    DatabaseHelper db;
    Animation sapkaAnim;
    ImageView sapkaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
       sapkaImageView.startAnimation(sapkaAnim);



        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextUsername.getText().toString().trim();
                String password =  editTextPassword.getText().toString().trim();

                if(editTextUsername.getText().toString().equals("")){
                    editTextUsername.setError("Username can't be empty!");
                }
                if(editTextPassword.getText().toString().equals("")) {
                    editTextPassword.setError("Password can't be empty!");
                }

                Boolean res = db.checkUser(user, password);

                  if(res == true)
                    {
                        SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("sharedUsername",user);
                        editor.commit();
                        Toast.makeText(MainActivity.this,"Logged in!",Toast.LENGTH_SHORT).show();
                        Intent MainMenu = new Intent(MainActivity.this,MainMenuActivity.class);
                        startActivity(MainMenu);
                        finish();
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }

                    else if (res == false)
                    {
                        Toast.makeText(MainActivity.this,"Invalid username or password!",Toast.LENGTH_SHORT).show();
                    }
                }
        });


    }

    public void init()
    {
        db = new DatabaseHelper(this);
        editTextUsername = (EditText)findViewById(R.id.editText_username);
        editTextPassword = (EditText)findViewById(R.id.editText_password);
        buttonLogin = (Button)findViewById(R.id.button_login);
        textViewRegister = (TextView) findViewById(R.id.textView_register);
        sapkaAnim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bouncing);
        sapkaImageView =(ImageView)findViewById(R.id.imageView_logosapka);
    }
}