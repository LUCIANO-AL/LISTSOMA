package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.arquivos;

import android.content.ContentValues;
import android.util.Log;

import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Divida;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Item;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;

public class ExcelListDividas {
    public DataBaseLista dataBaseLista;
    public static final String Tablename = "DIVIDAS2";


    public static final String NOMEDIV = "NOMEDIV";// 1 text(String)
    public static final String NOMEDOCREDOR = "NOMEDOCREDOR";// 2 text(String)
    public static final String VALORDIV = "VALORDIV";// 3 text(String)
    public static final String DATAVENC = "DATAVENC";// 4 text(String)
    public static final String DATADIV = "DATADIV";// 5 text(String)
    public static final String ID = "ID";// 6 integer
    public static final String PG = "PG";// 7 integer

    public  static Divida divida;

   public static void insertExcelToSqliteDividas(DataBaseLista dataBaseLista, Sheet sheet) {

        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {
            Row row = rit.next();

            ContentValues contentValues = new ContentValues();
            row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);
            row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);
            row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);
            row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);
            row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);
            row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);


            contentValues.put(NOMEDIV, row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(NOMEDOCREDOR, row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(VALORDIV, row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(PG, row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(DATAVENC, row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(DATADIV, row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());


            try {
                if (dataBaseLista.insertDividas("DIVIDAS2", contentValues) < 0) {
                    return;
                }
            } catch (Exception ex) {
                Log.d("Exception in importing", ex.getMessage().toString());
            }
        }
    }

}
