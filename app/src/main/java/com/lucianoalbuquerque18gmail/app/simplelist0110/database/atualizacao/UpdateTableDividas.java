package com.lucianoalbuquerque18gmail.app.simplelist0110.database.atualizacao;

public class UpdateTableDividas {
    public static String getCreate_UpdateTableDividas()
    {
        StringBuilder sqlItem = new StringBuilder();

        sqlItem.append("UPDATE DIVIDAS2 SET NOMEDOCREDOR = 'Credor';");

        return sqlItem.toString();

    }
}
