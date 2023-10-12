package com.example.personalcosts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.security.Timestamp;
public class Categoria extends AppCompatActivity {
    //se declaran las variables de instancia para los elementos
    //de la interfaz
    EditText titleCategoryEditText, contentCategoryEditText;
    Button addCategoryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        //Se inicializan los elementos de la interfaz
        titleCategoryEditText = findViewById(R.id.category_title_text);
        addCategoryBtn = findViewById(R.id.save_category_btn);
        contentCategoryEditText = findViewById(R.id.category_content_text);



        iconsView();

        //Boton para agregar una categoria a la bd]
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