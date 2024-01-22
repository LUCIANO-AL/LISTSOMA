package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Divida;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Item;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Receber;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repo_Divida {

    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }

    private Context context;
    private SQLiteDatabase conexao;
    private Divida divida;

    public Repo_Divida(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public Repo_Divida(Context context) {
        this.context = context;
    }

    public void inserir(Divida divida){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOMEDIV",divida.NOMEDIV);
        contentValues.put("VALORDIV",divida.VALORDIV);
        contentValues.put("DATAVENC",divida.DATAVENC);
        contentValues.put("DATADIV",divida.DATADIV);
        //contentValues.put("ID_LDIV",divida.ID_LDIV);
        contentValues.put("PG",divida.PG ? 1 : 0);
        contentValues.put("NOMEDOCREDOR",divida.NOMEDOCREDOR);

        conexao.insertOrThrow("DIVIDAS2", null, contentValues );

    }

    public void excluirTdosItens(){

        String[] parametros = new String[1];
        //parametros[0] = String.valueOf(ID_LDIV);

        conexao.delete("DIVIDAS2",null, null);
    }

    public void alterar(Divida divida){

        ContentValues contentValues = new ContentValues();

        contentValues.put("NOMEDIV",divida.NOMEDIV);
        contentValues.put("VALORDIV",divida.VALORDIV);
        contentValues.put("DATAVENC",divida.DATAVENC);
        contentValues.put("DATADIV",divida.DATADIV);
        //contentValues.put("ID_LDIV",divida.ID_LDIV);
        contentValues.put("PG",divida.PG ? 1 : 0);
        contentValues.put("NOMEDOCREDOR",divida.NOMEDOCREDOR);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(divida.ID);

        conexao.update("DIVIDAS2", contentValues,"ID = ?", parametros);

    }

    public List<Divida> buscarTodosSpinner(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Divida> div = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM DIVIDAS2 WHERE NOMEDOCREDOR = ? AND julianday(");
        sq2.append(" substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?) ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida2= new Divida();

                divida2.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida2.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida2.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida2.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida2.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));
               // divida2.ID_LDIV = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID_LDIV"));
                divida2.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida2.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));

                div.add(divida2);

            } while (resultadodiv.moveToNext());
        }

        resultadodiv.close();
        return div;
    }

    public List<Divida> buscarData (String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Divida> div = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM DIVIDAS2 WHERE julianday(");
        sq2.append(" substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" else ");
        sq2.append(" '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?) ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

      //  sq2.append("BETWEEN julianday(?) AND julianday(?)");


        String[] parametros = {DATAVENCINICIAL, DATAVENCFINAL};

        // Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);
        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida2= new Divida();

                divida2.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida2.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida2.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida2.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida2.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));
                // divida2.ID_LDIV = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID_LDIV"));
                divida2.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida2.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));

                div.add(divida2);

            } while (resultadodiv.moveToNext());
        }
        return div;
    }

    public List<Divida> buscarTodosSpinnerPago(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM DIVIDAS2 WHERE PG = 1 AND NOMEDOCREDOR = ? AND julianday(");
        sq2.append(" substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?) ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        resultadodiv.close();
        return rec;
    }
    public List<Divida> buscarTodosSpinnerAbertas(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM DIVIDAS2 WHERE PG = 0 AND NOMEDOCREDOR = ? AND julianday(");
        sq2.append(" substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?) ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        resultadodiv.close();
        return rec;
    }

    public List<Divida> buscarDataPagas (String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM DIVIDAS2 WHERE PG = 1 AND julianday(");
        sq2.append(" substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?) ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        String[] parametros = {DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        resultadodiv.close();
        return rec;
    }
    public List<Divida> buscarDataAbertas (String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM DIVIDAS2 WHERE PG = 0 AND julianday(");
        sq2.append(" substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?) ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        String[] parametros = {DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Divida> buscarTodos(){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * ");
        sq2.append("from DIVIDAS2");
        sq2.append(" ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),null);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Divida> buscarTodosPagos( ){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * ");
        sq2.append("from DIVIDAS2 WHERE PG = 1");
        sq2.append(" ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),null);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Divida> buscarTodasAbertas( ){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * ");
        sq2.append("from DIVIDAS2 WHERE PG = 0");
        sq2.append(" ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),null);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Divida> buscarTodosPorCredor(String NOMEDOCREDOR){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from DIVIDAS2 WHERE NOMEDOCREDOR = ? ");
        sq2.append(" order by NOMEDOCREDOR");

        String[] parametros = {NOMEDOCREDOR};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Divida> buscarTodosPorCredorAberto(String NOMEDOCREDOR){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from DIVIDAS2 WHERE NOMEDOCREDOR = ? AND PG = 0");
        sq2.append(" order by NOMEDOCREDOR");

        String[] parametros = {NOMEDOCREDOR};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Divida> buscarTodosPorCredorPago(String NOMEDOCREDOR){

        List<Divida> rec = new ArrayList<Divida>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from DIVIDAS2 WHERE NOMEDOCREDOR = ? AND PG = 1");
        sq2.append(" order by NOMEDOCREDOR");

        String[] parametros = {NOMEDOCREDOR};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Divida divida= new Divida();

                divida.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                divida.NOMEDIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV"));
                divida.NOMEDOCREDOR = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDOCREDOR"));
                divida.VALORDIV = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV"))));
                divida.PG = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG"))>= 1;
                divida.DATAVENC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC"));
                divida.DATADIV = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV"));

                rec.add(divida);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Divida> geraPlanilaDivida(){

        divida = new Divida();

        List<Divida> dividas = new ArrayList<Divida>();

        StringBuilder sql = new StringBuilder();
        sql.append(" select * from DIVIDAS2 ORDER BY julianday((substr(DATAVENC, 7, 4) || '-' || substr(DATAVENC, 4, 2) || '-' || substr(DATAVENC, 1, 2))) DESC;");

        //String[] parametros = new String[1];
        //parametros[0] = String.valueOf(ID);

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0) {

            resultado.moveToFirst();
            do {
                Divida dividalista = new Divida();

                dividalista.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
                dividalista.NOMEDIV = resultado.getString(resultado.getColumnIndexOrThrow("NOMEDIV"));
                dividalista.VALORDIV = String.valueOf(Double.valueOf(resultado.getString(resultado.getColumnIndexOrThrow("VALORDIV"))));
                dividalista.DATAVENC = resultado.getString(resultado.getColumnIndexOrThrow("DATAVENC"));
                dividalista.DATADIV = resultado.getString(resultado.getColumnIndexOrThrow("DATADIV"));
                //dividalista.ID_LDIV = resultado.getInt(resultado.getColumnIndexOrThrow("ID_LDIV"));
                dividalista.PG = resultado.getInt(resultado.getColumnIndexOrThrow("PG"))>= 1;
                dividalista.NOMEDOCREDOR = resultado.getString(resultado.getColumnIndexOrThrow("NOMEDOCREDOR"));

                dividas.add(dividalista);

            } while (resultado.moveToNext());

            sqliteExcelListItensDividas(dividas);
        }

        return dividas;

    }
    public void sqliteExcelListItensDividas(List<Divida> dividas){

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File root = new File(Environment.getExternalStorageDirectory() + "/BKPDEDIVIDAS");
        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        File arquivoXLS = new File(pasta, "listadividas.csv");
        //File arquivoXLS = new File(pasta, "intens_da_lista_" + nomeListaCompras(Integer.parseInt(edtComp.getText().toString())) + ".csv");

        FileOutputStream streamDeSaida = null;

        try {
            streamDeSaida = new FileOutputStream(arquivoXLS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*

         try {
                streamDeSaida.write(colunas.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

          */

        for (Divida divida: dividas) {
            //String dadosDeItens = "\"" + item.getNome() + "\",\"" + item.getPreco() + "\",\"" + item.getQuant() + "\""+"\n";
            // String dadosDeItens = item.getNome() + "," + item.getPreco() + "," + item.getQuant() + "\n";
            String dadosDeItens =  divida.getNOMEDIV()+ "," +divida.getNOMEDOCREDOR()+ "," +divida.getVALORDIV()+ "," +(divida.getChecked() ? 1 : 0)+ "," +divida.getDATAVENC()+ "," +divida.getDATADIV()+"\n";

            try {
                streamDeSaida.write(dadosDeItens.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            streamDeSaida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        csvToXLSXDividas();
    }

    public void csvToXLSXDividas() {
        try {
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            //File root = new File(Environment.getExternalStorageDirectory() + "/BKPDEDIVIDAS");
            File pasta = new File(root.getAbsolutePath());
            pasta.mkdirs();

            File csvFileAddress = new File(pasta, "listadividas.csv");

            File xlsxFileAddressItemConvert = new File(pasta, "listadividas.xlsx");

            XSSFWorkbook workBookDivida = new XSSFWorkbook();
            XSSFSheet sheet = workBookDivida.createSheet("Dividas");
            String currentLine= null;
            int RowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }
            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddressItemConvert);
            workBookDivida.write(fileOutputStream);
            fileOutputStream.close();

            csvFileAddress.delete();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
