package com.example.personalcosts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

public class Compra extends AppCompatActivity {
    EditText titlenote, contentNota;
    Button GuardarNota;
    String titleCompra, contentCompra, NoteIdentification1;
    TextView TitleAE;
    boolean ModoEdicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        titlenote = findViewById(R.id.nombre_compra);
        GuardarNota = findViewById(R.id.save_compra);
        contentNota = findViewById(R.id.contenido_compra);
        TitleAE = findViewById(R.id.tiulo_compras);

        GuardarNota.setOnClickListener(v -> GuardarCompra());

        titleCompra = getIntent().getStringExtra("titleCompra");
        contentCompra = getIntent().getStringExtra("contentCompra");
        NoteIdentification1 = getIntent().getStringExtra("noteIdentification");

        if (NoteIdentification1 != null && !NoteIdentification1.isEmpty()) {
            ModoEdicion = true;
        }

        titlenote.setText(titleCompra);
        contentNota.setText(contentCompra);

        if (ModoEdicion) {
            TitleAE.setText("Editar nota");
        }
    }

    void GuardarCompra() {
        String tilenoteEditText = titlenote.getText().toString();
        String contentNotaEditText = contentNota.getText().toString();

        if (tilenoteEditText.isEmpty()) {
            Toast.makeText(Compra.this, "El titulo es requerido", Toast.LENGTH_SHORT).show();
            return;
        } else if (contentNotaEditText.isEmpty()) {
            Toast.makeText(Compra.this, "No hay contenido para guardar", Toast.LENGTH_SHORT).show();
            return;
        }
        ClassDB classDB = new ClassDB();
        classDB.setTitleCompra(tilenoteEditText);
        classDB.setContentCompra(contentNotaEditText);

        GuardarCompraFirebase(classDB);
    }

    void GuardarCompraEnGuardarNotaFirebase(ClassDB classDB) {
        DocumentReference documentReference;
        documentReference = UtilidadCompra.getCollectionReferenceForCompra().document();
        documentReference.set(classDB).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Compra.this, "compra guardada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Compra.this, "No se puede guardar la nota", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void GuardarCompraFirebase(ClassDB classDB) { //Guarda los datos en el firestone
        DocumentReference documentReference; //Obtiene la referencia a la coleccion de compras utilizadas
        if (ModoEdicion) {
            // Actualizar la nota existente
            documentReference = UtilidadCompra.getCollectionReferenceForCompra().document(NoteIdentification1);
        } else {
            // Crear una nueva nota
            documentReference = UtilidadCompra.getCollectionReferenceForCompra().document();
        }
        documentReference.set(classDB).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Compra.this, ModoEdicion ? "compra editada" : "compra guardada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Compra.this, "No se pueden guardar los cambios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}