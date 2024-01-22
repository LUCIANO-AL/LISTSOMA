package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter.ListaDeListasAdapter;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter.Tarefa_Adapter;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.List_DeL_Tarefa;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Lista_De_List;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Tarefa;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaRepositorio;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Lista_LtrefaRepo;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Tarefa_Reposi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_home;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_homeT;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_todas_listas;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.addListaDeComp;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.menuSair;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.menuSairT;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.telaAddListaDeTare;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update_list;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update_tarefa;

public class List_t extends AppCompatActivity {
    private EditText edtTarefa_UpdT, edtNovaList_T, edtDataListTare;
    private Spinner edtPriori_UpdT;
    private CheckBox checkB_UpdT;
    public EditText edtId_UpdT, edtIdTareList_Upd;

    private EditText edtTarefa;
    private Spinner edtPriori;
    private CheckBox checkB;
    public EditText edtId;

    private RecyclerView listItemCompras;
    private ConstraintLayout itemContraintLayout;

    private SQLiteDatabase conexao;
    private DataBaseLista dataBase;
    private Tarefa_Reposi tarefa_reposi;
    private Lista_LtrefaRepo lista_LtrefaRepo;
    private Tarefa tarefa;
    private Tarefa_Adapter tarefa_adapter;
    private List_DeL_Tarefa list_DeL_Tarefa;

    private ImageButton buttonAdd;
    private Button btnEdit_UpdT;
    private Button butonVoltar_T;

    /*    public SharedPreferences preferences_tarefa;
        public SharedPreferences.Editor editor_tarefa;
        public int contagem_tarefa = 0;
        public Button contagemProgressiva;
        public TextView texto_tarefa;*/
    public TextView txtTareUpdt, txtIDListaUpdtTare;

    public static InterstitialAd tare_interstitial, interstitial_HomeListTarefa;

    public int contadorPropagandaListTarefa = 0;

    private Spinner spinnerListasTarefa;

    private Button visuTelaNomeLista;

    public String compareValorSpiner;

    private ImageButton btnDataListTare;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list_t);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Tarefas");
        toolbar.setLogo(R.drawable.logo_barra_tare);
        setSupportActionBar(toolbar);

       /* preferences_tarefa = getSharedPreferences("Toque2", MODE_PRIVATE);
        contagem_tarefa = preferences_tarefa.getInt("contador2", 0);
        editor_tarefa = preferences_tarefa.edit();*/

        AdView mAdView5 = (AdView) findViewById(R.id.adView5);
        AdView adViewListaTarefa = (AdView) findViewById(R.id.adViewListaTarefa);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView5.loadAd(adRequest);
        adViewListaTarefa.loadAd(adRequest);

        //buttonClick_Conta();
        //contagemProgressiva.performClick();
        //contador_Propag();

        //  findViewById(update_tarefa).setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        AdRequest adRequest_tarefa = new AdRequest.Builder().build();
        /*tare_interstitial = new InterstitialAd(List_t.this);
        tare_interstitial.setAdUnitId(getString(R.string.admob_interstitial_tarefa));
        tare_interstitial.loadAd(adRequest_tarefa);*/

        InterstitialAd.load(this, getString(R.string.admob_interstitial_tarefa), adRequest_tarefa,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        tare_interstitial = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Toast.makeText(List_t.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        tare_interstitial = null;
                    }
                });

        InterstitialAd.load(this, getString(R.string.intersticial_id_home_listtaref), adRequest_tarefa,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitial_HomeListTarefa = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Toast.makeText(List_t.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        tare_interstitial = null;
                    }
                });

        referenciaXML();

        criarConexao();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateTimeString = dateFormat.format(new Date());
        edtDataListTare.setText(currentDateTimeString);

        btnDataListTare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(List_t.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataListTare.setText(mDia + "/" + (mMes + 1) + "/" + mAno);
                        //edtDataListTare.setText((mMes+1) + "/" + mAno);
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

                    datePickerDialog = new DatePickerDialog(List_t.this, 0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            edtDataListTare.setText(mDia + "/" + (mMes + 1) + "/" + mAno);
                            //edtDataListTare.setText((mMes+1) + "/" + mAno);
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();
                }
            }
        });

        popularSpinnerTare();

        spinnerListasTarefa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                criarConexao();
                edtId_UpdT.setText(dataBase.buscarIDListTare(spinnerListasTarefa.getSelectedItem().toString()));

                toolbar.setTitle(spinnerListasTarefa.getSelectedItem().toString());

                // verificarPosicaoSpinner();

                buscaTarefa_Adpter();

                fechaConexao();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        verificaParametroRelaList();
        verificaCheck();

        // verificaParametro();

        listItemCompras.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listItemCompras.setLayoutManager(linearLayoutManager);

        tarefa_reposi = new Tarefa_Reposi(conexao);

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //contarPropaganda();

                if (validaCampos() == false) try {

                    if (tarefa.id_tarefa >= 0) {

                        criarConexao();
                        tarefa_reposi.inserir(tarefa);
                        tarefa_adapter.adicionarCliente(tarefa);

                        edtTarefa.setText("");
                        edtPriori.setSelection(0);
                        edtTarefa.requestFocus();
                    }

                    buscaTarefa_Adpter();
                    fechaConexao();


                    //  if (dataBase.somarCategoria(Integer.parseInt(edtId.getText().toString())) != null) {
                    //    txtTotal.setText(dataBaseLista.somarCategoria(Integer.parseInt(edtId.getText().toString())) + " R$");
                    //}
                } catch (SQLException ex) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton("R.string.text_action_ok_conexao", null);
                    dlg.show();
                }
            }
        });

        btnEdit_UpdT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validaCampos_UptT() == false) try {

                    if (tarefa.id_tarefa > 0) {
                        criarConexao();
                        tarefa_reposi.alterar(tarefa);
                        //edtTarefa.setText("");
                        //edtPriori.setSelection(0);
                        //edtTarefa.requestFocus();
                    }

                    buscaTarefa_Adpter();

                    compareValorSpiner = dataBase.buscarNomeListTarefa(txtIDListaUpdtTare.getText().toString());

                    verificarPosicaoSpinner();

                    fechaConexao();

                    findViewById(update_tarefa).setVisibility(View.INVISIBLE);
                    //finish();

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtTarefa.getWindowToken(), 0);

                  /*  if (dataBaseLista.somarCategoria(Integer.parseInt(edtId.getText().toString())) != null) {
                        txtTotal.setText(dataBaseLista.somarCategoria(Integer.parseInt(edtId.getText().toString())) + " R$");
                    }*/
                } catch (SQLException ex) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton("R.string.text_action_ok_conexao", null);
                    dlg.show();
                }

            }


        });

        butonVoltar_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(update_tarefa).setVisibility(View.INVISIBLE);
                //finish();
            }
        });

        buscaTarefa_Adpter();
        //configurarRecycler();
        fechaConexao();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                txtTareUpdt.setVisibility(View.GONE);

            }
        }, 20000);
    }

    private void referenciaXML() {
        spinnerListasTarefa = (Spinner) findViewById(R.id.spinnerListasTarefa);

        edtTarefa = (EditText) findViewById(R.id.edtTarefa);
        edtPriori = (Spinner) findViewById(R.id.edtPriori);
        checkB = (CheckBox) findViewById(R.id.checkB);
        edtId = (EditText) findViewById(R.id.edtId);

        edtTarefa_UpdT = (EditText) findViewById(R.id.edtTarefa_UpdT);
        edtPriori_UpdT = (Spinner) findViewById(R.id.edtPriori_UpdT);
        checkB_UpdT = (CheckBox) findViewById(R.id.checkB_UpdT);
        edtId_UpdT = (EditText) findViewById(R.id.edtId_UpdT);

        buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);
        //  buttonEdit = (ImageButton) findViewById(R.id.buttonEdit);
        btnEdit_UpdT = (Button) findViewById(R.id.btnEdit_UpdT);
        butonVoltar_T = (Button) findViewById(R.id.butonVoltar_T);
        visuTelaNomeLista = (Button) findViewById(R.id.visuTelaNomeLista);

        txtTareUpdt = (TextView) findViewById(R.id.txtTareUpdt);
        txtIDListaUpdtTare = (TextView) findViewById(R.id.txtIDListaUpdtTare);

        listItemCompras = (RecyclerView) findViewById(R.id.listItemCompras);
        itemContraintLayout = (ConstraintLayout) findViewById(R.id.itemContraintLayout);

        edtNovaList_T = (EditText) findViewById(R.id.edtNovaList_T);
        edtDataListTare = (EditText) findViewById(R.id.edtDataListTare);

        btnDataListTare = (ImageButton) findViewById(R.id.btnDataListTare);
    }

    private void popularSpinnerTare() {
        ArrayAdapter<String> adapter_spinner_nomes_listastare = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, dataBase.buscarNomeListTareSippner());
        spinnerListasTarefa.setAdapter(adapter_spinner_nomes_listastare);
    }

    public void verificarPosicaoSpinner() {

        ArrayAdapter<String> adapter_spinner_nomes_listastare = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, dataBase.buscarNomeListTareSippner());
        spinnerListasTarefa.setAdapter(adapter_spinner_nomes_listastare);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.preloaded_fonts, android.R.layout.simple_list_item_1);

        //adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerListasTarefa.setAdapter(adapter_spinner_nomes_listastare);

        if (!compareValorSpiner.equals(null)) {
            int spinnerPosition = adapter_spinner_nomes_listastare.getPosition(compareValorSpiner);
            spinnerListasTarefa.setSelection(spinnerPosition);
            //spinnerPosition = 0;
        }
    }

    public void contarPropaganda() {

        contadorPropagandaListTarefa++;

        if (contadorPropagandaListTarefa == 2) {

            if (tare_interstitial != null) {

                if (tare_interstitial != null) {
                    tare_interstitial.show(List_t.this);
                } else {
                    //Toast.makeText(List_t.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
    /*public void buttonClick_Conta() {
        texto_tarefa = (TextView)findViewById(R.id.texto_tarefa);
        contagemProgressiva = (Button) findViewById(R.id.contagemProgressiva);
      //  contagem_tarefa = preferences_tarefa.getInt("contador", 0);
        texto_tarefa.setText("Contagem atual:"+ contagem_tarefa);

        contagemProgressiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contagem_tarefa >= 3){
                    contagem_tarefa = contagem_tarefa - contagem_tarefa;
                }
                contagem_tarefa++;
                texto_tarefa.setText("Contagem atual: " + contagem_tarefa);
                editor_tarefa.putInt("contador2", contagem_tarefa);
                editor_tarefa.commit();
            }
        });
    }*/

    public void buscaTarefa_Adpter() {
        List<Tarefa> dados = tarefa_reposi.buscarTodos(Integer.parseInt(edtId_UpdT.getText().toString()));
        tarefa_adapter = new Tarefa_Adapter(dados);
        listItemCompras.setAdapter(tarefa_adapter);
    }

    public void verificaCheck() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();

        tarefa = new Tarefa();

        if ((bundle != null) && (bundle.containsKey("ITEMt"))) {

            findViewById(update_tarefa).setVisibility(View.VISIBLE);

            tarefa = (Tarefa) bundle.getSerializable("ITEMt");

            edtTarefa_UpdT.setText(tarefa.Tarefa);
            edtPriori_UpdT.getSelectedItem().toString();
            checkB_UpdT.setChecked(tarefa.check);
            //itemm.check = checkB.isChecked();
            // edtId_UpdT.setText(String.valueOf(tarefa.ID_T));
            txtIDListaUpdtTare.setText(String.valueOf(tarefa.ID_T));

            toolbar.setTitle(dataBase.select_NomeListaDeTarefas(Integer.parseInt(edtId_UpdT.getText().toString())));

            compareValorSpiner = dataBase.buscarNomeListTarefa(txtIDListaUpdtTare.getText().toString());

            verificarPosicaoSpinner();
        }
    }

    public void verificaParametroRelaList() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();

        list_DeL_Tarefa = new List_DeL_Tarefa();

        if ((bundle != null) && (bundle.containsKey("LISTA_DE_LIST_TARE"))) {

            list_DeL_Tarefa = (List_DeL_Tarefa) bundle.getSerializable("LISTA_DE_LIST_TARE");

            edtId_UpdT.setText(String.valueOf(list_DeL_Tarefa.ID_T));
            toolbar.setTitle(list_DeL_Tarefa.LISTATAREFA);

            compareValorSpiner = list_DeL_Tarefa.LISTATAREFA;

            verificarPosicaoSpinner();

        } else {

            if (dataBase.idIgualUmRelTare() == "0") {

                findViewById(telaAddListaDeTare).setVisibility(View.VISIBLE);
                edtId_UpdT.setText("0");


            } else {
                edtId_UpdT.setText(dataBase.buscarIDListTare(spinnerListasTarefa.getSelectedItem().toString()));

            }


        }
    }

    private void criarConexao() {

        try {

            dataBase = new DataBaseLista(this);
            conexao = dataBase.getWritableDatabase();
            // Snackbar.make(itemContraintLayout, R.string.message_conexao_criada_sucesso, Snackbar.LENGTH_LONG)
            //        .setAction (getString(R.string.text_action_ok_conexao), null).show();
            tarefa_reposi = new Tarefa_Reposi(conexao);

        } catch (SQLException ex) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("R.string.text_action_ok_conexao", null);
            dlg.show();

        }
    }

    private void fechaConexao() {
        try {
            dataBase = new DataBaseLista(this);
            conexao = dataBase.getWritableDatabase();
            tarefa_reposi = new Tarefa_Reposi(conexao);
            dataBase.close();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    private boolean validaCampos() {

        boolean res = false;

        String Tarefa = edtTarefa.getText().toString();
        String Prioridade = edtPriori.getSelectedItem().toString();
        int ID_T = Integer.parseInt(edtId_UpdT.getText().toString());
        boolean check = checkB.isChecked();

        tarefa.Tarefa = Tarefa;
        tarefa.Prioridade = Prioridade;
        tarefa.ID_T = ID_T;
        tarefa.check = check;

        if (res = isCampoVazio(Tarefa)) {
            edtTarefa.requestFocus();
        }
        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.message_campos_invalidosT);
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }

    private boolean validaCampos_UptT() {

        boolean res = false;

        String Tarefa = edtTarefa_UpdT.getText().toString();
        String Prioridade = edtPriori_UpdT.getSelectedItem().toString();
        int ID_T = Integer.parseInt(edtId_UpdT.getText().toString());
        boolean check = checkB_UpdT.isChecked();

        tarefa.Tarefa = Tarefa;
        tarefa.Prioridade = Prioridade;
        tarefa.ID_T = ID_T;
        tarefa.check = check;

        if (res = isCampoVazio(Tarefa)) {
            edtTarefa_UpdT.requestFocus();
        }
        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.message_campos_invalidosT);
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }

    private boolean isCampoVazio(String valor) {

        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;

    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuInicial.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tarefa, menu);
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:

                if (interstitial_HomeListTarefa != null) {
                    interstitial_HomeListTarefa.show(List_t.this);

                    interstitial_HomeListTarefa.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(List_t.this, MenuInicial.class));
                        }
                    });
                } else {
                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }

                break;

            case R.id.action_excluir_tudoT:

                dataBase = new DataBaseLista(this);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.itens_title_excluirtudo)
                        .setMessage(R.string.item_message_extudoT)
                        .setPositiveButton(R.string.button_item_sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        criarConexao();
                                        tarefa_reposi.excluirTdosItens(Integer.parseInt(edtId_UpdT.getText().toString()));
                                        edtTarefa.setText("");
                                        edtPriori.setSelection(0);

                                        buscaTarefa_Adpter();
                                        fechaConexao();

                                    }
                                })
                        .setNegativeButton(R.string.button_item_exctudo_nao, null).show();

               /* dataBaseLista = new DataBaseLista(this);
                if(dataBaseLista.somarCategoria(Integer.parseInt(edtId.getText().toString())) != null){
                    txtTotal.setText(dataBaseLista.somarCategoria(Integer.parseInt(edtId.getText().toString()))+" R$");
                }*/
                break;

            case menuSairT:
                finishAffinity();
                break;

            case action_todas_listas:
                startActivity(new Intent(this, Add_RelacaoLisTare.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                break;

            case action_homeT:

                if (interstitial_HomeListTarefa != null) {
                    interstitial_HomeListTarefa.show(List_t.this);

                    interstitial_HomeListTarefa.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(List_t.this, MenuInicial.class));
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

    /*private void configurarRecycler() {

        listItemCompras = (RecyclerView) findViewById(R.id.listItemCompras);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listItemCompras.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        buscaTarefa_Adpter();

        // Configurando um separador entre linhas, para uma melhor visualização.
        listItemCompras.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/
    //todas as listas
    public void addNovaListTarefa(View view) {
        if (validaCamposTarAdd() == false) try {

            if (list_DeL_Tarefa.ID_T == 0) {
                criarConexaoT();
                criarConexao();
                lista_LtrefaRepo.inserirLista(list_DeL_Tarefa);

                findViewById(telaAddListaDeTare).setVisibility(View.GONE);

                popularSpinnerTare();

            }


            buscaTarefa_Adpter();
            fechaConexao();
            fechaConexaoT();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(null);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    private boolean validaCamposTarAdd() {

        boolean res = false;

        // int ID_T = Integer.parseInt(edtIdTare.getText().toString());
        String LISTATAREFA = edtNovaList_T.getText().toString();
        String DATALISTA = edtDataListTare.getText().toString();

        //list_DeL_Tarefa.ID_T = ID_T;
        list_DeL_Tarefa.LISTATAREFA = LISTATAREFA;
        list_DeL_Tarefa.DATALISTA = DATALISTA;

        if (res = isCampoVazio(LISTATAREFA)) {
            edtNovaList_T.requestFocus();
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

    private void criarConexaoT() {
        try {
            dataBase = new DataBaseLista(this);
            conexao = dataBase.getWritableDatabase();
            lista_LtrefaRepo = new Lista_LtrefaRepo(conexao);

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();

        }
    }

    private void fechaConexaoT() {
        try {
            dataBase = new DataBaseLista(this);
            conexao = dataBase.getWritableDatabase();
            lista_LtrefaRepo = new Lista_LtrefaRepo(conexao);
            dataBase.close();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    public void telaAddNovaList(View view) {
        findViewById(telaAddListaDeTare).setVisibility(View.VISIBLE);
    }

    public void voltarTelaAddNovaList(View view) {
        findViewById(telaAddListaDeTare).setVisibility(View.GONE);
    }

}
