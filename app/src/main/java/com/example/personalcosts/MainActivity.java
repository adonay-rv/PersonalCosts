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

        //Se configura el boton para que permita al usuario cerrar sesion
        iconosalir.setOnClickListener((v) -> {
            new AlertDialog.Builder(MainActivity.this).setTitle("Cerrar sesión").
                    setMessage("¿Esta seguro de cerrar sesión?").setPositiveButton(android.R.string.yes,
                            (dialog, which) ->
                            {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }).setNegativeButton(android.R.string.cancel, null).setIcon(R.drawable.warning).show();
        });
    }
}