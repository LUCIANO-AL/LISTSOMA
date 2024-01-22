package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Divida;
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
import java.util.List;

public class Crud_DividasAReceber {

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
    private Receber receberxlsx;

    public Crud_DividasAReceber(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public Crud_DividasAReceber(Context context) {
        this.context = context;
    }

    public void inserir(Receber receber){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOMEDIV_REC",receber.NOMEDIV_REC);
        contentValues.put("NOMECLIENTE",receber.NOMECLIENTE);
        contentValues.put("VALORDIV_REC",receber.VALORDIV_REC);
        contentValues.put("PG_REC",receber.PG_REC ? 1 : 0);
        contentValues.put("DATAVENC_REC",receber.DATAVENC_REC);
        contentValues.put("DATADIV_REC",receber.DATADIV_REC);

        conexao.insertOrThrow("RECEBER", null, contentValues );

    }

    public void excluirTdosItens(){

        String[] parametros = new String[1];
        //parametros[0] = String.valueOf(ID_LDIV);

        conexao.delete("RECEBER",null, null);
    }

    public void alterar(Receber receber){

        ContentValues contentValues = new ContentValues();

        contentValues.put("NOMEDIV_REC",receber.NOMEDIV_REC);
        contentValues.put("NOMECLIENTE",receber.NOMECLIENTE);
        contentValues.put("VALORDIV_REC",receber.VALORDIV_REC);
        contentValues.put("PG_REC",receber.PG_REC ? 1 : 0);
        contentValues.put("DATAVENC_REC",receber.DATAVENC_REC);
        contentValues.put("DATADIV_REC",receber.DATADIV_REC);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(receber.ID);

        conexao.update("RECEBER", contentValues,"ID = ?", parametros);

    }

    public List<Receber> buscarTodosSpinner(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM RECEBER WHERE NOMECLIENTE = ? AND julianday(");
        sq2.append(" substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?)");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));

                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Receber> buscarData (String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM RECEBER WHERE julianday(");
        sq2.append(" substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" else ");
        sq2.append(" '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?)");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        String[] parametros = {DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Receber> buscarTodosSpinnerPago(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM RECEBER WHERE PG_REC = 1 AND NOMECLIENTE = ? AND julianday(");
        sq2.append(" substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?)");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));

                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Receber> buscarTodosSpinnerAbertas(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM RECEBER WHERE PG_REC = 0 AND NOMECLIENTE = ? AND julianday(");
        sq2.append(" substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?)");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));

                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Receber> buscarDataPagas (String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM RECEBER WHERE PG_REC = 1 AND  julianday(");
        sq2.append(" substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" else ");
        sq2.append(" '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?)");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        String[] parametros = {DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Receber> buscarDataAbertas (String DATAVENCINICIAL, String DATAVENCFINAL){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append("SELECT * FROM RECEBER WHERE PG_REC = 0 AND  julianday(");
        sq2.append(" substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)");
        sq2.append("||'-'||");
        sq2.append(" case when length(");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)");
        sq2.append(")=2");
        sq2.append(" then");
        sq2.append(" substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" else ");
        sq2.append(" '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)");
        sq2.append(" end");
        sq2.append("||'-'||");
        sq2.append(" case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2");
        sq2.append(" then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" else");
        sq2.append(" '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )");
        sq2.append(" end");
        sq2.append(" ) BETWEEN julianday(?) AND julianday(?)");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        String[] parametros = {DATAVENCINICIAL, DATAVENCFINAL};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Receber> buscarTodos( ){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from RECEBER");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),null);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Receber> buscarTodosPagos( ){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from RECEBER WHERE PG_REC = 1");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),null);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Receber> buscarTodasAbertas( ){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from RECEBER WHERE PG_REC = 0");
        sq2.append(" ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),null);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Receber> buscarTodosPorCliente(String NOMECLIENTE){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from RECEBER WHERE NOMECLIENTE = ? ");
        sq2.append(" order by NOMECLIENTE");

        String[] parametros = {NOMECLIENTE};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Receber> buscarTodosPorClienteAberto(String NOMECLIENTE){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from RECEBER WHERE NOMECLIENTE = ? AND PG_REC = 0");
        sq2.append(" order by NOMECLIENTE");

        String[] parametros = {NOMECLIENTE};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }
    public List<Receber> buscarTodosPorClientePago(String NOMECLIENTE){

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * from RECEBER WHERE NOMECLIENTE = ? AND PG_REC = 1");
        sq2.append(" order by NOMECLIENTE");

        String[] parametros = {NOMECLIENTE};

        Cursor resultadodiv = conexao.rawQuery(sq2.toString(),parametros);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber= new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());
        }
        return rec;
    }

    public List<Receber> geraPlanilaDividaReceber(){

        receberxlsx = new Receber();

        List<Receber> rec = new ArrayList<Receber>();

        StringBuilder sql = new StringBuilder();
        sql.append(" select * from RECEBER ORDER BY julianday((substr(DATAVENC_REC, 7, 4) || '-' || substr(DATAVENC_REC, 4, 2) || '-' || substr(DATAVENC_REC, 1, 2))) DESC;");


        //String[] parametros = new String[1];
        //parametros[0] = String.valueOf(ID);

        Cursor resultadodiv = conexao.rawQuery(sql.toString(),null);

        if(resultadodiv.getCount() > 0) {

            resultadodiv.moveToFirst();
            do {
                Receber receber = new Receber();

                receber.ID = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("ID"));
                receber.NOMEDIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMEDIV_REC"));
                receber.NOMECLIENTE = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("NOMECLIENTE"));
                receber.VALORDIV_REC = String.valueOf(Double.valueOf(resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("VALORDIV_REC"))));
                receber.PG_REC = resultadodiv.getInt(resultadodiv.getColumnIndexOrThrow("PG_REC"))>= 1;
                receber.DATAVENC_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATAVENC_REC"));
                receber.DATADIV_REC = resultadodiv.getString(resultadodiv.getColumnIndexOrThrow("DATADIV_REC"));


                rec.add(receber);

            } while (resultadodiv.moveToNext());

            sqliteExcelListItensDividasReceber(rec);
        }

        return rec;

    }
    public void sqliteExcelListItensDividasReceber(List<Receber> recebers){

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File root = new File(Environment.getExternalStorageDirectory() + "/BKPDEDIVIDAS");
        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        File arquivoXLS = new File(pasta, "listadividasreceber.csv");
        //File arquivoXLS = new File(pasta, "intens_da_lista_" + nomeListaCompras(Integer.parseInt(edtComp.getText().toString())) + ".csv");

        FileOutputStream streamDeSaida = null;

        try {
            streamDeSaida = new FileOutputStream(arquivoXLS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Receber receber: recebers) {

            String dadosDeItens =  receber.getNOMEDIV_REC()+ "," +receber.getNOMECLIENTE()+ ","+receber.getVALORDIV_REC()+ "," +(receber.getChecked() ? 1 : 0)+ "," +receber.getDATAVENC_REC()+ "," +receber.getDATADIV_REC()+"\n";

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

            File csvFileAddress = new File(pasta, "listadividasreceber.csv");

            File xlsxFileAddressItemConvert = new File(pasta, "listadividasreceber.xlsx");

            XSSFWorkbook workBookDivida = new XSSFWorkbook();
            XSSFSheet sheet = workBookDivida.createSheet("DividasAReceber");
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
