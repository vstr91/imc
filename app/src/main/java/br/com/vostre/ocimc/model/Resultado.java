package br.com.vostre.ocimc.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.UUID;

@Entity()
public class Resultado {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private Double resultado;

    @NonNull
    private DateTime data;

    @NonNull // imc, vet, etc.
    private String tipo;

    public Resultado(){
        this.setId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public DateTime getData() {
        return data;
    }

    public void setData(DateTime data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean valida(Resultado resultado){

        if(resultado.getResultado() != null && !resultado.getTipo().isEmpty() && !resultado.getTipo().equals("")){
            return true;
        } else{
            return false;
        }

    }

}
