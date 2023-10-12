package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Categoria_Compra extends AppCompatActivity {

    FloatingActionButton addCompraBtn;

    RecyclerView recyclerViewCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_compra);

        addCompraBtn = findViewById(R.id.add_compra);
        recyclerViewCompra = findViewById(R.id.recyler_viewCompra);

        addCompraBtn.setOnClickListener((v) -> startActivity(new Intent(Categoria_Compra.this, Compra.class)));
    }
}