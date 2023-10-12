package com.example.personalcosts;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryFragment extends Fragment {

    FloatingActionButton addCategoryBtn;
    RecyclerView recyclerViewCategoria;

    MoneyDB MDB;

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


        //Abre el activity para agregar una categoria
        addCategoryBtn.setOnClickListener((v) -> {Intent intent = new Intent(requireContext(), Categoria.class);
            startActivity(intent);
        });
    }
}