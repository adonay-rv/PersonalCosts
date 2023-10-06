package com.example.personalcosts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriaAdapter {



        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewName;
            public TextView textViewDescription;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.category_title_text);
                textViewDescription = itemView.findViewById(R.id.category_content_text);
            }
        }
    }



