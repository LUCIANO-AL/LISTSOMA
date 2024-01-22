package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Item;
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

import static com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaDeListReposi.conexaoL;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaDeListReposi.nomeListC;


public class ListaRepositorio {

    private static Context context;
    private SQLiteDatabase conexao;
    private static DataBaseLista dataBaseLista;
    private List<Item> dados;
    public Lista_De_List lista_de_list;
    public Item item;


    public ListaRepositorio(SQLiteDatabase conexao){this.conexao = conexao;}

    public ListaRepositorio() {
        this.context = context;
    }

    public void inserir(Item item){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Nome",item.Nome);
        contentValues.put("preco",item.preco);
        contentValues.put("quant",item.quant);
        contentValues.put("ID",item.ID);
        contentValues.put("ok",item.check ? 1 : 0);

        conexao.insertOrThrow("itemc2", null, contentValues );

    }

    public void excluirTdosItens(int ID){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        conexao.delete("itemc2","ID = ? ", parametros);
    }

     public void alterar(Item item){

        ContentValues contentValues = new ContentValues();

        contentValues.put("Nome",item.Nome);
        contentValues.put("preco",item.preco);
        contentValues.put("quant",item.quant);
        contentValues.put("ok",item.check ? 1 : 0);
        contentValues.put("ID",item.ID);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(item.id_item);

        conexao.update("itemc2", contentValues,"id_item = ?", parametros);

    }

    public void alterarCheck(int id_item){

        ContentValues contentValues = new ContentValues();

        Item item = new Item();

        //contentValues.put("Nome",item.Nome);
        //contentValues.put("preco",item.preco);
        //contentValues.put("quant",item.quant);
        contentValues.put("ok",item.check ? 1 : 0);
        //contentValues.put("ID",item.ID);

        //String[] parametros = new String[1];
        //parametros[0] = String.valueOf(item.id_item);

        conexao.update("itemc2", contentValues,"id_item = ?",new String[]{ id_item + ""});

    }

    public void alterarNoCheck(Item item){

        ContentValues contentValues = new ContentValues();

        contentValues.put("Nome",item.Nome);
        contentValues.put("preco",item.preco);
        contentValues.put("quant",item.quant);
        contentValues.put("ok",item.check = false);
        contentValues.put("ID",item.ID);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(item.id_item);

        conexao.update("itemc2", contentValues,"id_item = ?", parametros);

    }

    public void alterarcheck3(Item item){

        ContentValues contentValues = new ContentValues();

        contentValues.put("ok",item.check ? 1 : 0);


        String[] parametros = new String[1];
        parametros[0] = String.valueOf(item.id_item);

        conexao.update("itemc2", contentValues,"id_item = ?", parametros);

    }

    public List<Item> buscarTodos(int ID){

        List<Item> items = new ArrayList<Item>();

        StringBuilder sq2 = new StringBuilder();
        sq2.append(" select * ");
        sq2.append("from itemc2");
        sq2.append(" where ID = ? order by Nome");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        Cursor resultado = conexao.rawQuery(sq2.toString(),parametros);

        if(resultado.getCount() > 0) {

            resultado.moveToFirst();
            do {
                Item itemlista = new Item();

                itemlista.id_item = resultado.getInt(resultado.getColumnIndexOrThrow("id_item"));
                itemlista.Nome = resultado.getString(resultado.getColumnIndexOrThrow("Nome"));
                itemlista.preco = String.valueOf(Double.valueOf(resultado.getString(resultado.getColumnIndexOrThrow("preco"))));
                itemlista.quant = resultado.getString(resultado.getColumnIndexOrThrow("quant"));
                itemlista.check = resultado.getInt(resultado.getColumnIndexOrThrow("ok"))>= 1;
                itemlista.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));

                items.add(itemlista);

            } while (resultado.moveToNext());
        }
        return items;
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


    public List<Item> geraPlanila(int ID){

        item = new Item();

        List<Item> items = new ArrayList<Item>();

        StringBuilder sql = new StringBuilder();
        sql.append(" select * ");
        sql.append("from itemc2");
        sql.append(" where ID = ? order by Nome");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        Cursor resultado = conexao.rawQuery(sql.toString(),parametros);

        if(resultado.getCount() > 0) {

            resultado.moveToFirst();
            do {
                Item itemlista = new Item();

                itemlista.id_item = resultado.getInt(resultado.getColumnIndexOrThrow("id_item"));
                itemlista.Nome = resultado.getString(resultado.getColumnIndexOrThrow("Nome"));
                itemlista.preco = String.valueOf(Double.valueOf(resultado.getString(resultado.getColumnIndexOrThrow("preco"))));
                itemlista.quant = resultado.getString(resultado.getColumnIndexOrThrow("quant"));
                itemlista.check = resultado.getInt(resultado.getColumnIndexOrThrow("ok"))>= 1;
                itemlista.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));


                items.add(itemlista);


            } while (resultado.moveToNext());

            sqliteExcelListItens(items);
        }

        return items;

    }
    public void sqliteExcelListItens(List<Item> itens){
        // public void sqliteParaExcel(int ID){

       //String colunas = "\" NOME DO ITEM\" , \"PRECO\" , \"QUANTIDADE\"";

        // File root = context.getApplicationContext().getFilesDir();
        //File root = Environment.getExternalStorageDirectory();
        //File root = new File(Environment.getExternalStorageDirectory() + "/ListSomaCompartCompras");
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File root = new File(context.getExternalFilesDir("/"), "/Download");

        File pasta = new File(root.getAbsolutePath());
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File arquivoXLS = new File(pasta, "ItensListCompras.csv");
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
        for (Item item: itens) {
           //String dadosDeItens = "\"" + item.getNome() + "\",\"" + item.getPreco() + "\",\"" + item.getQuant() + "\""+"\n";
           // String dadosDeItens = item.getNome() + "," + item.getPreco() + "," + item.getQuant() + "\n";
            String dadosDeItens =  item.getNome() + "," + item.getPreco() + "," + item.getQuant() + "," + (item.getChecked() ? 1 : 0)+"\n";


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

            //File  csvFileAddress = new File(pasta, "intens_da_lista_" + nomeListaCompras(Integer.parseInt(edtComp.getText().toString())) + ".csv");
            File csvFileAddress = new File(pasta, "ItensListCompras.csv");
            //File  xlsxFileAddress = new File(pasta, "intens_da_lista_" + nomeListaCompras(Integer.parseInt(edtComp.getText().toString())) +".xlsx");
            //File xlsxFileAddress = new File(pasta, "ItensListCompras.xlsx");
            File xlsxFileAddressItemConvert = new File(pasta, "ItensListCompras.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("ListaDeItens");
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
            workBook.write(fileOutputStream);
            fileOutputStream.close();

            csvFileAddress.delete();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
