package com.lucianoalbuquerque18gmail.app.simplelist0110.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DataBaseLista extends SQLiteOpenHelper {

    private double totalSoma;
    private  String totalSoma2;
    private String total;

    private String totalSoma_check;
    private  String totalSoma2_check;
    private String total_check;

    private String totalAberto;
    private String totalAberto2;
    private String contAberto;

    private String contIdum, contIdum_Divida, contIdum_Tarefa;

    private String totalPG;
    private  String totalPG2;
    private String countPG;

    private String result;
    public static String resultListItens, resultNomeListaCompras, resultNomeListaTarefa, resultNomeListaDivida;
    public String nomeListC;
    public String nomeListCComparitlhar;

    private String resultare;

    private String resuldiv;

    public static List<Item> dadosdatabase;

    private File pasta;
    private File arquivo;
    private Context context;

    public static String tableName = "listac2";
    public static String colNomeLista = "NOMELISTA";
    public static String colData = "DATALISTA";

    private SQLiteDatabase db;

    private double listCompraIdUm;


   public DataBaseLista(Context context) {
       super(context, "SOMAL", null, 2);
    }

  /*  public DataBaseLista(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
         super(context, "SOMAL", factory, 1);
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableLista.getCreateLista());
        db.execSQL(TableItem.getCreateItem());
        db.execSQL(Table_RelaL_Tarefas.getCreateRelaL_Tarefa());
        db.execSQL(Table_Tarefa.getCreateTarefa());
        db.execSQL(Table_Rela_List_Div.getCreate_Rela_List_Div());
        db.execSQL(Table_Dividas.getCreate_Table_Dividas());
        db.execSQL(TableDivAReceber.getCreate_TableDivAReceber());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //db.execSQL(TableDivAReceber.getCreate_TableDivAReceber());
        //db.execSQL(AlterTableDividas.getCreateAlterTableDividas());
        //db.execSQL(UpdateTableDividas.getCreate_UpdateTableDividas());

        /*db.execSQL("ALTER TABLE l_tarefa2 ADD RESPONSAVEL   VARCHAR (100);");
        db.execSQL("ALTER TABLE l_tarefa2 ADD DIADASEMANA   VARCHAR (50);");
        db.execSQL("ALTER TABLE l_tarefa2 ADD HORAINICIO TEXT;");*/

    }

    //CRUDS REFERENTES CLASSE ADD_RELACAOLIST *********************************************************************************************************

    public int insert(String table, ContentValues values) {
        try {
            db = this.getWritableDatabase();
            int y = (int) db.insert(table, null, values);
            db.close();
            // Log.e("Data Inserted", "Data Inserted");
            //Log.e("y", y + "");
            return y;
        } catch (Exception ex) {
            Log.e("Error Insert", ex.getMessage().toString());
            return 0;
        }
    }
    public int insertItemLCompras(String table, ContentValues values) {
        try {
            db = this.getWritableDatabase();
            int y = (int) db.insert(table, null, values);

            db.close();
            // Log.e("Data Inserted", "Data Inserted");
            //Log.e("y", y + "");
            return y;
        } catch (Exception ex) {
            Log.e("Error Insert", ex.getMessage().toString());
            return 0;
        }
    }

    public  String select_ID_ListaItens(){

        String selectQuery = "select ID from listac2 order by ID desc limit 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {

            resultListItens = cursor.getString(0);
        }

        cursor.close();
        return resultListItens;

    }

    public  String select_NomeListaDeCompras(int ID){

        String selectQuery = "select NOMELISTA from listac2 where ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, parametros);

        if (cursor.moveToNext()) {

            resultNomeListaCompras = cursor.getString(0);
        }

        cursor.close();
        return resultNomeListaCompras;
    }

    public String idIgualUm() {

        String SelectQuery = "select ID from listac2 order by ID desc limit 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            contIdum = cursor.getString(0);
        } else {
            contIdum = "0";
        }

        cursor.close();
        return contIdum;
    }

    public String insertMultiplo(int ID){

        String InsertQuery = "insert into itemc2 (nome, preco, quant, ok, ID)" +
                " select nome, preco, quant, ok, (select ID from listac2 order by ID desc limit 1) " +
                "from  itemc2 where ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(InsertQuery, parametros);


        if (cursor.moveToNext()) {

            result = cursor.getString(0);
        }

        cursor.close();
        return result;

    }

    public void excluirtudaslistas(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "listac2");

    }

    public boolean excluirL(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("listac2", "ID=?", new String[]{ id + "" }) > 0;
    }

    //CRUDS REFERENTES CLASSE LIST *********************************************************************************************************

    public String totalReais_NoChek(double ID) {

        String SelectQuery = "SELECT SUM((preco * quant)) AS total\n" +
                "  FROM itemc2 WHERE ID =? and ok = 0";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalSoma = Double.parseDouble(cursor.getString(0));
        }

        cursor.close();

        return String.valueOf(totalSoma);

    }
    public String somarCategoria2(int ID) {

        String SelectQuery = "SELECT SUM((preco * quant)) AS total\n" +
                "  FROM itemc2 WHERE ID =? and ok = 0";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalSoma2 = cursor.getString(0);
        }

        cursor.close();
        return totalSoma2;

    }
    public String totalItem_NoCheck(int ID) {

        String SelectQuery = "SELECT COUNT(*) AS total\n" +
                "  FROM itemc2 WHERE ID =? and ok = 0";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            total = cursor.getString(0);
        }

        cursor.close();
        return total;
    }

    public String totalReais_check(double ID) {

        String SelectQuery = "SELECT SUM((preco * quant)) AS total\n" +
                "  FROM itemc2 WHERE ID =? and ok = 1";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalSoma_check = cursor.getString(0);
        }

        cursor.close();
        return totalSoma_check;

    }
    public String somarCategoria2_check(int ID) {

        String SelectQuery = "SELECT SUM((preco * quant)) AS total\n" +
                "  FROM itemc2 WHERE ID =? and ok = 1";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalSoma2_check = cursor.getString(0);
        }

        cursor.close();
        return totalSoma2_check;

    }
    public String totalItem_check(int ID) {

        String SelectQuery = "SELECT COUNT(*) AS total\n" +
                "  FROM itemc2 WHERE ID =? and ok = 1";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            total_check = cursor.getString(0);
        }

        cursor.close();
        return total_check;
    }

    public String totalReais(double ID) {

        String SelectQuery = "SELECT SUM((preco * quant)) AS total\n" +
                "  FROM itemc2 WHERE ID =?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalSoma_check = cursor.getString(0);
        }

        cursor.close();
        return totalSoma_check;

    }
    public String totalItens(int ID) {

        String SelectQuery = "SELECT COUNT(*) AS total\n" +
                "  FROM itemc2 WHERE ID =?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            total_check = cursor.getString(0);
        }

        cursor.close();
        return total_check;
    }

    public void excluirtudo(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "itemc2");

    }
    public boolean excluirIt(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("itemc2", "id_item=?", new String[]{ id + "" }) > 0;

    }

    public void noCheck(int id_item) {

        String UpdateQuery = "UPDATE itemc2 SET ok = 0 WHERE  id_item = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id_item);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();

        cursor.close();

    }
    public void check(int id_item) {

        String UpdateQuery = "UPDATE itemc2 SET ok = 1 WHERE  id_item = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id_item);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();

        cursor.close();
    }

    public String checkMultplos(int ID){

        String InsertQuery = "UPDATE itemc2 SET ok = 1 where ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(InsertQuery, parametros);


        if (cursor.moveToNext()) {

            result = cursor.getString(0);
        }

        cursor.close();
        return result;

    }
    public String nocheckMultplos(int ID){

        String InsertQuery = "UPDATE itemc2 SET ok = 0 where ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(InsertQuery, parametros);

        if (cursor.moveToNext()) {

            result = cursor.getString(0);
        }

        cursor.close();
        return result;

    }

    public String idDaLista(int ID) {

        String SelectQuery = "select ID from itemc2 where id_item = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contIdum = cursor.getString(0);

        } else {
            contIdum = "0";
        }

        cursor.close();

        return contIdum;
    }

    @SuppressLint("Range")
    public ArrayList<String> buscarNomeListComprasSippner(){

        SQLiteDatabase db = getReadableDatabase();

        String sql_busca_dados_spinner = "SELECT NOMELISTA FROM  listac2  ORDER BY ID DESC";

        ArrayList<String> dados_spinner = new ArrayList<>();

        //String[] parametros = new String[1];
        //parametros[0] = IDNomeList;

        Cursor cursor = db.rawQuery(sql_busca_dados_spinner, null);

        while (cursor.moveToNext()){

            dados_spinner.add(cursor.getString(cursor.getColumnIndex("NOMELISTA")));
            //dados_spinner.add(cursor.getString(cursor.getColumnIndex("ID")));

        }
        cursor.close();
        return (dados_spinner);
    }


    public String buscarNomeListCompras(String ID) {

        String SelectQuery = "select NOMELISTA  from listac2 WHERE ID = ? ";

        String[] parametros = new String[1];
        parametros[0] = ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contIdum = cursor.getString(0);
        }

        cursor.close();
        return contIdum;
    }


    public String buscarIDListCompras(String nomelista) {

        String SelectQuery = "select ID from listac2 WHERE NOMELISTA = ? ";

        String[] parametros = new String[1];
        parametros[0] = nomelista;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contIdum = cursor.getString(0);
        }

        cursor.close();
        return contIdum;
    }



    //CRUDS REFERENTES CLASSE ADD_RELACAOLISTARE *********************************************************************************************************

    public  String select_NomeListaDeTarefas(int ID_T){

        String selectQuery = "select LISTATAREFA from relac_list_tar2 where ID_T = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID_T);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, parametros);

        if (cursor.moveToNext()) {

            resultNomeListaTarefa = cursor.getString(0);
        }

        cursor.close();
        return resultNomeListaTarefa;
    }

    @SuppressLint("Range")
    public ArrayList<String> buscarNomeListTareSippner(){

        SQLiteDatabase db = getReadableDatabase();

        String sql_busca_dados_spinner = "SELECT LISTATAREFA FROM  relac_list_tar2  ORDER BY ID_T DESC";

        ArrayList<String> dados_spinner = new ArrayList<>();

        //String[] parametros = new String[1];
        //parametros[0] = IDNomeList;

        Cursor cursor = db.rawQuery(sql_busca_dados_spinner, null);

        while (cursor.moveToNext()){

            dados_spinner.add(cursor.getString(cursor.getColumnIndex("LISTATAREFA")));
            //dados_spinner.add(cursor.getString(cursor.getColumnIndex("ID")));

        }
        cursor.close();
        return (dados_spinner);
    }

    public String buscarIDListTare(String nomelista) {

        String SelectQuery = "select ID_T from relac_list_tar2 WHERE LISTATAREFA = ? ";

        String[] parametros = new String[1];
        parametros[0] = nomelista;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contIdum = cursor.getString(0);
        }

        cursor.close();
        return contIdum;
    }

    public String idIgualUmRelTare() {

        String SelectQuery = "select ID_T from relac_list_tar2 order by ID_T desc limit 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            contIdum = cursor.getString(0);
        } else {
            contIdum = "0";
        }

        cursor.close();
        return contIdum;
    }

    public String ultimoId_Tarefas() {

        String SelectQuery = "select ID_T from relac_list_tar2 order by ID_T desc limit 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            contIdum_Tarefa = cursor.getString(0);
        }

        cursor.close();
        return contIdum_Tarefa;
    }

    public String insertMultiploT(int ID){

        String InsertQuery = "insert into l_tarefa2 (TAREFA, PRIORIDADE, ID_T, ok)" +
                " select TAREFA, PRIORIDADE, (select ID_T from relac_list_tar2 order by ID_T desc limit 1), ok  " +
                "from  l_tarefa2 where ID_T = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(InsertQuery, parametros);


        if (cursor.moveToNext()) {

            resultare = cursor.getString(0);
        }

        cursor.close();
        return resultare;

    }

    public boolean excluirLTare(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("relac_list_tar2", "ID_T=?", new String[]{ id + "" }) > 0;
    }

    //CRUDS REFERENTES CLASSE LIST_T *********************************************************************************************************

    public boolean excluirItTare(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("l_tarefa2", "id_tarefa=?", new String[]{ id + "" }) > 0;
    }

    public void noCheckT(int id_tarefa) {

        String UpdateQuery = "UPDATE l_tarefa2 SET ok = 0 WHERE  id_tarefa = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id_tarefa);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();

        cursor.close();

    }
    public void checkT(int id_tarefa) {

        String UpdateQuery = "UPDATE l_tarefa2 SET ok = 1 WHERE  id_tarefa = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id_tarefa);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();

        cursor.close();
    }

    public String buscarNomeListTarefa(String ID) {

        String SelectQuery = "select LISTATAREFA  from relac_list_tar2 WHERE ID_T = ? ";

        String[] parametros = new String[1];
        parametros[0] = ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contIdum = cursor.getString(0);
        }

        cursor.close();
        return contIdum;
    }


    //CRUDS DA CLASSE LIST_DIVIDAS *********************************************************************************************************

    @SuppressLint("Range")
    public ArrayList<String> buscarNomeCredorSppinerPorData(String DATAVENCINICIAL, String DATAVENCIFINAL){

        SQLiteDatabase db = getReadableDatabase();

        String sql_busca_dados_spinner = "SELECT DISTINCT NOMEDOCREDOR FROM DIVIDAS2 " +
                "WHERE julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";


        ArrayList<String> dados_spinner = new ArrayList<>();

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        Cursor cursor = db.rawQuery(sql_busca_dados_spinner, parametros);

        while (cursor.moveToNext()){

            dados_spinner.add(cursor.getString(cursor.getColumnIndex("NOMEDOCREDOR")));

        }


        return (dados_spinner);
    }

    @SuppressLint("Range")
    public ArrayList<String> buscarNomeCredorSppinerTodos(){

        SQLiteDatabase db = getReadableDatabase();


        String sql_busca_dados_spinner = "SELECT DISTINCT NOMEDOCREDOR FROM DIVIDAS2 ORDER BY ID";


        ArrayList<String> dados_spinner = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql_busca_dados_spinner, null);

        while (cursor.moveToNext()){

            dados_spinner.add(cursor.getString(cursor.getColumnIndex("NOMEDOCREDOR")));

        }

        
        return (dados_spinner);
    }

    // Soma dos valores  e totais das dívidas em aberto por data de vencimento
    public String somarAberto(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT SUM(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 0 AND" +
        //      " DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 AND " +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalAberto = cursor.getString(0);
        }

        
        return totalAberto;

    }
    public String totalAberto(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        // String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //       " FROM DIVIDAS2 WHERE PG = 0 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 AND " +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        //select coUnt(nomediv) from dividas where ID_LDIV = 1 AND PG = 0

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }

       
        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por data de vencimento
    public String somarPG(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        // String SelectQuery = "SELECT SUM(VALORDIV) AS total\n" +
        //       " FROM DIVIDAS2 WHERE PG = 1 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 AND " +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }

        
        return totalPG;

    }
    public String totalPG(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 AND " +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }

    //TOTAIS DAS QUANTIDADES E VALORES EM REAIS POR DATA
    public String totalReaisPorData(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE " +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }


        return totalPG;

    }
    public String totalContPorData(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE " +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }

    // Soma dos valores  e totais das dívidas abertas por nome da divia e  data de vencimento
    public String somarAberto2_Filtro(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //String SelectQuery = "SELECT SUM(VALORDIV) AS total\n" +
        //      " FROM DIVIDAS2 WHERE PG = 0 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 AND NOMEDOCREDOR = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";


        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalAberto2 = cursor.getString(0);
        }

        return totalAberto2;

    }
    public String totalAberto_Filtro(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        // String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //       " FROM DIVIDAS2 WHERE PG = 0 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 AND NOMEDOCREDOR = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }

        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por nome da divia e  data de vencimento
    public String somarPG2_Filtro(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        // String SelectQuery = "SELECT SUM(VALORDIV) AS total\n" +
        //       " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 AND NOMEDOCREDOR = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }

        return totalPG2;

    }
    public String totalPG_Filtro(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 AND NOMEDOCREDOR = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }

    // TOTAIS DAS QUANTIDADES E VALORES EM REAIS POR DATA E NOME DO CLIENTE
    public String totalReaisPorDataCredor(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE NOMEDOCREDOR = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }

        return totalPG2;

    }
    public String totalContPorDataCredor(String NOMEDOCREDOR, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE NOMEDOCREDOR = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC, instr(DATAVENC, '/')+1), instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC, instr(DATAVENC, '/')+1, instr(substr(DATAVENC, instr(DATAVENC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC,1, instr(DATAVENC, '/')-1 )) =2" +
                " then substr(DATAVENC,1, instr(DATAVENC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC,1, instr(DATAVENC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMEDOCREDOR, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }

    // Soma dos valores  e totais das dívidas em aberto por data de vencimento
    public String somarAbertoTodos() {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 ORDER BY DATAVENC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            totalAberto = cursor.getString(0);
        }

        return totalAberto;

    }
    public String totalAbertoTodos() {

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 ORDER BY DATAVENC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }

       
        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por data de vencimento
    public String somarPGTodos() {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 ORDER BY DATAVENC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }

        return totalPG;

    }
    public String totalPGTodos() {

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 ORDER BY DATAVENC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }

    // TOTAIS DAS QUANTIDADES E VALORES EM REAIS TODAS AS DÍVIDAS
    public String totalReaisTodosDiv() {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 ORDER BY DATAVENC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }

        return totalPG;

    }
    public String totalContTodosDiv() {

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 ORDER BY DATAVENC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }

    // Soma dos valores  e totais das dívidas abertas por nome da divia e  data de vencimento
    public String somarAbertoSpinnerTodosPorCredor(String NOMEDOCREDOR) {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 AND NOMEDOCREDOR = ? ";

        String[] parametros = {NOMEDOCREDOR};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalAberto2 = cursor.getString(0);
        }

        return totalAberto2;

    }
    public String totalAbertoSpinnerTodosPorCredor(String NOMEDOCREDOR) {

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 0 AND NOMEDOCREDOR = ?";


        String[] parametros = {NOMEDOCREDOR};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }

        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por nome da divia e  data de vencimento
    public String somarPGSpinnerTodosPorCredor(String NOMEDOCREDOR) {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 AND NOMEDOCREDOR = ?";

        String[] parametros = {NOMEDOCREDOR};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }

        return totalPG2;

    }
    public String totalPGSpinnerTodosPorCredor(String NOMEDOCREDOR) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE PG = 1 AND NOMEDOCREDOR = ?";

        String[] parametros = {NOMEDOCREDOR};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }

    // TOTAIS DAS QUANTIDADES E VALORES EM REAIS TODAS AS DÍVIDAS POR CLIENTE
    public String totalReaisTodosPorCredor(String NOMEDOCREDOR) {

        String SelectQuery = "SELECT SUM(VALORDIV) AS total FROM DIVIDAS2 WHERE NOMEDOCREDOR = ?";

        String[] parametros = {NOMEDOCREDOR};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }

        return totalPG2;

    }
    public String totalContTodosPorCredor(String NOMEDOCREDOR) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV) AS total FROM DIVIDAS2 WHERE NOMEDOCREDOR = ?";

        String[] parametros = {NOMEDOCREDOR};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }

        return countPG;
    }


    public void DivPaga(int ID) {

        String UpdateQuery = "UPDATE DIVIDAS2 SET PG = 1 WHERE  ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();


    }
    public void DivNoPaga(int ID) {

        String UpdateQuery = "UPDATE DIVIDAS2 SET PG = 0 WHERE  ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();


    }

    public boolean excluirDivida(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("DIVIDAS2", "ID=?", new String[]{ id + "" }) > 0;

    }

    public int insertDividas(String table, ContentValues values) {
        try {
            db = this.getWritableDatabase();
            int y = (int) db.insert(table, null, values);
            db.close();
            // Log.e("Data Inserted", "Data Inserted");
            //Log.e("y", y + "");
            return y;
        } catch (Exception ex) {
            Log.e("Error Insert", ex.getMessage().toString());
            return 0;
        }
    }

    public  String select_NomeListaDeDividas(int ID){

        String selectQuery = "select NOMELISTA from T_RELA_LIST_DIVIDAS2 where ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, parametros);

        if (cursor.moveToNext()) {

            resultNomeListaDivida = cursor.getString(0);
        }

        cursor.close();
        return resultNomeListaDivida;
    }


    //CRUDS PARA CLASSE DIVIDASARECEBER ****************************************************************************************************
    @SuppressLint("Range")
    public ArrayList<String> buscarNomeClienteSppinnerPordaRec(String DATAVENCINICIAL, String DATAVENCIFINAL){

        SQLiteDatabase db = getReadableDatabase();

        String sql_busca_dados_spinner = "SELECT DISTINCT NOMECLIENTE FROM RECEBER " +
                "WHERE julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";


        ArrayList<String> dados_spinner = new ArrayList<>();

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        Cursor cursor = db.rawQuery(sql_busca_dados_spinner, parametros);

        while (cursor.moveToNext()){

            dados_spinner.add(cursor.getString(cursor.getColumnIndex("NOMECLIENTE")));

        }


        return (dados_spinner);
    }

    @SuppressLint("Range")
    public ArrayList<String> buscarNomeClienteSppinerTodosRec(){

        SQLiteDatabase db = getReadableDatabase();

        //String sql_busca_dados_spinner = "SELECT DISTINCT NOMEDIV FROM  DIVIDAS2 where DATAVENC BETWEEN ? AND ? ORDER BY ID DESC";

        String sql_busca_dados_spinner = "SELECT DISTINCT NOMECLIENTE FROM RECEBER ORDER BY ID";


        ArrayList<String> dados_spinner = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql_busca_dados_spinner, null);

        while (cursor.moveToNext()){

            dados_spinner.add(cursor.getString(cursor.getColumnIndex("NOMECLIENTE")));

        }


        return (dados_spinner);
    }

    // Soma dos valores  e totais das dívidas em aberto por data de vencimento
    public String somarAberto_Rec(String DATAVENCINICIAL, String DATAVENCIFINAL) {



        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 AND " +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalAberto = cursor.getString(0);
        }


        return totalAberto;

    }
    public String totalAberto_Rec(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        // String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //       " FROM DIVIDAS2 WHERE PG = 0 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 AND " +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        //select coUnt(nomediv) from dividas where ID_LDIV = 1 AND PG = 0

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }

        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por data de vencimento
    public String somarPG_Rec(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        // String SelectQuery = "SELECT SUM(VALORDIV) AS total\n" +
        //       " FROM DIVIDAS2 WHERE PG = 1 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 AND " +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }

        return totalPG;

    }
    public String totalPG_Rec(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 AND " +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    //TOTAIS DAS QUANTIDADES E VALORES EM REAIS POR DATA
    public String totalReaisPorDataRec(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        // String SelectQuery = "SELECT SUM(VALORDIV) AS total\n" +
        //       " FROM DIVIDAS2 WHERE PG = 1 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE " +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }

        return totalPG;

    }
    public String totalContPorDataRec(String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE" +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {DATAVENCINICIAL, DATAVENCIFINAL};


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    // Soma dos valores  e totais das dívidas abertas por nome da divia e  data de vencimento
    public String somarAberto2_Filtro_Rec(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 AND NOMECLIENTE = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";


        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalAberto2 = cursor.getString(0);
        }


        return totalAberto2;

    }
    public String totalAberto_Filtro_Rec(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 AND NOMECLIENTE = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }

        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por nome da divia e  data de vencimento
    public String somarPG2_Filtro_Rec(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 AND NOMECLIENTE = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }

        cursor.close();
        return totalPG2;

    }
    public String totalPG_Filtro_Rec(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 AND NOMECLIENTE = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    // TOTAIS DAS QUANTIDADES E VALORES EM REAIS POR DATA E NOME DO CLIENTE
    public String totalReaisPorDataClienteRec(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE NOMECLIENTE = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);


        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }

        cursor.close();
        return totalPG2;

    }
    public String totalContPorDataClienteRec(String NOMECLIENTE, String DATAVENCINICIAL, String DATAVENCIFINAL) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE NOMECLIENTE = ? AND" +
                " julianday(" +
                " substr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')+1)" +
                "||'-'||" +
                " case when length(" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1),'/')-1)" +
                " )=2" +
                " then" +
                " substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " else" +
                " '0'||substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1, instr(substr(DATAVENC_REC, instr(DATAVENC_REC, '/')+1), '/')-1)" +
                " end" +
                " ||'-'||" +
                " case when length(substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )) =2" +
                " then substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 ) " +
                " else" +
                " '0'||substr(DATAVENC_REC,1, instr(DATAVENC_REC, '/')-1 )" +
                " end " +
                ") BETWEEN julianday(?) AND julianday(?)";

        String[] parametros = {NOMECLIENTE, DATAVENCINICIAL, DATAVENCIFINAL};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    // Soma dos valores  e totais das dívidas em aberto por data de vencimento
    public String somarAberto_RecTodos() {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 ORDER BY DATAVENC_REC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            totalAberto = cursor.getString(0);
        }


        return totalAberto;

    }
    public String totalAberto_RecTodos() {

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 ORDER BY DATAVENC_REC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }


        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por data de vencimento
    public String somarPG_RecTodos() {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 ORDER BY DATAVENC_REC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }


        return totalPG;

    }
    public String totalPG_RecTodos() {

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 ORDER BY DATAVENC_REC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    // TOTAIS DAS QUANTIDADES E VALORES EM REAIS TODAS AS DÍVIDAS
    public String totalReaisTodosDivRec() {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER ORDER BY DATAVENC_REC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            totalPG = cursor.getString(0);
        }


        return totalPG;

    }
    public String totalContTodosDivRec() {

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER ORDER BY DATAVENC_REC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    // Soma dos valores  e totais das dívidas abertas por nome da divia e  data de vencimento
    public String somarAbertoSpinnerTodosPorCliente(String NOMECLIENTE) {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 AND NOMECLIENTE = ? ";

        String[] parametros = {NOMECLIENTE};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalAberto2 = cursor.getString(0);
        }


        return totalAberto2;

    }
    public String totalAbertoSpinnerTodosPorCliente(String NOMECLIENTE) {

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 0 AND NOMECLIENTE = ?";


        String[] parametros = {NOMECLIENTE};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            contAberto = cursor.getString(0);
        }


        return contAberto;
    }

    // Soma dos valores  e totais das dívidas pagas por nome da divia e  data de vencimento
    public String somarPGSpinnerTodosPorCliente(String NOMECLIENTE) {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 AND NOMECLIENTE = ?";

        String[] parametros = {NOMECLIENTE};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }


        return totalPG2;

    }
    public String totalPGSpinnerTodosPorCliente(String NOMECLIENTE) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE PG_REC = 1 AND NOMECLIENTE = ?";

        String[] parametros = {NOMECLIENTE};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    // TOTAIS DAS QUANTIDADES E VALORES EM REAIS TODAS AS DÍVIDAS POR CLIENTE
    public String totalReaisTodosPorClienteRec(String NOMECLIENTE) {

        String SelectQuery = "SELECT SUM(VALORDIV_REC) AS total FROM RECEBER WHERE NOMECLIENTE = ?";

        String[] parametros = {NOMECLIENTE};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            totalPG2 = cursor.getString(0);
        }


        return totalPG2;

    }
    public String totalContTodosPorClienteRec(String NOMECLIENTE) {

        //  String SelectQuery = "SELECT count(VALORDIV) AS total\n" +
        //        " FROM DIVIDAS2 WHERE PG = 1 AND NOMEDIV = ? AND DATAVENC BETWEEN ? AND ?";

        String SelectQuery = "SELECT count(VALORDIV_REC) AS total FROM RECEBER WHERE NOMECLIENTE = ?";

        String[] parametros = {NOMECLIENTE};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, parametros);

        if (cursor.moveToNext()) {

            countPG = cursor.getString(0);
        }


        return countPG;
    }

    public void DivPaga_Rec(int ID) {

        String UpdateQuery = "UPDATE RECEBER SET PG_REC = 1 WHERE  ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();

    }
    public void DivNoPaga_Rec(int ID) {

        String UpdateQuery = "UPDATE RECEBER SET PG_REC = 0 WHERE  ID = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(ID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UpdateQuery, parametros);

        cursor.moveToFirst();


    }

    public boolean excluirDivida_Rec(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("RECEBER", "ID=?", new String[]{ id + "" }) > 0;
    }

    public int insertDividasAReceber(String table, ContentValues values) {
        try {
            db = this.getWritableDatabase();
            int y = (int) db.insert(table, null, values);
            db.close();
            // Log.e("Data Inserted", "Data Inserted");
            //Log.e("y", y + "");
            return y;
        } catch (Exception ex) {
            Log.e("Error Insert", ex.getMessage().toString());
            return 0;
        }
    }


}
