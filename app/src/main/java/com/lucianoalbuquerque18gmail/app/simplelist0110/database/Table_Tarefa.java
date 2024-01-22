package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

public class Table_Tarefa {

  public static String getCreateTarefa()
  {
    StringBuilder sqlItem = new StringBuilder();

    sqlItem.append("CREATE TABLE  l_tarefa2 (");
    sqlItem.append("  id_tarefa  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
    sqlItem.append("  TAREFA     TEXT    NOT NULL,");
    sqlItem.append("  PRIORIDADE TEXT NOT NULL,");
    sqlItem.append("  ID_T INTEGER, ");
    sqlItem.append("  ok INTEGER NOT NULL");
    sqlItem.append(" );");

    return sqlItem.toString();

  }
}
