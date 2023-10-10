package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.Timestamp;

public class Categoria extends AppCompatActivity {

    //se declaran las variables de instancia para los elementos
    //de la interfaz
    EditText titleCategoryEditText, contentCategoryEditText;
    ImageButton addCategoryBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        //Se inicializan los elementos de la interfaz
        titleCategoryEditText = findViewById(R.id.category_title_text);
        addCategoryBtn = findViewById(R.id.save_category_btn);
        contentCategoryEditText = findViewById(R.id.category_content_text);

        //Boton para agregar una categoria a la bd
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoneyDB moneyDB = new MoneyDB(Categoria.this);
                //Se crea una nueva instancia de la bd
                moneyDB.AgregarCategoria(titleCategoryEditText.getText().toString().trim(),
                        contentCategoryEditText.getText().toString().trim());
                //Se toman los valores ingresados y se recorta para omitir los espacios en blanco
            }
        });
    }
}