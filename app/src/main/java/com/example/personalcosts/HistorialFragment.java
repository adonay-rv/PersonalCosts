package com.example.personalcosts;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HistorialFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PieChart pieChart = view.findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(100f, "Comida"));
        entries.add(new PieEntry(225f, "Transporte"));
        entries.add(new PieEntry(59f, "Hogar"));
        entries.add(new PieEntry(80f, "Salud"));
        entries.add(new PieEntry(25f, "Compras"));

        PieDataSet dataSet = new PieDataSet(entries, "-Categorias");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        PieData pieData = new PieData(dataSet);
        // Personaliza el formato de las etiquetas
        pieData.setValueFormatter(new PercentFormatter(pieChart) {
            @Override
            public String getFormattedValue(float value) {
                return "$" + super.getFormattedValue(value);
            }
        });
        pieChart.setData(pieData);

        pieChart.getDescription().setText("Gastos por categoría");
        pieChart.invalidate();
    }
}
