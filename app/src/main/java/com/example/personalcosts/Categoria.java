package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.Timestamp;

public class Categoria extends AppCompatActivity {

    EditText tittleCategoryEditText, contentCategoryEditText;
    ImageButton addCategoryBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        tittleCategoryEditText = findViewById(R.id.category_title_text);
        addCategoryBtn = findViewById(R.id.save_category_btn);
        contentCategoryEditText = findViewById(R.id.category_content_text);

        addCategoryBtn.setOnClickListener((v) -> saveCategory());
    }

    void saveCategory(){
        //se obtienen los valores ingresados por el usuario en los campos de título y contenido
        String titleCategory = tittleCategoryEditText.getText().toString();
        String contentCategory = contentCategoryEditText.getText().toString();

        if (titleCategory==null || titleCategory.isEmpty()) {
            tittleCategoryEditText.setError("El titulo es requerido");
            return;
        }
        // verifica si el contenido es nulo o está vacío. Si es así, se muestra un mensaje de error
        if (contentCategory==null || contentCategory.isEmpty()) {
            contentCategoryEditText.setError("El contenido está vacío");
            return;
        }

        //agregar la clase del modelo
        //Se crea una instancia de la clase
        ModeloCategoria modeloCategoria = new ModeloCategoria();
        // se establecen el título, el contenido
        modeloCategoria.setTitle(titleCategory);
        modeloCategoria.setContent(contentCategory);


        finish();

    }
}