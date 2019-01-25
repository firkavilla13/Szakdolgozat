package com.example.gabor.recappt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        editTextUsername = (EditText)findViewById(R.id.editText_username);
        editTextPassword = (EditText)findViewById(R.id.editText_password);
        buttonLogin = (Button)findViewById(R.id.button_login);
        textViewRegister = (TextView) findViewById(R.id.textView_register);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextUsername.getText().toString().trim();
                String password =  editTextPassword.getText().toString().trim();
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
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Invalid username or password!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}