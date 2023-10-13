package com.example.personalcosts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;


public class Categoria extends AppCompatActivity {
    EditText titlenote, contentNota;
    Button GuardarNota;
    String title, content, NoteIdentification;
    TextView TitleAE;
    boolean ModoEdicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        //Se inicializan los elementos de la interfaz
        titlenote = findViewById(R.id.category_title_text);
        GuardarNota = findViewById(R.id.save_category_btn);
        contentNota = findViewById(R.id.category_content_text);
        TitleAE = findViewById(R.id.page_title);

        GuardarNota.setOnClickListener(v -> GuardarCategoria());

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        NoteIdentification = getIntent().getStringExtra("noteIdentification");

        if (NoteIdentification != null && !NoteIdentification.isEmpty()) {
            ModoEdicion = true;
        }

        titlenote.setText(title);
        contentNota.setText(content);

        if (ModoEdicion) {
            TitleAE.setText("Editar nota");
        }
    }

    void GuardarCategoria() {
        String tilenoteEditText = titlenote.getText().toString();
        String contentNotaEditText = contentNota.getText().toString();

        if(tilenoteEditText.isEmpty()) {
            Toast.makeText(Categoria.this, "El titulo es requerido", Toast.LENGTH_SHORT).show();
            return;
        }else if(contentNotaEditText.isEmpty()){
            Toast.makeText(Categoria.this, "No hay contenido para guardar", Toast.LENGTH_SHORT).show();
            return;
        }
        ClassDB classDB = new ClassDB();
        classDB.setTitle(tilenoteEditText);
        classDB.setContent(contentNotaEditText);

        GuardarNotaFirebase(classDB);
    }

    void GuardarNotaEnGuardarNotaFirebase(ClassDB classDB){
        DocumentReference documentReference;
        documentReference = Utilidad.getCollectionReferenceForCategory().document();
        documentReference.set(classDB).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Categoria.this,"categoria guardada", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Categoria.this, "No se puede guardar la categoria", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void GuardarNotaFirebase(ClassDB classDB) { //Guarda los datos en el firestone
        DocumentReference documentReference; //Obtiene la referencia a la coleccion de notas utilizadas
        if (ModoEdicion) {
            documentReference = Utilidad.getCollectionReferenceForCategory().document(NoteIdentification);
            documentReference.set(classDB).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Categoria.this, "categoria editada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Categoria.this, "No se pueden guardar los cambios", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Utilidad.getCollectionReferenceForCategory().whereEqualTo("title", classDB.title).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot.isEmpty()) {
                            GuardarNotaEnGuardarNotaFirebase(classDB);
                        } else {
                            Toast.makeText(Categoria.this, "No puede guardar una categoria con un titulo ya existente", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Categoria.this, "No se pudo verificar si el titulo es existente", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

        public void iconsView(){
        //Para cuando seleccione un icono
        ImageView selectedImageView = findViewById(R.id.icon_category);
        ImageButton iconComida = findViewById(R.id.icon_comida);
        ImageButton iconCompras = findViewById(R.id.icon_compras);
        ImageButton iconHogar = findViewById(R.id.icon_hogar);
        ImageButton iconSalud = findViewById(R.id.icon_salud);
        ImageButton iconTransporte = findViewById(R.id.icon_transporte);
        ImageButton iconViajes = findViewById(R.id.icon_viajes);
        ImageButton iconMascota = findViewById(R.id.icon_mascota);
        ImageButton iconOtro = findViewById(R.id.icon_otro);

        // Agrega OnClickListener a cada ImageButton
        iconComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de comida
                selectedImageView.setImageResource(R.drawable.icon_comida);
            }
        });

        iconCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de compras
                selectedImageView.setImageResource(R.drawable.icon_compras);
            }
        });

        iconHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de hogar
                selectedImageView.setImageResource(R.drawable.icon_hogar);
            }
        });

        iconSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de comida
                selectedImageView.setImageResource(R.drawable.icon_salud);
            }
        });

        iconTransporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de compras
                selectedImageView.setImageResource(R.drawable.icon_transporte);
            }
        });

        iconViajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de hogar
                selectedImageView.setImageResource(R.drawable.icon_viajes);
            }
        });

        iconMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de compras
                selectedImageView.setImageResource(R.drawable.icon_mascota);
            }
        });

        iconOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualiza el ImageView con la imagen de hogar
                selectedImageView.setImageResource(R.drawable.icon_otro);
            }
        });
    }
}