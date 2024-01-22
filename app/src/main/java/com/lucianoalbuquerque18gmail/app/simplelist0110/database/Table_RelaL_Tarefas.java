package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

public class Table_RelaL_Tarefas {
  public static String getCreateRelaL_Tarefa()
  {
    StringBuilder sqlItem = new StringBuilder();

    sqlItem.append("CREATE TABLE  relac_list_tar2(");
    sqlItem.append("  ID_T        INTEGER   NOT NULL PRIMARY KEY AUTOINCREMENT, ");
    sqlItem.append(" LISTATAREFA VARCHAR (200) NOT NULL,");
    sqlItem.append(" DATALISTA TEXT");
    sqlItem.append(" );");

    return sqlItem.toString();

  }
}
