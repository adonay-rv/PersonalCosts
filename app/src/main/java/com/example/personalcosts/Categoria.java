package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.Timestamp;

public class Categoria extends AppCompatActivity {

    EditText titleCategoryEditText, contentCategoryEditText;
    ImageButton addCategoryBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        titleCategoryEditText = findViewById(R.id.category_title_text);
        addCategoryBtn = findViewById(R.id.save_category_btn);
        contentCategoryEditText = findViewById(R.id.category_content_text);

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoneyDB moneyDB = new MoneyDB(Categoria.this);
                moneyDB.AgregarCategoria(titleCategoryEditText.getText().toString().trim(),
                        contentCategoryEditText.getText().toString().trim());
            }
        });
    }
}