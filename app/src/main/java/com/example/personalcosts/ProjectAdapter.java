package com.example.personalcosts;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProjectAdapter extends FirestoreRecyclerAdapter<ClassDB, ProjectAdapter.NoteViewHolder> {

    Context context;

    public ProjectAdapter(@NonNull FirestoreRecyclerOptions<ClassDB> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProjectAdapter.NoteViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull ClassDB classDB) {
        holder.title_txt.setText(classDB.title);
        holder.content_txt.setText(classDB.content);

        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, Categoria.class);
            intent.putExtra("title", classDB.title);
            intent.putExtra("content", classDB.content);
            String Category_Identify = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("Category_Identify", Category_Identify);
            context.startActivity(intent);
        });

        holder.DeleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try{
                    String Category_Identify = getSnapshots().getSnapshot(position).getId();
                    BorrarCategoriaFirebase(Category_Identify);
                }catch (Exception e){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    @Override
    public ProjectAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_categoria, parent, false);
        return new NoteViewHolder(view);
    }

    void BorrarCategoriaFirebase(String Category_Identify){
        DocumentReference documentReference;
        documentReference =  Utilidad.getCollectionReferenceForNotes().document(Category_Identify);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Categoria eliminada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No se puede eliminar la categoria", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        ImageButton DeleteBtn;

        TextView title_txt, content_txt;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title_txt = itemView.findViewById(R.id.titulo_categoria);
            content_txt = itemView.findViewById(R.id.ContenidoCategoria);
            DeleteBtn = itemView.findViewById(R.id.DeleteCategoria);
        }
    }
}
