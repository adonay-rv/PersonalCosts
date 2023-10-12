package com.example.personalcosts;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    ImageButton iconosalir;
    ImageView imagePerfil;

    private final int REQUEST_PERMISSION = 123;
    Uri selectedImageUri;


    private TextView homeSaldoTextView;
    private Button btnAddPresupuesto;
    private Button btnResPresupuesto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iconosalir = view.findViewById(R.id.salirsesion);
        imagePerfil = view.findViewById(R.id.imagePerfil);


        //Se configura el boton para que permita al usuario cerrar sesion
        iconosalir.setOnClickListener((v) -> {
            new AlertDialog.Builder(requireContext()).setTitle("Cerrar sesión").
                    setMessage("¿Esta seguro de cerrar sesión?").setPositiveButton(android.R.string.yes,
                            (dialog, which) ->
                            {
                                startActivity(new Intent(requireContext(), LoginActivity.class));
                            }).setNegativeButton(android.R.string.cancel, null).setIcon(R.drawable.warning).show();
        });
        imagePerfil.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                mostrarOpcionesImagen();
            } else {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    abrirGaleria();
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                }
            }
        });

        homeSaldoTextView = view.findViewById(R.id.home_saldo);
        btnAddPresupuesto = view.findViewById(R.id.btn_addPresupuesto);
        btnResPresupuesto = view.findViewById(R.id.btn_resPresupuesto);

        btnAddPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPresupuestoDialog();
            }
        });

        btnResPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResPresupuestoDialog();
            }
        });
    }

    private ActivityResultLauncher<Intent> getContent =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImage = result.getData().getData();
                    // Maneja la imagen seleccionada según sea necesario.
                    imagePerfil.setImageURI(selectedImage);
                }
            });
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getContent.launch(intent);
    }

    private void mostrarOpcionesImagen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Imagen de perfil");
        builder.setMessage("¿Qué deseas hacer con la imagen?");
        builder.setPositiveButton("Cambiar", (dialog, which) -> abrirGaleria());
        builder.setNegativeButton("Borrar", (dialog, which) -> {
            // Si el usuario elige borrar, elimina la URI de la imagen y establece la imagen en blanco o null
            selectedImageUri = null;
            imagePerfil.setImageURI(null);
        });
        builder.setNeutralButton("Cancelar", null);
        builder.show();
    }
    private void showAddPresupuestoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Agregar Presupuesto");
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputValue = input.getText().toString();
                if (!inputValue.isEmpty()) {
                    double currentBalance = Double.parseDouble(homeSaldoTextView.getText().toString().replace("$", ""));
                    double newBalance = currentBalance + Double.parseDouble(inputValue);
                    homeSaldoTextView.setText(String.format("$%.2f", newBalance));
                    Toast.makeText(requireContext(), "Se agrego el monto con exito!", Toast.LENGTH_SHORT).show();
                } else {
                    // Muestra un mensaje de error al usuario, ya que no ingresó un valor válido.
                    Toast.makeText(requireContext(), "Ingresa un valor válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void showResPresupuestoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Restar Presupuesto");
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setPositiveButton("Restar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputValue = input.getText().toString();
                if (!inputValue.isEmpty()) {
                    double currentBalance = Double.parseDouble(homeSaldoTextView.getText().toString().replace("$", ""));
                    double newBalance = currentBalance - Double.parseDouble(inputValue);
                    homeSaldoTextView.setText(String.format("$%.2f", newBalance));
                    Toast.makeText(requireContext(), "Se resto el monto con exito!", Toast.LENGTH_SHORT).show();
                } else {
                    // Muestra un mensaje de error al usuario, ya que no ingresó un valor válido.
                    Toast.makeText(requireContext(), "Ingresa un valor válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}