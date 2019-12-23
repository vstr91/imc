package br.com.vostre.ocimc;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.vostre.ocimc.databinding.ActivityMainBinding;
import br.com.vostre.ocimc.databinding.ActivityResultadoBinding;
import br.com.vostre.ocimc.model.Resultado;
import br.com.vostre.ocimc.viewModel.ResultadosViewModel;

public class ResultadoActivity extends BaseActivity {

    ActivityResultadoBinding binding;
    ResultadosViewModel viewModel;
    Double resultado = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resultado);
        super.onCreate(savedInstanceState);

        binding.setView(this);

        viewModel = ViewModelProviders.of(this).get(ResultadosViewModel.class);
        viewModel.resultados.observe(this, resultadosObserver);

        setTitle("Histórico");

    }

    Observer<List<Resultado>> resultadosObserver = new Observer<List<Resultado>>() {
        @Override
        public void onChanged(List<Resultado> resultados) {
            List<BarEntry> entries = new ArrayList<>();

            for (Resultado r : resultados) {
                entries.add(new BarEntry(r.getData().getMillis(), r.getResultado().floatValue()));
            }

            BarDataSet dataSet = new BarDataSet(entries, "Label"); // add entries to dataset
            BarData barData = new BarData(dataSet);
            binding.chart.setData(barData);
            binding.chart.invalidate(); // refresh

        }
    };

}
