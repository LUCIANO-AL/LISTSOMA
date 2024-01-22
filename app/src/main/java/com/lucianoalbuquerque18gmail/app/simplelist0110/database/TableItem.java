package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

public class TableItem {

    public static String getCreateItem()
    {
        StringBuilder sqlItem = new StringBuilder();

        sqlItem.append("CREATE TABLE  itemc2(");
        sqlItem.append(" id_item INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlItem.append("  Nome    VARCHAR (100) NOT NULL,");
        sqlItem.append("  preco   INTEGER NOT NULL,");
        sqlItem.append("  quant   INTEGER (50),");
        sqlItem.append("  ID INTEGER, ");
        sqlItem.append("  ok INTEGER NOT NULL");
        sqlItem.append(" );");

        return sqlItem.toString();

    }
}
