package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView registerbtncview;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerbtncview = findViewById(R.id.txtCreateAccountBtn);
        btnRegister = findViewById(R.id.btnCreateAccount);

        btnRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
        registerbtncview.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateAccount.class)));
    }

    @Override
    public void onBackPressed() {
        //Evita que se pueda retroceder desde el login
        finishAffinity();
    }
}