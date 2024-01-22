package com.lucianoalbuquerque18gmail.app.simplelist0110.database.atualizacao;

public class AlterTableDividas {
    public static String getCreateAlterTableDividas()
    {
        StringBuilder sqlItem = new StringBuilder();

        sqlItem.append("ALTER TABLE DIVIDAS2 ADD NOMEDOCREDOR VARCHAR(200);");

        return sqlItem.toString();

    }
}
