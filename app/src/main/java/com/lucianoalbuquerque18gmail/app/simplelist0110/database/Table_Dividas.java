package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

public class Table_Dividas {

    public static String getCreate_Table_Dividas()
    {
        StringBuilder sqlItem = new StringBuilder();

        sqlItem.append("CREATE TABLE  DIVIDAS2(");
        sqlItem.append("ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sqlItem.append("NOMEDIV VARCHAR (200) NOT NULL,");
        sqlItem.append("VALORDIV INTEGER NOT NULL,");
        sqlItem.append("PG INTEGER NOT NULL,");
        sqlItem.append("DATAVENC TEXT,");
        sqlItem.append("DATADIV TEXT,");
        sqlItem.append("ID_LDIV INTEGER,");
        sqlItem.append("NOMEDOCREDOR VARCHAR (200));");
        return sqlItem.toString();

    }
}
