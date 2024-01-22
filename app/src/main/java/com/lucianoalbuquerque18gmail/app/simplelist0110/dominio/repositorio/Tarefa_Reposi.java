package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class Tarefa_Reposi {
  private Context context;
  private SQLiteDatabase conexao;

  public Tarefa_Reposi(SQLiteDatabase conexao){

    this.conexao = conexao;
  }

  public Tarefa_Reposi(Context context) {
    this.context = context;
  }

  public void inserir(Tarefa tarefa){

    ContentValues contentValues = new ContentValues();
    contentValues.put("TAREFA",tarefa.Tarefa);
    contentValues.put("PRIORIDADE",tarefa.Prioridade);
    contentValues.put("ID_T",tarefa.ID_T);
    contentValues.put("ok",tarefa.check ? 1 : 0);

    conexao.insertOrThrow("l_tarefa2", null, contentValues );

  }

  public void excluirTdosItens(int ID_T){

    String[] parametros = new String[1];
    parametros[0] = String.valueOf(ID_T);

    conexao.delete("l_tarefa2","ID_T = ? ", parametros);
  }

  public void alterar(Tarefa tarefa){

    ContentValues contentValues = new ContentValues();

    contentValues.put("TAREFA",tarefa.Tarefa);
    contentValues.put("PRIORIDADE",tarefa.Prioridade);
    contentValues.put("ID_T",tarefa.ID_T);
    contentValues.put("ok",tarefa.check ? 1 : 0);

    String[] parametros = new String[1];
    parametros[0] = String.valueOf(tarefa.id_tarefa);

    conexao.update("l_tarefa2", contentValues,"id_tarefa = ?", parametros);

  }

  public void alterarcheck(Tarefa tarefa){

    ContentValues contentValues = new ContentValues();

    contentValues.put("ok",tarefa.check ? 1 : 0);


    String[] parametros = new String[1];
    parametros[0] = String.valueOf(tarefa.id_tarefa);

    conexao.update("l_tarefa", contentValues,"id_tarefa = ?", parametros);

  }

  public List<Tarefa> buscarTodos(int ID_T){

    List<Tarefa> tarefas = new ArrayList<Tarefa>();

    StringBuilder sq2 = new StringBuilder();
    sq2.append(" select * ");
    sq2.append(" from l_tarefa2");
    sq2.append(" where ID_T = ? order by Prioridade");

    String[] parametros = new String[1];
    parametros[0] = String.valueOf(ID_T);

    Cursor resultado = conexao.rawQuery(sq2.toString(),parametros);

    if(resultado.getCount() > 0) {

      resultado.moveToFirst();
      do {
        Tarefa tarefalista = new Tarefa();


        tarefalista.id_tarefa = resultado.getInt(resultado.getColumnIndexOrThrow("id_tarefa"));
        tarefalista.Tarefa = resultado.getString(resultado.getColumnIndexOrThrow("TAREFA"));
        tarefalista.Prioridade = resultado.getString(resultado.getColumnIndexOrThrow("PRIORIDADE"));
        tarefalista.ID_T = resultado.getInt(resultado.getColumnIndexOrThrow("ID_T"));
        tarefalista.check = resultado.getInt(resultado.getColumnIndexOrThrow("ok"))>= 1;


        tarefas.add(tarefalista);

      } while (resultado.moveToNext());
    }
    return tarefas;
  }
}
