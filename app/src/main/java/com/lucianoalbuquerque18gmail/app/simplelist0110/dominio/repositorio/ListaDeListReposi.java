package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.lucianoalbuquerque18gmail.app.simplelist0110.R;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Lista_De_List;

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


public class ListaDeListReposi {

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

    public static String nomeListC;
    public static  SQLiteDatabase conexaoL;
    public SQLiteDatabase conexaolist;
    public static Context context;

    public ListaDeListReposi(SQLiteDatabase conexaolist) {
        this.conexaolist = conexaolist;
    }


    public void inserirLista(Lista_De_List lista_De_List){

        ContentValues contentValues = new ContentValues();

        contentValues.put("NOMELISTA",lista_De_List.NOMELISTA);
        contentValues.put("DATALISTA",lista_De_List.DATALISTA);

        conexaolist.insertOrThrow("listac2", null, contentValues );
    }

    public void inserirListaUm(Lista_De_List lista_De_List){

        ContentValues contentValues = new ContentValues();

        contentValues.put("NOMELISTA",lista_De_List.NOMELISTA);
        contentValues.put("DATALISTA",lista_De_List.DATALISTA);

        conexaolist.insertOrThrow("listac2", null, contentValues );
    }

    public void inserirListaC(Lista_De_List lista_De_List){

        ContentValues contentValues = new ContentValues();

        contentValues.put("NOMELISTA",lista_De_List.NOMELISTA+ "_copia");
        contentValues.put("DATALISTA",lista_De_List.DATALISTA);

        conexaolist.insertOrThrow("listac2", null, contentValues);
    }

    public void alterarLista(Lista_De_List lista_de_list){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOMELISTA",lista_de_list.NOMELISTA);
        contentValues.put("DATALISTA",lista_de_list.DATALISTA);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(lista_de_list.ID);

        conexaolist.update("listac2", contentValues,"ID = ? ", parametros);

    }

    public List<Lista_De_List> buscarTodasListas(){

        List<Lista_De_List> lista_de_lists = new ArrayList<Lista_De_List>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, NOMELISTA, DATALISTA");
        sql.append(" FROM listac2 order by ID desc");


       Cursor resultado = conexaolist.rawQuery(sql.toString(),null);


        if(resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                Lista_De_List list = new Lista_De_List();

                list.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
                list.NOMELISTA = resultado.getString(resultado.getColumnIndexOrThrow("NOMELISTA"));
                list.DATALISTA = resultado.getString(resultado.getColumnIndexOrThrow("DATALISTA"));


                lista_de_lists.add(list);

            } while (resultado.moveToNext());


        }

        return lista_de_lists;
    }

    public List<Lista_De_List> geraPlanilhaNomeLista(int ID){

        List<Lista_De_List> lista_de_lists = new ArrayList<Lista_De_List>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM LISTAC2 WHERE ID = ?");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        Cursor resultado = conexaolist.rawQuery(sql.toString(), parametros);

        if(resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                Lista_De_List list = new Lista_De_List();

                list.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
                list.NOMELISTA = resultado.getString(resultado.getColumnIndexOrThrow("NOMELISTA"));
                list.DATALISTA = resultado.getString(resultado.getColumnIndexOrThrow("DATALISTA"));


                lista_de_lists.add(list);

           } while (resultado.moveToNext());

            sqliteExcelRelaLista(lista_de_lists);
           }

        return lista_de_lists;
    }

    public static String nomeListaCompras(int ID) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT NOMELISTA FROM listac2 WHERE ID = ?");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        Cursor resultadoNomeListCompras = conexaoL.rawQuery(sql.toString(), parametros);

        if (resultadoNomeListCompras.moveToNext()) {

            nomeListC = resultadoNomeListCompras.getString(0);
        }

        return nomeListC;

    }

    public static void sqliteExcelRelaLista(List<Lista_De_List> listadelistas){

       // String colunas = "\" NOME DA LISTA \"";
      //  File root = new File(Environment.getExternalStorageDirectory() + "/ListSomaCompartCompras");
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

       // File root = new File(context.getExternalFilesDir("/"), "/Download");

        File pasta = new File(root.getAbsolutePath());
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File  arquivoXLSNomeLista = new File(pasta, "NomelistCompras.csv");
        //File  arquivoXLSNomeLista = new File(pasta, nomeListaCompras(Integer.parseInt(edtComp.getText().toString())) + ".csv");

        FileOutputStream streamDeSaida = null;

        try {
            streamDeSaida = new FileOutputStream(arquivoXLSNomeLista);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


       /* try {
            streamDeSaida.write(colunas.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        */


        for (Lista_De_List lista_de_list: listadelistas) {

              //String nomeDalista = "\"" + lista_de_list.getNOMELISTA() + "\",\"" + lista_de_list.getDATALISTA() + "\"";
            String nomeDalista = lista_de_list.getNOMELISTA() + "," + lista_de_list.getDATALISTA();

            try {
                streamDeSaida.write(nomeDalista.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            streamDeSaida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

       csvToXLSX();

    }

    public static void csvToXLSX() {
        try {
            //File root = new File(Environment.getExternalStorageDirectory() + "/ListSomaCompartCompras");
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            //File root = new File(context.getExternalFilesDir("/"), "/Download");

            File pasta = new File(root.getAbsolutePath());
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            //File  csvFileAddress = new File(pasta, nomeListaCompras(Integer.parseInt(edtComp.getText().toString())) + ".csv");
            File  csvFileAddress = new File(pasta, "NomelistCompras.csv");
            //File  xlsxFileAddress = new File(pasta, nomeListaCompras(Integer.parseInt(edtComp.getText().toString())) + ".xlsx");
            //File  xlsxFileAddress = new File(pasta, "NomelistCompras.xlsx");
            File xlsxFileAddressNomeListConvert = new File(pasta, "NomelistCompras.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("NomedaLista");
            String currentLine=null;
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

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddressNomeListConvert);
            workBook.write(fileOutputStream);
            fileOutputStream.close();

            csvFileAddress.delete();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
