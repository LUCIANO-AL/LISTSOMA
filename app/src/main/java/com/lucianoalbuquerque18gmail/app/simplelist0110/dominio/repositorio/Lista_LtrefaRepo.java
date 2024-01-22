package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.List_DeL_Tarefa;

import java.util.ArrayList;
import java.util.List;

public class Lista_LtrefaRepo {

  public SQLiteDatabase conexaolist;
  private DataBaseLista dataBaseLista;

  public Lista_LtrefaRepo(SQLiteDatabase conexaolist) {
    this.conexaolist = conexaolist;
  }


  public void inserirLista(List_DeL_Tarefa list_DeL_Tarefa){

    ContentValues contentValues = new ContentValues();
    contentValues.put("LISTATAREFA",list_DeL_Tarefa.LISTATAREFA);
    contentValues.put("DATALISTA",list_DeL_Tarefa.DATALISTA);

    conexaolist.insertOrThrow("relac_list_tar2", null, contentValues );
  }

  public void inserirListaC(List_DeL_Tarefa list_DeL_Tarefa){

    ContentValues contentValues = new ContentValues();
    contentValues.put("LISTATAREFA",list_DeL_Tarefa.LISTATAREFA+"_copia");
    contentValues.put("DATALISTA",list_DeL_Tarefa.DATALISTA);

    conexaolist.insertOrThrow("relac_list_tar2", null, contentValues );
  }

  public void alterarLista(List_DeL_Tarefa list_DeL_Tarefa){

    ContentValues contentValues = new ContentValues();
    contentValues.put("LISTATAREFA",list_DeL_Tarefa.LISTATAREFA);
    contentValues.put("DATALISTA",list_DeL_Tarefa.DATALISTA);


    String[] parametros = new String[1];
    parametros[0] = String.valueOf(list_DeL_Tarefa.ID_T);

    conexaolist.update("relac_list_tar2", contentValues,"ID_T = ? ", parametros);

  }

  public List<List_DeL_Tarefa> buscarTodasListas(){


    List<List_DeL_Tarefa> lista_de_lists = new ArrayList<List_DeL_Tarefa>();

//   conexaolist = dataBaseLista.getReadableDatabase();

    StringBuilder sql = new StringBuilder();
    sql.append(" SELECT ID_T, LISTATAREFA, DATALISTA");
    sql.append(" FROM relac_list_tar2 order by ID_T desc");

    Cursor resultado = conexaolist.rawQuery(sql.toString(), null);

    if(resultado.getCount() > 0) {

      resultado.moveToFirst();

      do {

        List_DeL_Tarefa list = new List_DeL_Tarefa();

        list.ID_T = resultado.getInt(resultado.getColumnIndexOrThrow("ID_T"));
        list.LISTATAREFA = resultado.getString(resultado.getColumnIndexOrThrow("LISTATAREFA"));
        list.DATALISTA = resultado.getString(resultado.getColumnIndexOrThrow("DATALISTA"));

        lista_de_lists.add(list);

      } while (resultado.moveToNext());


    }

    return lista_de_lists;
  }
}
