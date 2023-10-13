package com.example.personalcosts;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class HomeFragment extends Fragment {

    ImageButton iconosalir;
    ImageView imagePerfil;



    private TextView homeSaldoTextView, nameUserTextView;
    private Button btnAddPresupuesto, btnResPresupuesto;

    //Para mostrar nombre del usuario
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    //Para guardar imagen en FireBase Storge
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    //imagen
    private ActivityResultLauncher<Intent> pickImageLauncher;


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
        nameUserTextView = view.findViewById(R.id.name_user);

        //Para mostrar el nombre del usuario
        if (user != null) {
            String userName = user.getDisplayName();

            if (userName != null && !userName.isEmpty()) {
                nameUserTextView.setText(userName);
            } else {
                nameUserTextView.setText("Nombre de usuario no disponible");
            }
        } else {
            nameUserTextView.setText("Usuario no autenticado");
        }


        //Se configura el boton para que permita al usuario cerrar sesion
        iconosalir.setOnClickListener((v) -> {
            new AlertDialog.Builder(requireContext()).setTitle("Cerrar sesión").
                    setMessage("¿Esta seguro de cerrar sesión?").setPositiveButton(android.R.string.yes,
                            (dialog, which) ->
                            {
                                startActivity(new Intent(requireContext(), LoginActivity.class));
                            }).setNegativeButton(android.R.string.cancel, null).setIcon(R.drawable.warning).show();
        });

        //Para subir la imagen
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        StorageReference imageRef = storageRef.child("profile_images/" + user.getUid() + ".jpg");
                        UploadTask uploadTask = imageRef.putFile(selectedImageUri);
                        uploadTask.addOnCompleteListener(requireActivity(), task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(requireContext(), "Imagen subida con éxito", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        imagePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectionDialog();
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
    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Seleccionar Imagen");

        builder.setPositiveButton("Desde la Galería", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Abre la galería para seleccionar una imagen
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickImageLauncher.launch(intent);
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