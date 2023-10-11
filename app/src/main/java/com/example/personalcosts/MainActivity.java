package com.example.personalcosts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.personalcosts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    View rootView;  // Agregamos una referencia a la vista raíz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        // Agregamos un oyente para detectar el teclado
        rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootView.getHeight();
            int keypadHeight = screenHeight - r.bottom;

            // Si la altura del teclado es mayor que un cierto umbral, oculta el menú
            if (keypadHeight > screenHeight * 0.20) {
                binding.bottomNavigationView.setVisibility(View.GONE);
            } else {
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.home_) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.category_) {
                replaceFragment(new CategoryFragment());
            } else if (itemId == R.id.historial_) {
                replaceFragment(new HistorialFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(null);
    }
}
