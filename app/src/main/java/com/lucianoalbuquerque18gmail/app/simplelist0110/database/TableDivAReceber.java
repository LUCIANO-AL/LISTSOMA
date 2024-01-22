package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

public class TableDivAReceber {

    public static String getCreate_TableDivAReceber()
    {
        StringBuilder sqlItem = new StringBuilder();

        sqlItem.append("CREATE TABLE RECEBER(");
        sqlItem.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sqlItem.append("NOMEDIV_REC  VARCHAR (200) NOT NULL,");
        sqlItem.append("VALORDIV_REC INTEGER       NOT NULL,");
        sqlItem.append("PG_REC       INTEGER       NOT NULL,");
        sqlItem.append("DATAVENC_REC TEXT,");
        sqlItem.append("DATADIV_REC  TEXT,");
        sqlItem.append("NOMECLIENTE  VARCHAR (200) NOT NULL);");

        return sqlItem.toString();

    }
}
