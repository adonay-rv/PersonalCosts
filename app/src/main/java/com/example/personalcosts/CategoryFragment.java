package com.example.personalcosts;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class CategoryFragment extends Fragment {

    FloatingActionButton addCategoryBtn;
    RecyclerView recyclerViewCategoria;
    ProjectAdapter projectAdapter;
    SearchView searchviewCategoria1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    public  void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addCategoryBtn = view.findViewById(R.id.add_category);
        recyclerViewCategoria = view.findViewById(R.id.recyler_viewCategoria);
        searchviewCategoria1 =view.findViewById(R.id.searchviewCategoria);

        addCategoryBtn.setOnClickListener((v) -> startActivity(new Intent(getActivity(), Categoria.class)));
        setupRecyclerView();

        searchviewCategoria1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BuscarNota(newText);
                return false;
            }
        });
    }

    void BuscarNota(String searchText) {
        Query query = Utilidad.getCollectionReferenceForCategory().orderBy("title").startAt(searchText).endAt(searchText + "\uf8ff");
        FirestoreRecyclerOptions<ClassDB> options = new FirestoreRecyclerOptions.Builder<ClassDB>().setQuery(query, ClassDB.class).build();

        projectAdapter.stopListening();
        projectAdapter = new ProjectAdapter(options, getActivity());
        recyclerViewCategoria.setAdapter(projectAdapter);
        projectAdapter.startListening();
    }
    void setupRecyclerView() {
        Query query = Utilidad.getCollectionReferenceForCategory();
        FirestoreRecyclerOptions<ClassDB> options = new FirestoreRecyclerOptions.
                Builder<ClassDB>().setQuery(query, ClassDB.class).build();
        recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(getActivity()));
        projectAdapter = new ProjectAdapter(options, getActivity());
        recyclerViewCategoria.setAdapter(projectAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        projectAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        projectAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        projectAdapter.notifyDataSetChanged();
    }
}