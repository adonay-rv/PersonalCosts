package com.example.personalcosts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Se declaran las variables de instancia para los elementos de la interfaz
    //y la bd
    ImageButton iconosalir, menu_category;
    FloatingActionButton addCategoryBtn;
    RecyclerView recyclerView;

    MoneyDB MDB;
    ArrayList<String> idCategoria, NombreCategoria, ContenidoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconosalir = findViewById(R.id.salirsesion);
        addCategoryBtn = findViewById(R.id.add_category);
        recyclerView = findViewById(R.id.recyler_view);
        menu_category = findViewById(R.id.Menu_category);

        //Abre el activity para agregar una categoria
        addCategoryBtn.setOnClickListener((view) -> {Intent intent = new Intent(MainActivity.this, Categoria.class);
        startActivity(intent);
        });

        menu_category.setOnClickListener((view) -> {Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        //se inicializan la bd y las listas para almacenar los datos
        //de las categorias
        MDB =  new MoneyDB(MainActivity.this);
        idCategoria = new ArrayList<>();
        NombreCategoria = new ArrayList<>();
        ContenidoCategoria = new ArrayList<>();
    }
}