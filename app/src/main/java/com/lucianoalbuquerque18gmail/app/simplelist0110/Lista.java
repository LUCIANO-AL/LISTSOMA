package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter.ItemAdapter;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Item;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Lista_De_List;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaDeListReposi;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaRepositorio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.Add_RelacaoList.edtIdListAdd;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Add_RelacaoList.edtNovaList;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_home;
//import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.compartilhaCompras;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.addListaDeComp;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.del_item_lista;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.desmarca_tudo;
//import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.importCompras;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.edtNomeLista;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.menuSair;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.todasListas;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update_list;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Lista extends AppCompatActivity {

    private EditText edtItemLista, edtPreco, edtQuant, edtId_dd, edtItemLista_Upd, edtPreco_Upd, edtQuant_Upd;
    public static EditText edtId_Upd;
    private EditText edtItemLista_Del, edtPreco_Del, edtQuant_Del, edtNovaList, edtDataList;
    private CheckBox checkB_Del, checkB, checkB_Upd;
    public static TextView txtTotalReais_NoCheck, txtTotalitem_NoCheck, txtTotal_check, txtTotalItem_check, txtIdItemUpdt, txtIDListaUpdt;
    public static TextView txtReaisTodosItens, txtContTodosItens;

    public TextView texto, txtComoExcl_Updt_item;
    private RecyclerView listItemCompras;
    private ConstraintLayout itemContraintLayout;
    private SQLiteDatabase conexao;
    public DataBaseLista dataBaseLista;
    private ListaRepositorio listaRepositorio;
    public ListaDeListReposi listaDeListReposi;
    private Item itemm;
    private ItemAdapter itemAdapter;
    public Lista_De_List lista_de_list;
    private ImageButton buttonAdd, btnDataList, buttonEdit;

    private Button btnEdit_Upd, btnAddNovaLista, butonVoltar_c, butonVoltar_Del, btnDell;

    public Button contagemProgressiva;

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public int contagem_list_compras = 0;

    TextView lbl;
    ListView lv;
    final Context context = this;
    public String ultimoID;
    public static final int requestcode = 1;
    public static InterstitialAd lista_interstitial, interstitial_HomeListCompr;

    public int contadorPropagandaListCompras = 0;

    private Spinner spinnerListasCompr;

    private Button visuTelaNomeLista;

    public String compareValue;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    public String spinneranterior;

    private AdView adViewUpdtListaCompras, adViewAddRelaListCompras, mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lista);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Lista");
        toolbar.setLogo(R.drawable.logo_barra);
        setSupportActionBar(toolbar);

        referenciaXML();

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        adViewUpdtListaCompras.loadAd(adRequest);
        adViewAddRelaListCompras.loadAd(adRequest);

        AdRequest adRequest_intest = new AdRequest.Builder().build();
        /*lista_interstitial = new InterstitialAd(Lista.this);
        lista_interstitial.setAdUnitId(getString(R.string.admob_interstitial_compras));
        lista_interstitial.loadAd(adRequest_intest);*/

        InterstitialAd.load(this, getString(R.string.admob_interstitial_compras), adRequest_intest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        lista_interstitial = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Toast.makeText(Lista.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        lista_interstitial = null;
                    }
                });

        InterstitialAd.load(this, getString(R.string.intersticial_id_home_listcompras), adRequest_intest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitial_HomeListCompr = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                       // Toast.makeText(Lista.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        interstitial_HomeListCompr = null;
                    }
                });

        findViewById(update_list).setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        criarConexao();

        ArrayAdapter<String> adapter_spinner_nomes_listascomp = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, dataBaseLista.buscarNomeListComprasSippner());
        spinnerListasCompr.setAdapter(adapter_spinner_nomes_listascomp);

        spinnerListasCompr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                criarConexao();
                edtId_Upd.setText(dataBaseLista.buscarIDListCompras(spinnerListasCompr.getSelectedItem().toString()));

                toolbar.setTitle(spinnerListasCompr.getSelectedItem().toString());

               // verificarPosicaoSpinner();

                BuscarAdapter();
                funcoesTotaisCheckNoCheck();
                fechaConexao();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        verificaParametroRelaList();
        verificaParametro_upt();
        //verificaParametro();

        listItemCompras.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listItemCompras.setLayoutManager(linearLayoutManager);

        listaRepositorio = new ListaRepositorio(conexao);

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //contarPropaganda();

                if (validaCampos() == false && validaCamposZero() == false) try {

                    if (itemm.id_item >= 0) {

                        criarConexao();
                        listaRepositorio.inserir(itemm);
                        itemAdapter.adicionarCliente(itemm);

                        edtItemLista.setText("");
                        edtPreco.setText("");
                        edtQuant.setText("");
                        edtItemLista.requestFocus();

                    }

                    BuscarAdapter();
                    funcoesTotaisCheckNoCheck();
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

        btnEdit_Upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaCampos_Upt() == false && validaCampos_Upt_Zero() == false) try {

                    if (itemm.id_item > 0) {

                        criarConexao();
                        listaRepositorio.alterar(itemm);

                        BuscarAdapter();

                        compareValue = dataBaseLista.buscarNomeListCompras(txtIDListaUpdt.getText().toString());

                        verificarPosicaoSpinner();

                        // itemAdapter.atualizarItem(itemm);
                        // edtItemLista.setText("");
                        //edtPreco.setText("");
                        //edtQuant.setText("");
                        //edtItemLista.requestFocus();


                    }



                    //funcTotal();
                    //funcTotalItem();

                    findViewById(update_list).setVisibility(View.INVISIBLE);
                    funcoesTotaisCheckNoCheck();
                    fechaConexao();

                    //  atualizartela();

                    //  finish();

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtItemLista_Upd.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtPreco_Upd.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtQuant_Upd.getWindowToken(), 0);

                } catch (SQLException ex) {

                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
                    dlg.show();
                }
            }
        });

        butonVoltar_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(update_list).setVisibility(View.INVISIBLE);
                //  finish();
            }
        });

        butonVoltar_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCampos_Del() == false)
                    try {

                        if (itemm.id_item > 0) {

                            findViewById(del_item_lista).setVisibility(View.INVISIBLE);
                            //finish();
                        }

                        //BuscarAdapter();

                    } catch (SQLException ex) {

                        AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                        dlg.setTitle("Erro");
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
                        dlg.show();
                    }
            }
        });

        btnDell.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (validaCampos_Del() == false) try {

                    if (itemm.id_item > 0) {

                        criarConexao();
                        dataBaseLista.excluirIt(itemm.id_item);
                        // edtItemLista.setText("");
                        //edtPreco.setText("");
                        //edtQuant.setText("");
                        //edtItemLista.requestFocus();
                        funcoesTotaisCheckNoCheck();
                        BuscarAdapter();
                        fechaConexao();
                    }


                    findViewById(del_item_lista).setVisibility(View.INVISIBLE);
                    //finish();


                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtItemLista_Del.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtPreco_Del.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtQuant_Del.getWindowToken(), 0);

                } catch (SQLException ex) {

                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
                    dlg.show();
                }
            }


        });

        btnAddNovaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validacaoAddNovaLista() == false) try {

                    if (lista_de_list.ID >= 0) {

                        criarConexaoNomeList();
                        criarConexao();
                        listaDeListReposi.inserirLista(lista_de_list);

                        //edtIdListAdd.setText(dataBaseLista.idIgualUm());

                        findViewById(addListaDeComp).setVisibility(View.GONE);

                        ArrayAdapter<String> adapter_spinner_nomes_listascomp = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, dataBaseLista.buscarNomeListComprasSippner());
                        spinnerListasCompr.setAdapter(adapter_spinner_nomes_listascomp);
                    }

                    BuscarAdapter();
                    fechaConexaoNomeList();
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        String currentDateTimeString = dateFormat.format(new Date());
        edtDataList.setText(currentDateTimeString);

        btnDataList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Lista.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataList.setText(mDia + "/" + (mMes + 1) + "/" + mAno);
                        //edtDataList.setText((mMes + 1) + "/" + mAno);
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        edtDataList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(Lista.this, 0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            edtDataList.setText(mDia + "/" + (mMes + 1) + "/" + mAno);
                            //edtDataList.setText((mMes + 1) + "/" + mAno);
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();
                }
            }
        });


        //edtId_Upd.setText(dataBaseLista.buscarIDListCompras(spinnerListasCompr.getSelectedItem().toString()));

        //System.out.println("TEste "+ spinnerListasCompr.getSelectedItem().toString());

        //verificaParametro_upt();
        BuscarAdapter();
        funcoesTotaisCheckNoCheck();
        //configurarRecycler();
        fechaConexao();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                txtComoExcl_Updt_item.setVisibility(View.GONE);

            }
        }, 20000);
    }

    public void verificarPosicaoSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, dataBaseLista.buscarNomeListComprasSippner());
        spinnerListasCompr.setAdapter(adapter);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.preloaded_fonts, android.R.layout.simple_list_item_1);

        //adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerListasCompr.setAdapter(adapter);

        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            spinnerListasCompr.setSelection(spinnerPosition);
            //spinnerPosition = 0;
        }
    }

    public void criarNovaListaComp(View v) {

        if (validacaoAddNovaLista() == false) try {

            if (lista_de_list.ID >= 0) {

                criarConexao();
                listaDeListReposi.inserirLista(lista_de_list);

                edtIdListAdd.setText(dataBaseLista.idIgualUm());

                findViewById(addListaDeComp).setVisibility(View.GONE);
            }

            BuscarAdapter();
            fechaConexao();

        } catch (SQLException ex) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(null);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    public void volta(View view) {
        findViewById(addListaDeComp).setVisibility(View.GONE);
    }

    public void telaAddNovaList(View view) {
        findViewById(addListaDeComp).setVisibility(View.VISIBLE);
    }

    public void contarPropaganda() {

        contadorPropagandaListCompras++;

        if (contadorPropagandaListCompras == 2) {

            if (lista_interstitial != null) {

                if (lista_interstitial != null) {
                    lista_interstitial.show(Lista.this);
                } else {
                   // Toast.makeText(Lista.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }

    public void referenciaXML() {

        mAdView = (AdView) findViewById(R.id.adView);
        adViewUpdtListaCompras = (AdView) findViewById(R.id.adViewUpdtListaCompras);
        adViewAddRelaListCompras = (AdView) findViewById(R.id.adViewAddRelaListCompras);

        edtItemLista = (EditText) findViewById(R.id.edtItemLista);
        edtPreco = (EditText) findViewById(R.id.edtPreco);
        edtQuant = (EditText) findViewById(R.id.edtQuant);
        edtItemLista_Upd = (EditText) findViewById(R.id.edtItemLista_Upd);
        edtPreco_Upd = (EditText) findViewById(R.id.edtPreco_Upd);
        edtQuant_Upd = (EditText) findViewById(R.id.edtQuant_Upd);
        edtItemLista_Del = (EditText) findViewById(R.id.edtItemLista_Del);
        edtPreco_Del = (EditText) findViewById(R.id.edtPreco_Del);
        edtQuant_Del = (EditText) findViewById(R.id.edtQuant_Del);
        edtId_dd = (EditText) findViewById(R.id.edtId_dd);
        edtId_Upd = (EditText) findViewById(R.id.edtId_Upd);
        edtNovaList = (EditText) findViewById(R.id.edtNovaList);
        edtDataList = (EditText) findViewById(R.id.edtDataList);

        checkB = (CheckBox) findViewById(R.id.checkB);
        checkB_Upd = (CheckBox) findViewById(R.id.checkB_Upd);
        checkB_Del = (CheckBox) findViewById(R.id.checkB_Del);

        buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);
        btnDataList = (ImageButton) findViewById(R.id.btnDataList);

        btnEdit_Upd = (Button) findViewById(R.id.btnEdit_Upd);
        btnDell = (Button) findViewById(R.id.btnDell);
        butonVoltar_Del = (Button) findViewById(R.id.butonVoltar_Del);
        butonVoltar_c = (Button) findViewById(R.id.butonVoltar_c);
        btnAddNovaLista = (Button) findViewById(R.id.btnAddNovaLista);
        visuTelaNomeLista = (Button) findViewById(R.id.visuTelaNomeLista);

        txtTotalReais_NoCheck = (TextView) findViewById(R.id.txtTotalReais_NoCheck);
        txtTotalitem_NoCheck = (TextView) findViewById(R.id.txtTotalitem_NoCheck);
        txtTotal_check = (TextView) findViewById(R.id.txtTotal_check);
        txtTotalItem_check = (TextView) findViewById(R.id.txtTotalitem_check);
        txtComoExcl_Updt_item = (TextView) findViewById(R.id.txtComoExcl_Updt_item);
        txtReaisTodosItens = (TextView) findViewById(R.id.txtReaisTodosItens);
        txtContTodosItens = (TextView) findViewById(R.id.txtContTodosItens);
        txtIdItemUpdt = (TextView) findViewById(R.id.txtIdItemUpdt);
        txtIDListaUpdt = (TextView) findViewById(R.id.txtIDListaUpdt);

        listItemCompras = (RecyclerView) findViewById(R.id.listItemCompras);

        itemContraintLayout = (ConstraintLayout) findViewById(R.id.itemContraintLayout);

        spinnerListasCompr = (Spinner) findViewById(R.id.spinnerListasCompr);



    }

    /*public void buttonClick_Conta() {

        texto = (TextView)findViewById(R.id.texto);
        contagemProgressiva = (Button) findViewById(R.id.contagemProgressiva);

        // contagem = preferences.getInt("contador", 0);
        texto.setText("Contagem atual:"+ contagem_list_compras);

        contagemProgressiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contagem_list_compras >= 0){
                    contagem_list_compras = contagem_list_compras - contagem_list_compras;
                }
                contagem_list_compras++;
                texto.setText("Contagem atual: " + contagem_list_compras);
                editor.putInt("contador", contagem_list_compras);
                editor.commit();
            }
        });
    }*/

    public void BuscarAdapter() {
        List<Item> dados = listaRepositorio.buscarTodos(Integer.parseInt(edtId_Upd.getText().toString()));
        itemAdapter = new ItemAdapter(this, dados);
        listItemCompras.setAdapter(itemAdapter);
    }

    public void funcTotalItem_NoCheck() {
        dataBaseLista = new DataBaseLista(this);
        if (dataBaseLista.totalItem_NoCheck(Integer.parseInt(edtId_Upd.getText().toString())) != null) {
            txtTotalitem_NoCheck.setText("(" + dataBaseLista.totalItem_NoCheck(Integer.parseInt(edtId_Upd.getText().toString())) + ")");
        } else {
            txtTotalitem_NoCheck.setText("(0)");
        }

    }

    public void funcTotalReais_NoCheck() {
        dataBaseLista = new DataBaseLista(this);
        if (dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())) == null) {
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReais_NoCheck.setText("R$0.00");
        } else {
            txtTotalReais_NoCheck.setText("R$" + dataBaseLista.totalReais_NoChek(Double.parseDouble(edtId_Upd.getText().toString())));
        }

    }

    public void funcTotalItem_check() {
        DataBaseLista dataBaseLista = new DataBaseLista(this);
        if (dataBaseLista.totalItem_check(Integer.parseInt(edtId_Upd.getText().toString())) != null) {
            txtTotalItem_check.setText("(" + dataBaseLista.totalItem_check(Integer.parseInt(edtId_Upd.getText().toString())) + ")");
        } else {
            txtTotalItem_check.setText("(0)");
        }

    }

    public void funcTotal_check() {
        DataBaseLista dataBaseLista = new DataBaseLista(this);
        if (dataBaseLista.somarCategoria2_check(Integer.parseInt(edtId_Upd.getText().toString())) == null) {
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotal_check.setText("R$0.00");
        } else {
            txtTotal_check.setText("R$" + dataBaseLista.totalReais_check(Double.parseDouble(edtId_Upd.getText().toString())));
        }
    }

    public void funcTotalItem() {
        DataBaseLista dataBaseLista = new DataBaseLista(this);
        if (dataBaseLista.totalItens(Integer.parseInt(edtId_Upd.getText().toString())) != null) {
            txtContTodosItens.setText("(" + dataBaseLista.totalItens(Integer.parseInt(edtId_Upd.getText().toString())) + ")");
        } else {
            txtContTodosItens.setText("(0)");
        }

    }

    public void funcTotalReais() {
        DataBaseLista dataBaseLista = new DataBaseLista(this);
        if (dataBaseLista.totalReais(Integer.parseInt(edtId_Upd.getText().toString())) == null) {
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtReaisTodosItens.setText("R$0.00");
        } else {
            txtReaisTodosItens.setText("R$" + dataBaseLista.totalReais(Double.parseDouble(edtId_Upd.getText().toString())));
        }
    }

    public void funcoesTotaisCheckNoCheck() {
        funcTotalItem_NoCheck();
        funcTotalReais_NoCheck();
        funcTotalItem_check();
        funcTotal_check();
        funcTotalItem();
        funcTotalReais();
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Add_RelacaoList.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    public void verificaParametro_upt() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();

        itemm = new Item();

        if ((bundle != null) && (bundle.containsKey("ITEM"))) {

            findViewById(update_list).setVisibility(View.VISIBLE);

            itemm = (Item) bundle.getSerializable("ITEM");

            txtIdItemUpdt.setText(String.valueOf(itemm.id_item));
            edtItemLista_Upd.setText(itemm.Nome);
            edtPreco_Upd.setText(String.valueOf(itemm.preco));
            edtQuant_Upd.setText(itemm.quant);
            checkB_Upd.setChecked(itemm.check);
            //itemm.check = checkB.isChecked();
            txtIDListaUpdt.setText(String.valueOf(itemm.ID));

            toolbar.setTitle(dataBaseLista.select_NomeListaDeCompras(Integer.parseInt(edtId_Upd.getText().toString())));

            compareValue = dataBaseLista.buscarNomeListCompras(txtIDListaUpdt.getText().toString());

            verificarPosicaoSpinner();

        } else if ((bundle != null) && (bundle.containsKey("ITEM_DEL"))) {

            findViewById(del_item_lista).setVisibility(View.VISIBLE);

            System.out.println("Teste2 " + spinnerListasCompr.getSelectedItem().toString());

            itemm = (Item) bundle.getSerializable("ITEM_DEL");

            edtItemLista_Del.setText(itemm.Nome);
            edtPreco_Del.setText(String.valueOf(itemm.preco));
            edtQuant_Del.setText(itemm.quant);
            checkB_Del.setChecked(itemm.check);
            //itemm.check = checkB.isChecked();
            txtIDListaUpdt.setText(String.valueOf(itemm.ID));

            toolbar.setTitle(dataBaseLista.select_NomeListaDeCompras(Integer.parseInt(edtId_Upd.getText().toString())));

            compareValue = dataBaseLista.buscarNomeListCompras(txtIDListaUpdt.getText().toString());

            verificarPosicaoSpinner();

        }

    }

    public void verificaParametroRelaList() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();

        lista_de_list = new Lista_De_List();

        if ((bundle != null) && (bundle.containsKey("LISTA_DE_LIST"))) {

            lista_de_list = (Lista_De_List) bundle.getSerializable("LISTA_DE_LIST");

            edtId_Upd.setText(String.valueOf(lista_de_list.ID));
            toolbar.setTitle(lista_de_list.NOMELISTA);

            compareValue = lista_de_list.NOMELISTA;

            verificarPosicaoSpinner();

        } /*else {
            edtId_Upd.setText(edtIdListAdd.getText().toString());
            //String nome = getIntent().getStringExtra ("listnome");
            toolbar.setTitle(" Lista " + edtNovaList.getText().toString());
            compareValue = edtNovaList.getText().toString();

        }*/ else {

            if (dataBaseLista.idIgualUm() == "0") {

                findViewById(addListaDeComp).setVisibility(View.VISIBLE);
                edtId_Upd.setText("0");


            } else {
                edtId_Upd.setText(dataBaseLista.buscarIDListCompras(spinnerListasCompr.getSelectedItem().toString()));

            }

        }

    }

    private void criarConexao() {

        try {

            dataBaseLista = new DataBaseLista(this);
            conexao = dataBaseLista.getWritableDatabase();
            // Snackbar.make(itemContraintLayout, R.string.message_conexao_criada_sucesso, Snackbar.LENGTH_LONG)
            //        .setAction (getString(R.string.text_action_ok_conexao), null).show();
            listaRepositorio = new ListaRepositorio(conexao);

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
            conexao = dataBaseLista.getWritableDatabase();
            listaRepositorio = new ListaRepositorio(conexao);
            dataBaseLista.close();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    private void criarConexaoNomeList() {

        try {

            dataBaseLista = new DataBaseLista(this);
            conexao = dataBaseLista.getWritableDatabase();
            listaDeListReposi = new ListaDeListReposi(conexao);

        } catch (SQLException ex) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();

        }
    }

    private void fechaConexaoNomeList() {
        try {
            dataBaseLista = new DataBaseLista(this);
            conexao = dataBaseLista.getWritableDatabase();
            listaDeListReposi = new ListaDeListReposi(conexao);
            dataBaseLista.close();

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

        String Nome = edtItemLista.getText().toString();
        String preco = edtPreco.getText().toString();
        String quant = edtQuant.getText().toString();
        int ID = Integer.parseInt(edtId_Upd.getText().toString());
        boolean check = checkB.isChecked();

        itemm.Nome = Nome;
        itemm.preco = preco;
        itemm.quant = quant;
        itemm.ID = ID;
        itemm.check = check;

        if (res = isCampoVazio(Nome)) {
            edtItemLista.requestFocus();
        }/*else
           // if (res = isCampoVazio(preco)){
             //   edtPreco.requestFocus();
            //} else
           // if(res = isCampoVazio(quant)){
             //   edtQuant.requestFocus();
            //}*/

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.message_campos_invalidos);
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }

    private boolean validaCamposZero() {

        boolean res = false;


        String Nome = edtItemLista.getText().toString();
        String preco = edtPreco.getText().toString();
        String quant = edtQuant.getText().toString();
        int ID = Integer.parseInt(edtId_Upd.getText().toString());
        boolean check = checkB.isChecked();

        itemm.Nome = Nome;
        itemm.preco = preco;
        itemm.quant = quant;
        itemm.ID = ID;
        itemm.check = check;

        /*if(res = isCampoVazio(Nome)){
            edtItemLista.requestFocus();
        }else*/
        if (res = isCampoVazio(preco)) {
            edtPreco.requestFocus();
            edtPreco.setText("0");
        } else if (res = isCampoVazio(quant)) {
            edtQuant.requestFocus();
            edtQuant.setText("1");
        }

        if (res) {
            buttonClick_Add();
            buttonAdd.performClick();
        }
        return res;
    }

    private boolean validacaoAddNovaLista() {
        boolean res = false;

        lista_de_list = new Lista_De_List();

        // int ID = Integer.parseInt(edtId.getText().toString());
        String NOMELISTA = edtNovaList.getText().toString();
        String DATALISTA = edtDataList.getText().toString();


        //lista_de_list.ID = ID;
        lista_de_list.NOMELISTA = NOMELISTA;
        lista_de_list.DATALISTA = DATALISTA;

        if (res = isCampoVazio(NOMELISTA)) {
            edtNovaList.requestFocus();
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

    public void buttonClick_Add() {
        buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCampos() == false && validaCamposZero() == false) try {

                    if (itemm.id_item >= 0) {
                        criarConexao();
                        listaRepositorio.inserir(itemm);
                        itemAdapter.adicionarCliente(itemm);

                        edtItemLista.setText("");
                        edtPreco.setText("");
                        edtQuant.setText("");
                        edtItemLista.requestFocus();

                    }

                    BuscarAdapter();

                    funcoesTotaisCheckNoCheck();
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
    }

    private boolean validaCampos_Upt() {

        boolean res = false;

        String Nome = edtItemLista_Upd.getText().toString();
        String preco = edtPreco_Upd.getText().toString();
        String quant = edtQuant_Upd.getText().toString();
        int ID = Integer.parseInt(txtIDListaUpdt.getText().toString());
        boolean check = checkB_Upd.isChecked();

        itemm.Nome = Nome;
        itemm.preco = preco;
        itemm.quant = quant;
        itemm.ID = ID;
        itemm.check = check;

        if (res = isCampoVazio(Nome)) {
            //edtItemLista.requestFocus();
        }/*else
                // if (res = isCampoVazio(preco)){
                //   edtPreco.requestFocus();
                //} else
                // if(res = isCampoVazio(quant)){
                //   edtQuant.requestFocus();
                //}*/

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.message_campos_invalidos);
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }

    private boolean validaCampos_Upt_Zero() {

        boolean res = false;

        String Nome = edtItemLista_Upd.getText().toString();
        String preco = edtPreco_Upd.getText().toString();
        String quant = edtQuant_Upd.getText().toString();
        int ID = Integer.parseInt(txtIDListaUpdt.getText().toString());
        boolean check = checkB_Upd.isChecked();

        itemm.Nome = Nome;
        itemm.preco = preco;
        itemm.quant = quant;
        itemm.ID = ID;
        itemm.check = check;

         /*if(res = isCampoVazio(Nome)){
            edtItemLista.requestFocus();
        }else*/
        if (res = isCampoVazio(preco)) {
            edtPreco_Upd.requestFocus();
            edtPreco_Upd.setText("0");
        } else if (res = isCampoVazio(quant)) {
            edtQuant_Upd.requestFocus();
            edtQuant_Upd.setText("1");
        }

        if (res) {

            buttonClick_Upt();
            btnEdit_Upd.performClick();
        }
        return res;
    }

    public void buttonClick_Upt() {
        btnEdit_Upd = (Button) findViewById(R.id.btnEdit_Upd);
        btnEdit_Upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCampos_Upt() == false && validaCampos_Upt_Zero() == false) try {

                    if (itemm.id_item > 0) {

                        listaRepositorio.alterar(itemm);
                        // edtItemLista.setText("");
                        //edtPreco.setText("");
                        //edtQuant.setText("");
                        //edtItemLista.requestFocus();

                    }

                    BuscarAdapter();

                    funcoesTotaisCheckNoCheck();

                    findViewById(update_list).setVisibility(View.INVISIBLE);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtItemLista_Upd.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtPreco_Upd.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtQuant_Upd.getWindowToken(), 0);

                } catch (SQLException ex) {

                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
                    dlg.show();
                }

            }
        });
    }

    private boolean validaCampos_Del() {

        boolean res = false;

        String Nome = edtItemLista_Del.getText().toString();
        String preco = edtPreco_Del.getText().toString();
        String quant = edtQuant_Del.getText().toString();
        int ID = Integer.parseInt(edtId_Upd.getText().toString());
        boolean check = checkB_Del.isChecked();

        itemm.Nome = Nome;
        itemm.preco = preco;
        itemm.quant = quant;
        itemm.ID = ID;
        itemm.check = check;

        if (res = isCampoVazio(Nome)) {
            //edtItemLista.requestFocus();
        }/*else
                // if (res = isCampoVazio(preco)){
                //   edtPreco.requestFocus();
                //} else
                // if(res = isCampoVazio(quant)){
                //   edtQuant.requestFocus();
                //}*/

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.message_campos_invalidos);
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
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:

                if (interstitial_HomeListCompr != null) {
                    interstitial_HomeListCompr.show(Lista.this);

                    interstitial_HomeListCompr.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(Lista.this, MenuInicial.class));
                        }
                    });
                } else {
                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }

                break;

            case R.id.action_excluir_tudo:

                dataBaseLista = new DataBaseLista(this);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.itens_title_excluirtudo)
                        .setMessage(R.string.item_message_extudo)
                        .setPositiveButton(R.string.button_item_sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        criarConexao();
                                        listaRepositorio.excluirTdosItens(Integer.parseInt(edtId_Upd.getText().toString()));

                                        edtItemLista.setText("");
                                        edtPreco.setText("");
                                        edtQuant.setText("");
                                        edtItemLista.requestFocus();

                                        BuscarAdapter();

                                    }
                                })
                        .setNegativeButton(R.string.button_item_exctudo_nao, null).show();

                funcoesTotaisCheckNoCheck();
                fechaConexao();
                break;

            case menuSair:
                finishAffinity();
                break;

            case desmarca_tudo:
                criarConexao();
                dataBaseLista.nocheckMultplos(Integer.parseInt(edtId_Upd.getText().toString()));
                BuscarAdapter();
                fechaConexao();

                break;
            case action_home:

                if (interstitial_HomeListCompr != null) {
                    interstitial_HomeListCompr.show(Lista.this);

                    interstitial_HomeListCompr.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(Lista.this, MenuInicial.class));
                        }
                    });
                } else {
                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }

                break;

            case todasListas:
                startActivity(new Intent(this, Add_RelacaoList.class));
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
        BuscarAdapter();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listItemCompras.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/

}
