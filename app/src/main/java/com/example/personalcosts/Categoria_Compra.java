package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class Categoria_Compra extends AppCompatActivity {

    FloatingActionButton addCompraBtn;

    RecyclerView recyclerViewCompra;

    AdapterCompra adapterCompra;
    SearchView searchviewCategoria1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_compra);

        addCompraBtn = findViewById(R.id.add_compra);
        recyclerViewCompra = findViewById(R.id.recyler_viewCompra);
        addCompraBtn.setOnClickListener((v) -> startActivity(new Intent(Categoria_Compra.this, Compra.class)));
        setupRecyclerView();
    }

    void BuscarNota(String searchText) {
        Query query = UtilidadCompra.getCollectionReferenceForCompra().orderBy("titleCompra").startAt(searchText).endAt(searchText + "\uf8ff");
        FirestoreRecyclerOptions<ClassDB> options = new FirestoreRecyclerOptions.Builder<ClassDB>().setQuery(query, ClassDB.class).build();

        adapterCompra.stopListening();
        adapterCompra = new AdapterCompra(options, this);
        recyclerViewCompra.setAdapter(adapterCompra);
        adapterCompra.startListening();
    }

    void setupRecyclerView() {
        Query query = UtilidadCompra.getCollectionReferenceForCompra();
        FirestoreRecyclerOptions<ClassDB> options = new FirestoreRecyclerOptions.
                Builder<ClassDB>().setQuery(query, ClassDB.class).build();
        recyclerViewCompra.setLayoutManager(new LinearLayoutManager(this));
        adapterCompra = new AdapterCompra(options, this);
        recyclerViewCompra.setAdapter(adapterCompra);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterCompra.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterCompra.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterCompra.notifyDataSetChanged();
    }
}