package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    Button iniciobtn;
    TextView textviewinicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        iniciobtn = findViewById(R.id.btnCreateAccount);
        textviewinicio = findViewById(R.id.txtInicarbtn);

        iniciobtn.setOnClickListener(v -> {
                    Toast.makeText(CreateAccount.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateAccount.this, LoginActivity.class));
                });
        textviewinicio.setOnClickListener(v -> startActivity(new Intent(CreateAccount.this, LoginActivity.class)));
    }
}