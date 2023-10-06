package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int Splash = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent PantallaSplah = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(PantallaSplah);
                finish();
            }
        }, Splash);
    }
}