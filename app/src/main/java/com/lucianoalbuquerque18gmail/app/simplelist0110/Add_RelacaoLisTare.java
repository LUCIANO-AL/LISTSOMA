package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter.ListaDeLTarfAdapter;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.List_DeL_Tarefa;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Lista_LtrefaRepo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_t.interstitial_HomeListTarefa;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_excluir_tudo;


import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_home;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.menuSair;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update_T;

public class Add_RelacaoLisTare extends AppCompatActivity {

    private RecyclerView listRelaListsT;

    private Button butonVoltar_T;

    private ListaDeLTarfAdapter listaDeLTarfAdapter;

    private Lista_LtrefaRepo lista_LtrefaRepo;
    private DataBaseLista dataBaseLista;
    private SQLiteDatabase conexaoList;

    private EditText edtNovaListTarefa;
    private EditText edtId;
    private EditText edtIdTare;

    private List_DeL_Tarefa list_DeL_Tarefa;
    private ImageButton buttonAddTare;

    private EditText edtNovaList_T;
    private EditText edtId_T;
    private Button btnEdit_T;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private EditText edtDataListTare_Insert;
    private EditText edtDataListTare, edtIdListTareAdd;
    private ImageButton btnDataListTare;
    private TextView txtCopiarLTare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_relacao_listare);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Todas as Listas");
        toolbar.setLogo(R.drawable.logo_barra_tare);
        setSupportActionBar(toolbar);

        AdView mAdView3 = (AdView) findViewById(R.id.adView3);

        AdView adViewUpdtRelaListaTarefa = (AdView) findViewById(R.id.adViewUpdtRelaListaTarefa);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView3.loadAd(adRequest);

        adViewUpdtRelaListaTarefa.loadAd(adRequest);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewById(update_T).setVisibility(View.INVISIBLE);

        referenciaXML();

        criarConexaoT();
        //ALterTabelaRelaTare();

        verificaParametroListT();
        verificaParametroListCopiaT();

        listRelaListsT.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerT = new LinearLayoutManager(this);
        listRelaListsT.setLayoutManager(linearLayoutManagerT);

        lista_LtrefaRepo = new Lista_LtrefaRepo(conexaoList);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateTimeString = dateFormat.format(new Date());
        edtDataListTare_Insert.setText(currentDateTimeString);

        btnDataListTare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Add_RelacaoLisTare.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataListTare.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        edtDataListTare.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(Add_RelacaoLisTare.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            edtDataListTare.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            //edtDataListTare.setText((mMes+1) + "/" + mAno);
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();
                }
            }
        });

        buttonAddTare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaCamposTarAdd() == false) try {

                    if (list_DeL_Tarefa.ID_T == 0) {
                        criarConexaoT();
                        lista_LtrefaRepo.inserirLista(list_DeL_Tarefa);
                        // listaDeLTarfAdapter.adicionarCliente(list_DeL_Tarefa);

                        edtIdListTareAdd.setText(dataBaseLista.ultimoId_Tarefas());

                        Intent it = new Intent(Add_RelacaoLisTare.this, List_t.class);
                        //it.putExtra("listarefa", edtId_T.getText().toString());
                        startActivity(it);

                        //it.putExtra("listnomeT", edtNovaListTarefa.getText().toString());
                        //startActivity(it);

                      /*  ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(edtNovaListTarefa.getWindowToken(), 0);
                        edtNovaListTarefa.setText("");
                      */
                    }

                    //BuscarAdapterT();
                    fechaConexao();

                } catch (SQLException ex) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
                    dlg.show();
                }
            }
        });

        btnEdit_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCamposDialogT() == false) try {

                    if (list_DeL_Tarefa.ID_T >= 0) {
                        criarConexaoT();
                        lista_LtrefaRepo.alterarLista(list_DeL_Tarefa);
                        listaDeLTarfAdapter.atualizarNomeListT(list_DeL_Tarefa);

                        findViewById(update_T).setVisibility(View.INVISIBLE);
                        //atualizartelaT();
                        //  finish();

                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(edtNovaList_T.getWindowToken(), 0);
                    }

                    BuscarAdapterT();
                    fechaConexao();

                } catch (SQLException ex) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
                    dlg.show();
                }
            }
        });

        butonVoltar_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(update_T).setVisibility(View.INVISIBLE);
                // finish();
            }
        });

        BuscarAdapterT();
        //configurarRecyclerT();

        fechaConexao();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtCopiarLTare.setVisibility(View.GONE);

            }
        },20000);
    }

    public void referenciaXML(){
        edtIdListTareAdd = (EditText) findViewById(R.id.edtIdListTareAdd);
        edtId = (EditText) findViewById(R.id.edtId);

        edtNovaList_T = (EditText) findViewById(R.id.edtNovaList_T);
        edtId_T = (EditText) findViewById(R.id.edtId_T);
        btnEdit_T = (Button) findViewById(R.id.btnEdit_T);

        edtIdTare = (EditText) findViewById(R.id.edtIdTare);
        edtNovaListTarefa = (EditText) findViewById(R.id.edtNovaListTarefa);
        buttonAddTare = (ImageButton) findViewById(R.id.buttonAddTare);

        listRelaListsT = (RecyclerView) findViewById(R.id.listRelaListsT);

        butonVoltar_T = (Button) findViewById(R.id.butonVoltar_T);

        edtDataListTare_Insert = (EditText) findViewById(R.id.edtDataListTare_Insert);
        edtDataListTare = (EditText) findViewById(R.id.edtDataListTare);
        btnDataListTare = (ImageButton)findViewById(R.id.btnDataListTare);

        txtCopiarLTare = (TextView) findViewById(R.id.txtCopiarLTare);
    }

    public void voltaListVazia(View view){
        onBackPressed();

    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, List_t.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    public void BuscarAdapterT(){
        List<List_DeL_Tarefa> rela2 = lista_LtrefaRepo.buscarTodasListas();
        listaDeLTarfAdapter = new ListaDeLTarfAdapter(rela2);
        listRelaListsT.setAdapter(listaDeLTarfAdapter);
    }

    public void verificaParametroListT() {

        Bundle bundle = getIntent().getExtras();

        list_DeL_Tarefa = new List_DeL_Tarefa();

        if ((bundle != null) && (bundle.containsKey("LISTA_DE_LIST_TARE"))) {

            findViewById(update_T).setVisibility(View.VISIBLE);

            list_DeL_Tarefa = (List_DeL_Tarefa) bundle.getSerializable("LISTA_DE_LIST_TARE");

            edtId_T.setText(String.valueOf(list_DeL_Tarefa.ID_T));
            edtNovaList_T.setText(list_DeL_Tarefa.LISTATAREFA);
            edtDataListTare.setText(list_DeL_Tarefa.DATALISTA);

        }

    }
    public void verificaParametroListCopiaT() {

        Bundle bundle = getIntent().getExtras();

        list_DeL_Tarefa = new List_DeL_Tarefa();

        if ((bundle != null) && (bundle.containsKey("LIST_TAREFA_C"))) {

            list_DeL_Tarefa = (List_DeL_Tarefa) bundle.getSerializable("LIST_TAREFA_C");

            edtIdTare.setText(String.valueOf(list_DeL_Tarefa.ID_T));
            edtNovaListTarefa.setText(list_DeL_Tarefa.LISTATAREFA);
            edtDataListTare_Insert.setText(list_DeL_Tarefa.DATALISTA);

            if (validaCamposTar() == false) try {

                if (list_DeL_Tarefa.ID_T > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.logo_barra)
                            .setTitle(" ListSoma Lista de tarefas")
                            .setMessage("Copiar lista?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    criarConexaoT();
                                    lista_LtrefaRepo.inserirListaC(list_DeL_Tarefa);
                                    dataBaseLista.insertMultiploT(list_DeL_Tarefa.ID_T);
                                    edtNovaListTarefa.setText("");
                                    BuscarAdapterT();
                                    fechaConexao();
                                }
                            })
                            .setNegativeButton("NÃO", null)
                            .create()
                            .show();
                }
            } catch (SQLException ex) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                dlg.setTitle("Erro");
                dlg.setMessage(ex.getMessage());
                dlg.setNeutralButton("R.string.text_action_ok_conexao", null);
                dlg.show();
            }
        }
    }

    private void criarConexaoT() {
        try {
            dataBaseLista = new DataBaseLista(this);
            conexaoList = dataBaseLista.getWritableDatabase();
            lista_LtrefaRepo = new Lista_LtrefaRepo(conexaoList);

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();

        }
    }

    private void fechaConexao() {
        try {
            dataBaseLista = new DataBaseLista(this);
            conexaoList = dataBaseLista.getWritableDatabase();
            lista_LtrefaRepo = new Lista_LtrefaRepo(conexaoList);
            dataBaseLista.close();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    private boolean validaCamposDialogT() {
        boolean res = false;

        int ID_T = Integer.parseInt(edtId_T.getText().toString());
        String LISTATAREFA= edtNovaList_T.getText().toString();
        String DATALISTA= edtDataListTare.getText().toString();

        list_DeL_Tarefa.ID_T = ID_T;
        list_DeL_Tarefa.LISTATAREFA = LISTATAREFA;
        list_DeL_Tarefa.DATALISTA = DATALISTA;

        if (res = isCampoVazio(LISTATAREFA)) {
            // edtNovaList.requestFocus();
        }
        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.lista_message_validacampo);
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }
        return res;
    }

    private boolean validaCamposTar() {
        boolean res = false;

        int ID_T = Integer.parseInt(edtIdTare.getText().toString());
        String LISTATAREFA = edtNovaListTarefa.getText().toString();
        String DATALISTA= edtDataListTare_Insert.getText().toString();

        list_DeL_Tarefa.ID_T = ID_T;
        list_DeL_Tarefa.LISTATAREFA = LISTATAREFA;
        list_DeL_Tarefa.DATALISTA = DATALISTA;

        if (res = isCampoVazio(LISTATAREFA)) {
            edtNovaListTarefa.requestFocus();
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.lista_message_validacampo);
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }
    private boolean validaCamposTarAdd() {

        boolean res = false;

        // int ID_T = Integer.parseInt(edtIdTare.getText().toString());
        String LISTATAREFA = edtNovaListTarefa.getText().toString();
        String DATALISTA= edtDataListTare_Insert.getText().toString();

        //list_DeL_Tarefa.ID_T = ID_T;
        list_DeL_Tarefa.LISTATAREFA = LISTATAREFA;
        list_DeL_Tarefa.DATALISTA = DATALISTA;

        if (res = isCampoVazio(LISTATAREFA)) {
            edtNovaListTarefa.requestFocus();
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.lista_message_validacampo);
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }
    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_rela_listare, menu);
        return true;
    }
    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case action_excluir_tudo:

                dataBaseLista = new DataBaseLista(this);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_listas_del)
                        .setMessage(R.string.message_listas_dell)
                        .setPositiveButton(R.string.text_listas_sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        criarConexaoT();
                                        dataBaseLista.excluirtudaslistas();
                                        dataBaseLista.excluirtudo();
                                        fechaConexao();

                                        // Intent it = new Intent(Add_RelacaoList.this, Add_RelacaoList.class);
                                        //startActivity(it);
                                    }
                                })
                        .setNegativeButton(R.string.txt_listas_nao, null).show();

                break;

            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, List_t.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case menuSair:
                finishAffinity();
                break;

            case action_home:
                if (interstitial_HomeListTarefa != null) {
                    interstitial_HomeListTarefa.show(Add_RelacaoLisTare.this);

                    interstitial_HomeListTarefa.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(Add_RelacaoLisTare.this, MenuInicial.class));
                        }
                    });
                } else {
                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }
                break;
        }


        return super.onOptionsItemSelected(item);

    }

    /*private void configurarRecyclerT() {

        listRelaListsT = (RecyclerView) findViewById(R.id.listRelaListsT);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listRelaListsT.setLayoutManager(layoutManager);
        // Adiciona o adapter que irá anexar os objetos à lista.
        BuscarAdapterT();
        // Configurando um separador entre linhas, para uma melhor visualização.
        listRelaListsT.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/


}