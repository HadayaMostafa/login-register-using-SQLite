package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class register extends AppCompatActivity {
    EditText createUserName, CreateEmail ,CreatePassword ,fname ,lname;
    Button RegisterReg ,LoginReg ;
    TextView info ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        createUserName = findViewById(R.id.createUserName);
        CreateEmail = findViewById(R.id.CreateEmail);
        CreatePassword = findViewById(R.id.CreatePassword);


        RegisterReg = findViewById(R.id.RegisterReg);
        LoginReg = findViewById(R.id.LoginReg);

        info = findViewById(R.id.info);


        LoginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(register.this, MainActivity.class);
                startActivity(i);

            }
        });
        RegisterReg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String strfname = fname.getText().toString();
                String strlname = lname.getText().toString();
                String struserName = createUserName.getText().toString();
                String stremail = CreateEmail.getText().toString();
                String strpassword = CreatePassword.getText().toString();

                if (strfname.isEmpty() || strlname.isEmpty() || struserName.isEmpty()
                        || stremail.isEmpty() || strpassword.isEmpty()) {
                    info.setText("All fields required");
                    return;
                }

                try (dbConnection db = new dbConnection(register.this)) {
                    Users newUser = new Users(strfname, strlname, struserName, stremail, strpassword);

                    if (db.checkUser(stremail, struserName)) {
                        info.setText("User already exists!");
                    } else {
                        if (db.addUser(newUser)) {
                            info.setText("Registration Successful!");
                            Intent intent = new Intent(register.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            info.setText("Something error");
                            Log.e("Database Error","Something error");
                        }
                    }
                }
                catch (Exception e){
                    Log.e("Database Error", "Exception: " + e.getMessage(), e);
                }
            }
        });
    }}