package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

public class Table_Rela_List_Div {
    public static String getCreate_Rela_List_Div()
    {
        StringBuilder sqlLista = new StringBuilder();

        sqlLista.append("CREATE TABLE  T_RELA_LIST_DIVIDAS2(");
        sqlLista.append("ID INTEGER NOT NULL   PRIMARY KEY AUTOINCREMENT, ");
        sqlLista.append("NOMELISTA VARCHAR (200) NOT NULL, ");
        sqlLista.append("DATALISTA TEXT");
        sqlLista.append(" );");

        return sqlLista.toString();


    }
}
