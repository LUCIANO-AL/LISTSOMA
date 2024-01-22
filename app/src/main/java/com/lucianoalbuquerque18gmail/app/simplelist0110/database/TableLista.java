package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

public class TableLista {

    public static String getCreateLista()
    {
        StringBuilder sqlLista = new StringBuilder();

        sqlLista.append("CREATE TABLE listac2(");
        sqlLista.append("ID INTEGER NOT NULL   PRIMARY KEY AUTOINCREMENT,");
        sqlLista.append("NOMELISTA VARCHAR (200) NOT NULL,");
        sqlLista.append("DATALISTA TEXT");
        sqlLista.append(" );");

         return sqlLista.toString();

    }
}
