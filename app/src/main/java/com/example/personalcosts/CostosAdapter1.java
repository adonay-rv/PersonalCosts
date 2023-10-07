package com.example.personalcosts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CostosAdapter1 extends RecyclerView.Adapter<CostosAdapter1.MyViewHolder> {
    private Context context;
    private ArrayList idCategoria, NombreCategoria, ContenidoCategoria;

    CostosAdapter1(Context context, ArrayList idCategoria, ArrayList NombreCategoria, ArrayList ContenidoCategoria){
        this.context = context;
        this.idCategoria = idCategoria;
        this.NombreCategoria = NombreCategoria;
        this.ContenidoCategoria = ContenidoCategoria;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vista_categoria, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.NombreCategoria.setText(String.valueOf(NombreCategoria.get(position)));
        holder.ContenidoCategoria.setText(String.valueOf(ContenidoCategoria.get(position)));
    }

    @Override
    public int getItemCount() {
        return idCategoria.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView NombreCategoria, ContenidoCategoria;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            NombreCategoria = itemView.findViewById(R.id.titulo_categoria);
            ContenidoCategoria = itemView.findViewById(R.id.ContenidoCategoria);
        }
    }
}
