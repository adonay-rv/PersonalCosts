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

    ImageButton iconosalir;
    FloatingActionButton addCategoryBtn;
    RecyclerView recyclerView;

    MoneyDB MDB;
    ArrayList<String> idCategoria, NombreCategoria, ContenidoCategoria;

    CostosAdapter1 costosAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconosalir = findViewById(R.id.salirsesion);
        addCategoryBtn = findViewById(R.id.add_category);
        recyclerView = findViewById(R.id.recyler_view);

        addCategoryBtn.setOnClickListener((view) -> {Intent intent = new Intent(MainActivity.this, Categoria.class);
        startActivity(intent);
        });
        MDB =  new MoneyDB(MainActivity.this);
        idCategoria = new ArrayList<>();
        NombreCategoria = new ArrayList<>();
        ContenidoCategoria = new ArrayList<>();

        costosAdapter1 = new CostosAdapter1(MainActivity.this, idCategoria, NombreCategoria, ContenidoCategoria);
        recyclerView.setAdapter(costosAdapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        NuevaData();

        iconosalir.setOnClickListener((v) -> {
            new AlertDialog.Builder(MainActivity.this).setTitle("Cerrar sesión").
                    setMessage("¿Esta seguro de cerrar sesión?").setPositiveButton(android.R.string.yes,
                            (dialog, which) ->
                            {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }).setNegativeButton(android.R.string.cancel, null).setIcon(R.drawable.warning).show();
        });
    }

    void NuevaData(){
        // Limpia las listas antes de agregar los datos
        idCategoria.clear();
        NombreCategoria.clear();
        ContenidoCategoria.clear();

        Cursor cursor = MDB.Data();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                idCategoria.add(cursor.getString(0));
                NombreCategoria.add(cursor.getString(1));
                ContenidoCategoria.add(cursor.getString(2));
            }
            costosAdapter1.notifyDataSetChanged();
        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    protected void onResume() {
        super.onResume();
        NuevaData();
    }
}