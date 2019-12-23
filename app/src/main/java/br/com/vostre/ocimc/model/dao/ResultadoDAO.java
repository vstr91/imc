package br.com.vostre.ocimc.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.vostre.ocimc.model.Resultado;

@Dao
public interface ResultadoDAO {

    @Query("SELECT * FROM resultado ORDER BY data DESC")
    LiveData<List<Resultado>> listarTodos();

    @Query("SELECT * FROM resultado WHERE tipo = :tipo ORDER BY data DESC")
    LiveData<List<Resultado>> listarTodosPorTipo(String tipo);

    @Query("SELECT * FROM resultado WHERE id = :id")
    LiveData<Resultado> carregarPorId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserirTodos(List<Resultado> resultados);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserir(Resultado resultado);

    @Update
    void editar(Resultado resultado);

    @Delete
    void deletar(Resultado resultado);

    @Query("DELETE FROM resultado")
    void deletarTodos();

}
