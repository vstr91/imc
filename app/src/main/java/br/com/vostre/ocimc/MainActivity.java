package br.com.vostre.ocimc;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import br.com.vostre.ocimc.databinding.ActivityMainBinding;
import br.com.vostre.ocimc.model.Resultado;
import br.com.vostre.ocimc.viewModel.ResultadosViewModel;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    ResultadosViewModel viewModel;
    Double resultado = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.onCreate(savedInstanceState);

        binding.setView(this);

        viewModel = ViewModelProviders.of(this).get(ResultadosViewModel.class);

        ocultaSetas();
        binding.btnSalvar.setVisibility(View.GONE);

        char separator = DecimalFormatSymbols.getInstance().getDecimalSeparator();

        binding.editTextAltura.setKeyListener(DigitsKeyListener.getInstance("0123456789" + separator));
        binding.editTextPeso.setKeyListener(DigitsKeyListener.getInstance("0123456789" + separator));


        setTitle("Calculadora IMC");

        binding.editTextPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Editable edit = binding.editTextAltura.getText();

                if(editable != null && !editable.toString().trim().isEmpty() && edit != null && !edit.toString().trim().isEmpty()){
                    calcular();
                }

            }
        });

        binding.editTextAltura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Editable edit = binding.editTextPeso.getText();

                if(editable != null && !editable.toString().trim().isEmpty() && edit != null && !edit.toString().trim().isEmpty()){
                    calcular();
                }

            }
        });

    }

    public void onClickBtnSalvar(View v){

        Resultado resultado = new Resultado();
        resultado.setResultado(this.resultado);
        resultado.setTipo("imc");

        viewModel.salvarResultado(resultado);
        viewModel.retorno.observe(this, retornoObserver);
    }

    public void onClickBtnHistorico(View v){

        Intent i = new Intent(this, ResultadoActivity.class);
        startActivity(i);
    }

    private void calcular(){

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);

        double peso = Double.parseDouble(binding.editTextPeso.getText().toString().replace(",", "."));
        double altura = Double.parseDouble(binding.editTextAltura.getText().toString().replace(",", "."));

        //System.out.println("AAA: "+peso+" | "+altura+" || "+(altura * altura));

        double resultado = peso / (altura * altura);

        ocultaSetas();

        if(resultado < 18.5){
            binding.imageViewSeta1.setVisibility(View.VISIBLE);
        } else if(resultado >= 18.5 && resultado < 25){
            binding.imageViewSeta2.setVisibility(View.VISIBLE);
        } else if(resultado >= 25 && resultado < 30){
            binding.imageViewSeta3.setVisibility(View.VISIBLE);
        } else if(resultado >= 30 && resultado < 35){
            binding.imageViewSeta4.setVisibility(View.VISIBLE);
        } else if(resultado >= 35 && resultado < 40){
            binding.imageViewSeta5.setVisibility(View.VISIBLE);
        } else if(resultado >= 40){
            binding.imageViewSeta6.setVisibility(View.VISIBLE);
        }

        this.resultado = resultado;

        binding.textViewResultado.setText(nf.format(resultado));
        binding.btnSalvar.setVisibility(View.VISIBLE);
    }

    private void ocultaSetas(){
        binding.imageViewSeta1.setVisibility(View.INVISIBLE);
        binding.imageViewSeta2.setVisibility(View.INVISIBLE);
        binding.imageViewSeta3.setVisibility(View.INVISIBLE);
        binding.imageViewSeta4.setVisibility(View.INVISIBLE);
        binding.imageViewSeta5.setVisibility(View.INVISIBLE);
        binding.imageViewSeta6.setVisibility(View.INVISIBLE);
    }

    Observer<Integer> retornoObserver = new Observer<Integer>() {
        @Override
        public void onChanged(Integer retorno) {

            if(retorno == 1){
                Toast.makeText(getApplicationContext(), "Resultado cadastrado!", Toast.LENGTH_SHORT).show();
            } else if(retorno == 0){
                Toast.makeText(getApplicationContext(),
                        "Dados necessários não informados. Por favor preencha " +
                                "todos os dados obrigatórios!",
                        Toast.LENGTH_SHORT).show();
            }

        }
    };

}
