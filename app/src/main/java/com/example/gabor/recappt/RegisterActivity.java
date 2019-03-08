package com.example.gabor.recappt;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText textUsername;
    EditText textPassword;
    EditText textConfirmPassword;
    EditText textFullName;
    EditText textEmail;
    Button buttonRegister;
    TextView textViewLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(LoginIntent);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = textUsername.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                String confirm_password = textConfirmPassword.getText().toString().trim();
                String fullname = textFullName.getText().toString().trim();
                String email = textEmail.getText().toString().trim();

                if(user.equals("")){
                    textUsername.setError("Username is missing!");
                }
                if(password.equals("")){
                    textPassword.setError("Password is missing!");
                }
                if(confirm_password.equals("")){
                    textConfirmPassword.setError("Confirm Password is missing!");
                }
                if(!confirm_password.equals(password)){
                    textConfirmPassword.setError("Passwords must be the same !");
                }
                if(fullname.equals("")){
                    textFullName.setError("Name is missing!");
                }
                if(email.equals("")){
                    textEmail.setError("E-mail is missing!");
                }

                if(user.matches("")||password.matches("")||fullname.matches("")||email.matches(""))
                {
                    Toast.makeText(RegisterActivity.this,"Field can not be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.equals(confirm_password)){


                        long val = db.addUser(user,password,fullname,email);
                        if (val > 0)
                        {Toast.makeText(RegisterActivity.this,"Successfull registration!",Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(moveToLogin);}
                        else {
                            Toast.makeText(RegisterActivity.this,"Username or Email alredy in use!",Toast.LENGTH_SHORT).show();
                        }

                    }

                else { Toast.makeText(RegisterActivity.this, "Email alredy in use!", Toast.LENGTH_SHORT).show();}



            }
        });
    }

    public void init()
    {
        db = new DatabaseHelper(this);
        textUsername = (EditText)findViewById(R.id.editText_username);
        textPassword = (EditText)findViewById(R.id.editText_password);
        textConfirmPassword=(EditText)findViewById(R.id.editText_confirm_password);
        textFullName=(EditText)findViewById(R.id.editTtext_fullname);
        textEmail=(EditText)findViewById(R.id.eddiText_email);
        buttonRegister = (Button)findViewById(R.id.button_register);
        textViewLogin = (TextView) findViewById(R.id.textView_login);
    }

}
