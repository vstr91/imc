package br.com.vostre.ocimc.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import org.joda.time.DateTime;

import java.util.List;

import br.com.vostre.ocimc.model.Resultado;
import br.com.vostre.ocimc.model.dao.AppDatabase;

public class ResultadosViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public LiveData<List<Resultado>> resultados;
    public Resultado resultado;

    public static MutableLiveData<Integer> retorno;

    public LiveData<List<Resultado>> getResultados() {
        return resultados;
    }

    public void setResultados(LiveData<List<Resultado>> resultados) {
        this.resultados = resultados;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public ResultadosViewModel(Application app){
        super(app);
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        resultado = new Resultado();
        resultados = appDatabase.resultadoDAO().listarTodos();

        retorno = new MutableLiveData<>();
        retorno.setValue(-1);
    }

    public void salvarResultado(Resultado resultado){

        if(resultado.valida(resultado)){
            resultado.setData(DateTime.now());
            add(resultado);
        } else{
            retorno.setValue(0);
        }

    }

    // adicionar

    public void add(final Resultado resultado) {
        new addAsyncTask(appDatabase).execute(resultado);
    }

    private static class addAsyncTask extends AsyncTask<Resultado, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Resultado... params) {
            db.resultadoDAO().inserir((params[0]));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ResultadosViewModel.retorno.setValue(1);
        }

    }

    // fim adicionar

    // editar

    public void edit(final Resultado resultado) {
        new editAsyncTask(appDatabase).execute(resultado);
    }

    private static class editAsyncTask extends AsyncTask<Resultado, Void, Void> {

        private AppDatabase db;

        editAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Resultado... params) {
            db.resultadoDAO().editar((params[0]));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ResultadosViewModel.retorno.setValue(1);
        }

    }

    // fim editar

}
