package com.example.personalcosts;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HomeFragment extends Fragment {

    ImageButton iconosalir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public  void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iconosalir = view.findViewById(R.id.salirsesion);


        //Se configura el boton para que permita al usuario cerrar sesion
        iconosalir.setOnClickListener((v) -> {
            new AlertDialog.Builder(requireContext()).setTitle("Cerrar sesión").
                    setMessage("¿Esta seguro de cerrar sesión?").setPositiveButton(android.R.string.yes,
                            (dialog, which) ->
                            {
                                startActivity(new Intent(requireContext(), LoginActivity.class));
                            }).setNegativeButton(android.R.string.cancel, null).setIcon(R.drawable.warning).show();
        });
    }
}