package com.example.personalcosts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton iconosalir;

    FloatingActionButton addCategoryBtn;
    RecyclerView recyclerView;

    private CategoriaAdapter adapter;
    private List<ModeloCategoria> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconosalir = findViewById(R.id.salirsesion);
        addCategoryBtn = findViewById(R.id.add_category);
        recyclerView = findViewById(R.id.recyler_view);


        addCategoryBtn.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, Categoria.class)));


        iconosalir.setOnClickListener((v) -> {
            new AlertDialog.Builder(MainActivity.this).setTitle("Cerrar sesión").
                    setMessage("¿Esta seguro de cerrar sesión?").setPositiveButton(android.R.string.yes,
                            (dialog, which) ->
                            {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }).setNegativeButton(android.R.string.cancel, null).setIcon(R.drawable.warning).show();
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}