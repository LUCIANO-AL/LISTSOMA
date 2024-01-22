package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.arquivos;

import android.content.ContentValues;
import android.util.Log;

import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Divida;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Receber;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;

public class ExcelListDividasAReceber {

    public DataBaseLista dataBaseLista;
    public static final String Tablename = "RECEBER";


    public static final String NOMEDIV_REC = "NOMEDIV_REC";// 1 text(String)
    public static final String NOMECLIENTE = "NOMECLIENTE";// 2 text(String)
    public static final String VALORDIV_REC = "VALORDIV_REC";//3 text(String)
    public static final String DATAVENC_REC = "DATAVENC_REC";//4 text(String)
    public static final String DATADIV_REC = "DATADIV_REC";// 5 text(String)
    public static final String PG_REC = "PG_REC";// 6 integer

    public  static Receber receber;

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


            contentValues.put(NOMEDIV_REC, row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(NOMECLIENTE, row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(VALORDIV_REC, row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(PG_REC, row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(DATAVENC_REC, row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(DATADIV_REC, row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());


            try {
                if (dataBaseLista.insertDividasAReceber("RECEBER", contentValues) < 0) {
                    return;
                }
            } catch (Exception ex) {
                Log.d("Exception in importing", ex.getMessage().toString());
            }
        }
    }

}
