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
    //Listas para guardar los datos de las categorias
    CostosAdapter1 costosAdapter1; //adaptador para el recyclerView
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

        //inicializa el adaptador y se configura el RecyclerView
        costosAdapter1 = new CostosAdapter1(MainActivity.this, idCategoria, NombreCategoria, ContenidoCategoria);
        recyclerView.setAdapter(costosAdapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //Se cargan los datos dentro de las listas y se actuliza el recyclerview
        NuevaData();

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

    void NuevaData(){
        // Limpia las listas antes de agregar los datos
        idCategoria.clear();
        NombreCategoria.clear();
        ContenidoCategoria.clear();

        //Obtiene los datos de la bd
        Cursor cursor = MD.;

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
        NuevaData(); //Actualiza los datos cuando se reanuda la actividad
    }
}