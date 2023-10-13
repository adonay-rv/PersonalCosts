package com.example.personalcosts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class AdapterCompra extends FirestoreRecyclerAdapter<ClassDB, AdapterCompra.CompraViewHolder> {
    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterCompra(@NonNull FirestoreRecyclerOptions<ClassDB> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterCompra.CompraViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull ClassDB classDB) {
        holder.titleTextView1.setText(classDB.getTitleCompra());
        holder.contentTextView1.setText(classDB.getContentCompra());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Compra.class);
                intent.putExtra("noteIdentification", classDB.getNoteIdentification());
                // Asume que tienes un método getNoteIdentification() en tu clase ClassDB
                v.getContext().startActivity(intent);
            }
        });

        holder.botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrarNotaInFirebase(classDB.getNoteIdentification1());
            }
        });

    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_compra, parent, false);
        return new CompraViewHolder(view);
    }

    void BorrarNotaInFirebase(String NoteIdentification1){
        DocumentReference documentReference;
        documentReference = Utilidad.getCollectionReferenceForCategory().document(NoteIdentification1);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"Nota eliminada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No se puede eliminar la nota", Toast.LENGTH_SHORT).show();
                }
            }
        });
    };

    public class CompraViewHolder extends RecyclerView.ViewHolder {
        ImageButton botonEliminar;
        TextView titleTextView1, contentTextView1;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView1 = itemView.findViewById(R.id.nombre_vista_compra);
            contentTextView1 = itemView.findViewById(R.id.Datos_compra);
            botonEliminar = itemView.findViewById(R.id.DeleteCompra);
        }
    }
}
