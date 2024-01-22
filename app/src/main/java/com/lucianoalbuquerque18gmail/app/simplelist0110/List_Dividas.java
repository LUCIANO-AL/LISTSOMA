package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;

import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter.AdapterDividas;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Divida;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.arquivos.ExcelListDividas;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Repo_Divida;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.text.format.DateFormat;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_homeT;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.importarDividasBkp;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.menuSairT;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.princi_div;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.salvarDividasBkp;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update_divida;

public class List_Dividas extends AppCompatActivity {

    private EditText edtNovaList_Div, edtNovaDivParc, edtNomeCredorDiv;
    private EditText edtValor_Div;
    private EditText edtDataVenc_Div, edtParcelasDiv, edtDataMes;
    private EditText edtDataMutiplicada, edtDataDia, edtDataAno;
    private EditText edtDataDiv;
    public static EditText edtId_Div;
    public  EditText edtId_Div2;
    public static EditText edtVencInicial, edtVencFinal;
    public static EditText txtDataInicial, txtDataFinal ;
    public int numparcela = 1;
    public int Mes1;

    private TextView textEdit_Div;
    private TextView textAdd_Div;
    public static TextView txtContAberto;
    public static TextView txtTotalAberto;
    public static TextView txtContPG;
    public static TextView txtTotalPG;
    public TextView texto_div;
    public TextView txtUpdtDiv;
    public static TextView txtTotalCont;
    public static TextView txtTotalReais;

    private ImageButton btnDataVenc_Div;
    private ImageButton btnDataDiv;
    private ImageButton buttonAdd_Div;
    private ImageButton btnDataVencInicial, btnDataVencFinal;

    public static RecyclerView listDividas;

    private ConstraintLayout dividaContraintLayout;
    private ConstraintLayout constraintSpinner, constraintSpinner2;
    private ConstraintLayout constraintInicial, constraintFinal, constraintSpinnerTodasDiv;

    private Button butonVoltar_Div;
    private Button btnEdit_Div;
    private Button btnSalvar_Div, btnSalvarParc;
    public Button contagemProgressiva;

    private SQLiteDatabase conexao;
    public DataBaseLista dataBase = new DataBaseLista(this);
    private Repo_Divida repo_divida;
    private Divida divida;
    private AdapterDividas adapterDividas;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    public SharedPreferences preferences_div;
    public SharedPreferences.Editor editor_div;

    public int contagem_div = 0;

    public InterstitialAd div_interstitial, lista_interstitial, lista_interstitial_SalvarArq;

    public LinearLayout lineraLParc;

    public ExcelListDividas excelListDividas;

    public static Spinner spinnerNomeDoCredorPorData, spinnerNomeCredorTodos;
    public static ArrayAdapter<String> adapterspinnerNomeCredorPorData, adapterspinnerNomeCredorTodos;

    public static CheckBox checkFiltrarPorCredor, checkDivRecTodos, checkSpinneTodasPorCredor, checkSpinnePorCredor;
    private CheckBox checkB_DiV;

    public static RadioButton radioPorData, radioAbertoPorData, radioPagoPorData;
    public static RadioButton radioTodosCredor, radioAbertoCredor, radioPagoCredor;
    public static RadioButton radioTodos, radioAbertoTodos, radioPagoTodos;
    public static RadioButton radioTodoPorCredor, radioAbertoTodosPorCredor, radioPagoTodosPorCredor;
    private RadioGroup radioGroupPorData, radioGroupPorCredor, radioGroupTodos, radioGroupTodosPorCredor;

    public int contadorPropagandaDivAPagar = 0;

    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list__dividas);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" Dividas a pagar");
        toolbar.setLogo(R.drawable.logo_barra_div);
        setSupportActionBar(toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView_Div = (AdView) findViewById(R.id.adView_Div);
        AdView adViewListaDividas = (AdView) findViewById(R.id.adViewListaDividas);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_Div.loadAd(adRequest);
        adViewListaDividas.loadAd(adRequest);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        AdRequest adRequest_div = new AdRequest.Builder().build();
       /* div_interstitial = new InterstitialAd(List_Dividas.this);
        div_interstitial.setAdUnit(getString(R.string.admob_interstitial_div));
        div_interstitial.loadAd(adRequest_div);*/

        InterstitialAd.load(this,getString(R.string.admob_interstitial_div), adRequest_div,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        div_interstitial = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        div_interstitial = null;
                    }
                });

        AdRequest adRequest_intest = new AdRequest.Builder().build();
        /*lista_interstitial = new InterstitialAd(List_Dividas.this);
        lista_interstitial.setAdUnitId(getString(R.string.admob_interstitial_compras));
        lista_interstitial.loadAd(adRequest_intest);*/

        InterstitialAd.load(this,getString(R.string.admob_interstitial_compras), adRequest_intest,
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
                        //Toast.makeText(List_Dividas.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        lista_interstitial = null;
                    }
                });

        InterstitialAd.load(this,getString(R.string.admob_interstitial_tarefa), adRequest_intest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        lista_interstitial_SalvarArq = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                       // Toast.makeText(List_Dividas.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        lista_interstitial_SalvarArq = null;
                    }
                });


        findViewById(update_divida).setVisibility(View.INVISIBLE);

        preferences_div = getSharedPreferences("Toque3", MODE_PRIVATE);
        contagem_div = preferences_div.getInt("contador3", 0);
        editor_div = preferences_div.edit();

        txtUpdtDiv = (TextView) findViewById(R.id.txtUpdtDiv);

        buttonClick_Conta();
        contagemProgressiva.performClick();

        referenciasXML();
        criarConexao();
        //verificaParametroRelaList();
        trazerUpdate();
        // verificaParametro();

        listDividas.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(linearLayoutManager);

        repo_divida = new Repo_Divida(conexao);

        btnDataVenc_Div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtDataVenc_Div.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtDataVenc_Div.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataVenc_Div.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                        edtDataDia.setText(String.format("%02d", mDia));
                        edtDataMes.setText(String.format("%02d", mMes+1));
                        edtDataAno.setText(Integer.toString(mAno));
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        btnDataDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtDataDiv.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtDataDiv.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataDiv.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        edtDataVenc_Div.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtDataVenc_Div.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtDataVenc_Div.setTextIsSelectable(true);
                    }

                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //edtDataVenc_Div.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtDataVenc_Div.setText( String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                            edtDataDia.setText(String.format("%02d", mDia));
                            edtDataMes.setText(String.format("%02d", mMes+1));
                            edtDataAno.setText(Integer.toString(mAno));
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();

                }
            }
        });

        edtDataDiv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtDataDiv.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtDataDiv.setTextIsSelectable(true);
                    }

                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //   edtDataListDiv_D.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtDataDiv.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();
                }
            }
        });

        edtVencInicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus ) {
                if (hasFocus) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtVencInicial.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtVencInicial.setTextIsSelectable(true);
                    }
                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //   edtDataListDiv_D.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtVencInicial.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                            try {
                                txtDataInicial.setText(converteStringEmData(edtVencInicial.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            edtVencFinal.requestFocus();

                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();

                }

            }
        });

        edtVencFinal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus ) {
                if (hasFocus) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtVencFinal.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtVencFinal.setTextIsSelectable(true);
                    }

                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //   edtDataListDiv_D.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtVencFinal.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                            try {
                                txtDataFinal.setText(converteStringEmData(edtVencFinal.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            criarConexao();
                            filtrarDividaPorData();
                            //configurarRecycler();

                            funcoesContReaisPorData();
                            fechaConexao();

                            adapterspinnerNomeCredorPorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                    dataBase.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
                            spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorPorData);

                            spinnerNomeDoCredorPorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    criarConexao();
                                    buscaDivida_AdpterSpiner();
                                    //configurarRecyclerFiltro();

                                    funcoesContReaisPorDataCredor();

                                    fechaConexao();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();

                }

            }
        });

        btnDataVencInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtVencInicial.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtVencInicial.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtVencInicial.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                        try {
                            txtDataInicial.setText(converteStringEmData(edtVencInicial.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        edtVencFinal.requestFocus();
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        btnDataVencFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtVencFinal.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtVencFinal.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(List_Dividas.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtVencFinal.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                        try {
                            txtDataFinal.setText(converteStringEmData(edtVencFinal.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        criarConexao();
                        filtrarDividaPorData();
                        //configurarRecycler();

                        funcoesContReaisPorData();
                        fechaConexao();

                        adapterspinnerNomeCredorPorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                dataBase.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
                        spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorPorData);

                        spinnerNomeDoCredorPorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                criarConexao();
                                buscaDivida_AdpterSpiner();
                                //configurarRecyclerFiltro();

                                funcoesContReaisPorDataCredor();
                                fechaConexao();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        btnSalvar_Div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //contarPropaganda();

                if(Integer.parseInt(edtParcelasDiv.getText().toString())<=100){
                    if (validaCampos() == false) try {

                        if (divida.ID >= 0) {
                            criarConexao();
                            repo_divida.inserir(divida);
                            adapterDividas.adicionarCliente(divida);

                            calendar = Calendar.getInstance();
                            int dia1 = Integer.parseInt((edtDataDia.getText().toString()));
                            int mes1 = Integer.parseInt((edtDataMes.getText().toString()));
                            int mes2 = Integer.parseInt((edtDataMes.getText().toString()));
                            int mes3 = Integer.parseInt((edtDataMes.getText().toString()));
                            int ano1 = Integer.parseInt((edtDataAno.getText().toString()));
                            // edtNovaDivParc.setText(edtNovaList_Div.getText().toString());

                            for(int parcelas = Integer.parseInt(edtParcelasDiv.getText().toString()); parcelas > 1; parcelas--){


                                edtNovaDivParc.setText(edtNovaList_Div.getText().toString() + " Parcela "+(++numparcela));
                                Mes1 = ++mes1;

                                if (Mes1<=12){
                                    edtDataMutiplicada.setText(String.format("%02d", dia1) + "/" + String.format("%02d", Mes1) + "/" + ano1);
                                }

                                if (Mes1>12){
                                    mes1 = mes1-12;
                                    ano1=ano1+1;
                                    edtDataMutiplicada.setText(String.format("%02d", dia1) + "/" + String.format("%02d", mes1) + "/" + ano1);

                                }

                                ClickAltomatico_AddDivida();
                                btnSalvarParc.performClick();
                            }

                            numparcela = (numparcela-numparcela) +1;

                            edtNovaList_Div.setText("");
                            edtValor_Div.setText("");
                            edtDataVenc_Div.setText("");
                            edtDataDiv.setText("");
                            edtDataDia.setText("");
                            edtDataMes.setText("");
                            edtDataMutiplicada.setText("");
                            edtParcelasDiv.setText("01");
                            edtNomeCredorDiv.setText("");

                            findViewById(update_divida).setVisibility(View.GONE);
                            findViewById(princi_div).setVisibility(View.VISIBLE);

                        }

                        todasBuscas();

                        // filtrarDividaPorData();
                        //buscaDivida_Adpter();
                        funcoesContReaisPorData();
                        fechaConexao();


                       /* if (checkSpinnePorCredor.isChecked()){
                            adapterspinnerNomeCredorPorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                    dataBase.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
                            spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorPorData);

                            spinnerNomeDoCredorPorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    criarConexao();
                                    buscaDivida_AdpterSpiner();

                                    funcoesContReaisPorDataCredor();
                                    fechaConexao();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else
                        if (checkSpinneTodasPorCredor.isChecked()){
                            adapterspinnerNomeCredorTodos = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                    dataBase.buscarNomeCredorSppinerTodos());
                            spinnerNomeCredorTodos.setAdapter(adapterspinnerNomeCredorTodos);

                            spinnerNomeCredorTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    criarConexao();
                                    buscaDividaAdpterTodasPorCredor();
                                    configurarRecyclerTodosPorCredor();

                                    funcoesContReaisSpinnerTodosPorCredor();
                                    fechaConexao();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }*/


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
                } else {
                    Toast.makeText (List_Dividas.this, "100 é o número máximo de parcelas permitidas ", Toast.LENGTH_LONG).show();

                }


            }
        });

        btnEdit_Div.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validaCampos() == false) try {

                    if (divida.ID > 0) {
                        criarConexao();
                        repo_divida.alterar(divida);

                        edtNovaList_Div.setText("");
                        edtValor_Div.setText("");
                        edtDataVenc_Div.setText("");
                        edtDataDiv.setText("");
                        edtNomeCredorDiv.setText("");

                        //edtTarefa.setText("");
                        //edtPriori.setSelection(0);
                        //edtTarefa.requestFocus();
                    }

                    //buscaDivida_Adpter();
                    //filtrarDividaPorData();
                    todasBuscas();

                    findViewById(update_divida).setVisibility(View.GONE);
                    findViewById(princi_div).setVisibility(View.VISIBLE);

                    funcoesContReaisPorData();
                    fechaConexao();
                    //finish();

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtNovaList_Div.getWindowToken(), 0);

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

        butonVoltar_Div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtNovaList_Div.setText("");
                edtValor_Div.setText("");
                edtDataVenc_Div.setText("");
                edtDataDiv.setText("");
                edtNomeCredorDiv.setText("");

                findViewById(update_divida).setVisibility(View.GONE);
                findViewById(princi_div).setVisibility(View.VISIBLE);

            }
        });

        buttonAdd_Div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        findViewById(update_divida).setVisibility(View.VISIBLE);
                        findViewById(princi_div).setVisibility(View.GONE);

                        findViewById(R.id.btnEdit_Div).setVisibility(View.GONE);
                        findViewById(R.id.textAdd_Div).setVisibility(View.VISIBLE);

                        findViewById(R.id.btnSalvar_Div).setVisibility(View.VISIBLE);
                        findViewById(R.id.textEdit_Div).setVisibility(View.GONE);

                        findViewById(R.id.lineraLParc).setVisibility(View.VISIBLE);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String currentDateTimeString = dateFormat.format(new Date());
                        edtDataDiv.setText(currentDateTimeString);

                    }
                },1000);

            }
        });

        spinnerNomeDoCredorPorData.setVisibility(View.GONE);

        // buscaDivida_Adpter();
       /* filtrarDividaPorData();
        configurarRecycler();
        funcContPG();
        funcTotalPG();
        funcContAberto();
        funcTotalAberto();*/

        preencherEdtData();

        constraintInicial.setVisibility(View.VISIBLE);
        constraintFinal.setVisibility(View.VISIBLE);

        //filtrarDividaPorData();
        todasBuscas();
        //configurarRecycler();
        funcoesContReaisPorData();
        fechaConexao();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                txtUpdtDiv.setVisibility(View.GONE);

            }
        },20000);

    }

    public void contarPropaganda(){
        contadorPropagandaDivAPagar++;

        if(contadorPropagandaDivAPagar == 2){

            if (div_interstitial != null) {
                div_interstitial.show(List_Dividas.this);
            } else {
                //Toast.makeText(List_Dividas.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static String converteStringEmData(String stringData) throws ParseException {
        SimpleDateFormat simpleDateForma2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date receivedDate = simpleDateForma2.parse(stringData);

        String formatedDate = DateFormat.format("yyyy-MM-dd", receivedDate).toString();
        return formatedDate;
    }

    public void buttonClick_Conta() {

        texto_div = (TextView)findViewById(R.id.texto_div);
        contagemProgressiva = (Button) findViewById(R.id.contagemProgressiva);

        // contagem = preferences.getInt("contador", 0);
        texto_div.setText("Contagem atual:"+ contagem_div);

        contagemProgressiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contagem_div >= 3){
                    contagem_div = contagem_div - contagem_div;
                }
                contagem_div++;
                texto_div.setText("Contagem atual: " + contagem_div);
                editor_div.putInt("contador3", contagem_div);
                editor_div.commit();
            }
        });
    }

    public void referenciasXML(){

        radioGroupPorData = (RadioGroup) findViewById(R.id.radioGroupPorData);
        radioGroupPorCredor = (RadioGroup) findViewById(R.id.radioGroupPorCredor);
        radioGroupTodos = (RadioGroup) findViewById(R.id.radioGroupTodos);
        radioGroupTodosPorCredor = (RadioGroup) findViewById(R.id.radioGroupTodosPorCredor);

        radioPorData = (RadioButton) findViewById(R.id.radioPorData);
        radioAbertoPorData = (RadioButton) findViewById(R.id.radioAbertoPorData);
        radioPagoPorData = (RadioButton) findViewById(R.id.radioPagoPorData);
        radioTodosCredor = (RadioButton) findViewById(R.id.radioTodosCredor);
        radioAbertoCredor = (RadioButton) findViewById(R.id.radioAbertoCredor);
        radioPagoCredor = (RadioButton) findViewById(R.id.radioPagoCredor);
        radioTodoPorCredor = (RadioButton) findViewById(R.id.radioTodoPorCredor);
        radioAbertoTodosPorCredor = (RadioButton) findViewById(R.id.radioAbertoTodosPorCredor);
        radioPagoTodosPorCredor = (RadioButton) findViewById(R.id.radioPagoTodosPorCredor);
        radioTodos = (RadioButton) findViewById(R.id.radioTodo);
        radioAbertoTodos = (RadioButton) findViewById(R.id.radioAbertoTodos);
        radioPagoTodos= (RadioButton) findViewById(R.id.radioPagoTodos);

        lineraLParc = (LinearLayout) findViewById(R.id.lineraLParc);

        edtId_Div = (EditText) findViewById(R.id.edtId_Div);
        edtId_Div2 = (EditText) findViewById(R.id.edtId_Div2);
        edtNovaList_Div = (EditText) findViewById(R.id.edtNovaList_Div);
        edtValor_Div = (EditText) findViewById(R.id.edtValor_Div);
        edtDataVenc_Div = (EditText) findViewById(R.id.edtDataVenc_Div);
        edtDataDiv = (EditText) findViewById(R.id.edtDataDiv);
        edtNovaDivParc = (EditText) findViewById(R.id.edtNovaDivParc);
        edtDataAno = (EditText) findViewById(R.id.edtDataAno);
        edtDataDia = (EditText) findViewById(R.id.edtDataDia);
        edtDataMutiplicada = (EditText) findViewById(R.id.edtDataMutiplicada);
        edtDataMes = (EditText) findViewById(R.id.edtDataMes);
        edtParcelasDiv= (EditText) findViewById(R.id.edtParcelasDiv);
        edtNomeCredorDiv = (EditText) findViewById(R.id.edtNomeCredorDiv);
        edtVencInicial= (EditText) findViewById(R.id.edtVencInicial);
        edtVencFinal= (EditText) findViewById(R.id.edtVencFinal);
        txtDataInicial = (EditText) findViewById(R.id.txtDataInicial);
        txtDataFinal = (EditText) findViewById(R.id.txtDataFinal);

        btnSalvarParc = (Button) findViewById(R.id.btnSalvarParc);
        butonVoltar_Div = (Button) findViewById(R.id.butonVoltar_Div);
        btnEdit_Div = (Button) findViewById(R.id.btnEdit_Div);
        btnSalvar_Div = (Button) findViewById(R.id.btnSalvar_Div);

        btnDataVencInicial = (ImageButton) findViewById(R.id.btnDataVencInicial);
        btnDataVencFinal = (ImageButton) findViewById(R.id.btnDataVencFinal);
        btnDataVenc_Div = (ImageButton) findViewById(R.id.btnDataVenc_Div);
        btnDataDiv = (ImageButton) findViewById(R.id.btnDataDiv);
        buttonAdd_Div = (ImageButton) findViewById(R.id.buttonAdd_Div);

        constraintInicial = (ConstraintLayout) findViewById(R.id.constraintInicial);
        constraintFinal = (ConstraintLayout) findViewById(R.id.constraintFinal);
        dividaContraintLayout = (ConstraintLayout) findViewById(R.id.dividaContraintLayout);
        constraintSpinner = (ConstraintLayout) findViewById(R.id.constraintSpinner);
        constraintSpinner2 = (ConstraintLayout) findViewById(R.id.constraintSpinner2);
        constraintSpinnerTodasDiv = (ConstraintLayout) findViewById(R.id.constraintSpinnerTodasDiv);

        spinnerNomeDoCredorPorData = (Spinner) findViewById(R.id.spinnerNomeDoCredor);
        spinnerNomeCredorTodos = (Spinner) findViewById(R.id.spinnerNomeCredorTodos);


        checkFiltrarPorCredor = (CheckBox) findViewById(R.id.checkFiltrarPorCredor);
        checkB_DiV = (CheckBox) findViewById(R.id.checkB_DiV);
        checkDivRecTodos = (CheckBox) findViewById(R.id.checkDivRecTodos);
        checkSpinneTodasPorCredor = (CheckBox) findViewById(R.id.checkSpinneTodasPorCredor);
        checkSpinnePorCredor = (CheckBox) findViewById(R.id.checkFiltrarPorCredor);

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        textEdit_Div = (TextView) findViewById(R.id.textEdit_Div);
        textAdd_Div = (TextView) findViewById(R.id.textAdd_Div);
        txtTotalAberto = (TextView) findViewById(R.id.txtTotalAberto);
        txtContAberto = (TextView) findViewById(R.id.txtContAberto);
        txtTotalPG = (TextView) findViewById(R.id.txtTotalPG);
        txtContPG = (TextView) findViewById(R.id.txtContPG);
        txtTotalCont = (TextView) findViewById(R.id.txtTotalCont);
        txtTotalReais = (TextView) findViewById(R.id.txtTotalReais);

    }

    public void preencherEdtData(){

        calendar = Calendar.getInstance();
        int dia1 = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int mes1 = calendar.get(Calendar.MONTH);
        int ano1 = calendar.get(Calendar.YEAR);

        edtVencInicial.setText(String.format("%02d", dia1) + "/" + String.format("%02d", mes1+1) + "/" + ano1);


        try {
            txtDataInicial.setText(converteStringEmData(edtVencInicial.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        /*SimpleDateFormat dateFormatFinal = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateTimeStringFinal = dateFormatFinal.format(new Date());
        SimpleDateFormat dateFormatBr = new SimpleDateFormat("yyyy-MM-dd");

        dateFormatBr.format(currentDateTimeStringFinal);

        edtVencInicial.setText((CharSequence) dateFormatBr);*/

        calendar = Calendar.getInstance();
        int dia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        edtVencFinal.setText(String.format("%02d", dia) + "/" + String.format("%02d", mes+1) + "/" + ano);


        try {
            txtDataFinal.setText(converteStringEmData(edtVencFinal.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
    public void preencherEdtDataFiltro(){

        /* SimpleDateFormat dateFormat = new SimpleDateFormat(01 + "/MM/yyyy");
        String currentDateTimeString = dateFormat.format(new Date());
        edtVencInicialFiltro.setText(currentDateTimeString);

       SimpleDateFormat dateFormatFinal = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateTimeStringFinal = dateFormatFinal.format(new Date());
        edtVencFinal2.setText(currentDateTimeStringFinal);

        calendar = Calendar.getInstance();
        int mes1 = calendar.get(Calendar.MONTH);
        int ano1 = calendar.get(Calendar.YEAR);

        edtVencInicialFiltro.setText("0"+1 + "/" + (mes1+1) + "/" + ano1);*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("01/MM/yyyy");
        String currentDateTimeString = dateFormat.format(new Date());
        edtVencInicial.setText(currentDateTimeString);

        try {
            txtDataInicial.setText(converteStringEmData(edtVencInicial.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar = Calendar.getInstance();
        int dia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        edtVencFinal.setText(String.format("%02d", dia) + "/" + String.format("%02d", mes+1) + "/" + ano);


        try {
            txtDataFinal.setText(converteStringEmData(edtVencFinal.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void clickCheckBoxCredorPorData(View view){
        if(checkSpinnePorCredor.isChecked()){

            spinnerNomeDoCredorPorData.setVisibility(View.VISIBLE);
            //checkDivRecTodos.setVisibility(view.GONE);
            radioGroupPorCredor.setVisibility(View.VISIBLE);
            radioGroupPorData.setVisibility(view.GONE);
            checkDivRecTodos.setVisibility(view.GONE);

            preencherEdtDataFiltro();

            adapterspinnerNomeCredorPorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
            spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorPorData);

            spinnerNomeDoCredorPorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (radioTodosCredor.isChecked()){
                        criarConexao();
                        buscaDivida_AdpterSpiner();
                        //configurarRecyclerFiltro();

                        funcoesContReaisPorDataCredor();
                        fechaConexao();
                    } else
                    if (radioAbertoCredor.isChecked()){
                        criarConexao();
                        buscaDivida_AdpterSpinerAbertas();
                        //configurarRecyclerAbertoSpinner();

                        funcoesContReaisPorDataCredor();
                        fechaConexao();
                    } else
                    if (radioPagoCredor.isChecked()) {
                        criarConexao();
                        buscaDivida_AdpterSpinerPg();
                        //configurarRecyclerPagoSpinner();

                        funcoesContReaisPorDataCredor();
                        fechaConexao();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        if(!checkSpinnePorCredor.isChecked()) {

            spinnerNomeDoCredorPorData.setVisibility(View.GONE);
            //checkDivRecTodos.setVisibility(view.GONE);
            radioGroupPorCredor.setVisibility(View.GONE);
            radioGroupPorData.setVisibility(view.VISIBLE);
            checkDivRecTodos.setVisibility(view.VISIBLE);

            preencherEdtData();

            criarConexao();
            filtrarDividaPorData();
            //configurarRecycler();

            funcoesContReaisPorData();
            fechaConexao();

        }
    }
    public void clickCheckBoxTodos(View view){

        if(checkDivRecTodos.isChecked()){
            radioGroupTodos.setVisibility(view.VISIBLE);
            radioGroupPorData.setVisibility(view.GONE);
            btnDataVencInicial.setVisibility(view.GONE);
            btnDataVencFinal.setVisibility(view.GONE);
            constraintInicial.setVisibility(view.GONE);
            constraintFinal.setVisibility(view.GONE);
            constraintSpinner2.setVisibility(view.GONE);
            constraintSpinnerTodasDiv.setVisibility(view.VISIBLE);

            criarConexao();
            buscaDividaAdpterTodas();
            //configurarRecyclerTodos();

            funcoesContReaisTodos();
            fechaConexao();

        } else
        if(!checkDivRecTodos.isChecked()){
            radioGroupTodos.setVisibility(view.GONE);
            radioGroupPorData.setVisibility(view.VISIBLE);
            btnDataVencInicial.setVisibility(view.VISIBLE);
            btnDataVencFinal.setVisibility(view.VISIBLE);
            constraintInicial.setVisibility(view.VISIBLE);
            constraintFinal.setVisibility(view.VISIBLE);
            constraintSpinner2.setVisibility(view.VISIBLE);
            spinnerNomeDoCredorPorData.setVisibility(view.GONE);
            constraintSpinnerTodasDiv.setVisibility(view.GONE);
            //checkSpinneTodasPorCliente.setChecked(false);

            criarConexao();
            filtrarDividaPorData();
            //configurarRecycler();

            funcoesContReaisPorData();
            fechaConexao();

        }

    }
    public void clickCheckBoxTodosPorCredor(View view){
        if(checkSpinneTodasPorCredor.isChecked()){

            radioGroupTodos.setVisibility(View.GONE);
            radioGroupTodosPorCredor.setVisibility(View.VISIBLE);
            spinnerNomeCredorTodos.setVisibility(View.VISIBLE);
            checkDivRecTodos.setVisibility(View.GONE);
            spinnerNomeDoCredorPorData.setVisibility(view.GONE);

            checkDivRecTodos.setChecked(false);

            adapterspinnerNomeCredorTodos = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeCredorSppinerTodos());
            spinnerNomeCredorTodos.setAdapter(adapterspinnerNomeCredorTodos);

            spinnerNomeCredorTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    criarConexao();
                    buscaDividaAdpterTodasPorCredor();
                    //configurarRecyclerTodosPorCredor();

                    funcoesContReaisSpinnerTodosPorCredor();
                    fechaConexao();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else {


            radioGroupTodos.setVisibility(view.VISIBLE);
            radioGroupTodosPorCredor.setVisibility(view.GONE);
            spinnerNomeCredorTodos.setVisibility(view.GONE);
            checkDivRecTodos.setVisibility(view.VISIBLE);

            checkDivRecTodos.setChecked(true);

            criarConexao();
            buscaDividaAdpterTodas();
            //configurarRecyclerTodos();

            funcoesContReaisTodos();
            fechaConexao();

        }
    }

    public void onRadioButtonClickedPorData(View view) {
        // Is the button now checked?
        boolean checkedPorDaata = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioPorData:
                if (checkedPorDaata)
                    criarConexao();
                filtrarDividaPorData();
                //todasBuscas();
                //configurarRecycler();

                funcoesContReaisPorData();
                fechaConexao();
                break;
            case R.id.radioAbertoPorData:
                if (checkedPorDaata)
                    criarConexao();

                filtrarDividaPorDataAbertas();
                //configurarRecyclerAbertoPorData();

                funcoesContReaisPorData();

                fechaConexao();
                break;
            case R.id.radioPagoPorData:
                if (checkedPorDaata)
                    criarConexao();
                filtrarDividaPorDataPg();
                //configurarRecyclerPagoPorData();

                funcoesContReaisPorData();
                fechaConexao();
                break;
        }
    }
    public void onRadioButtonClickedPorCredorPorData(View view) {
        // Is the button now checked?
        boolean checkedPorCredor = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioTodosCredor:
                if (checkedPorCredor)

                    criarConexao();
                buscaDivida_AdpterSpiner();
                //configurarRecyclerFiltro();
                funcoesContReaisPorDataCredor();
                fechaConexao();

               /*     adapterspinnerNomeCredorPorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            dataBase.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
                spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorPorData);

                spinnerNomeDoCredorPorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        criarConexao();
                        buscaDivida_AdpterSpiner();
                        configurarRecyclerFiltro();

                        funcoesContReaisPorDataCredor();
                        fechaConexao();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

                break;
            case R.id.radioAbertoCredor:
                if (checkedPorCredor)
                 /*   adapter_spinner_NomeDivida_Rec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            dataBase.buscarNomeDividaSippner_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                spinnerNomeDivida_Rec.setAdapter(adapter_spinner_NomeDivida_Rec);

                spinnerNomeDivida_Rec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        criarConexao();
                        buscaDivida_AdpterSpinerAbertas();
                        configurarRecyclerAbertoSpinner();
                        funcContPG_Fitro();
                        funcTotalPG_Filtro();
                        funcContAberto_Filtro();
                        funcTotalAberto_Filtro();
                        fechaConexao();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                  */

                    criarConexao();
                buscaDivida_AdpterSpinerAbertas();
                //configurarRecyclerAbertoSpinner();

                funcoesContReaisPorDataCredor();
                fechaConexao();

                break;
            case R.id.radioPagoCredor:
                if (checkedPorCredor)

                 /*   adapter_spinner_NomeDivida_Rec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            dataBase.buscarNomeDividaSippner_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                spinnerNomeDivida_Rec.setAdapter(adapter_spinner_NomeDivida_Rec);

                spinnerNomeDivida_Rec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        criarConexao();
                        buscaDivida_AdpterSpinerPg();
                        configurarRecyclerPagoSpinner();
                        funcContPG_Fitro();
                        funcTotalPG_Filtro();
                        funcContAberto_Filtro();
                        funcTotalAberto_Filtro();
                        fechaConexao();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                  */

                    criarConexao();
                buscaDivida_AdpterSpinerPg();
                //configurarRecyclerPagoSpinner();

                funcoesContReaisPorDataCredor();
                fechaConexao();

                break;
        }
    }
    public void onRadioButtonClickedTodos(View view) {
        // Is the button now checked?
        boolean checkedTodos = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioTodo:
                if (checkedTodos)
                    criarConexao();
                buscaDividaAdpterTodas();
                //configurarRecyclerTodos();

                funcoesContReaisTodos();
                fechaConexao();

                break;
            case R.id.radioAbertoTodos:
                if (checkedTodos)
                    criarConexao();
                buscaTodasDividaAdpterAbertas();
                //configurarRecyclerAbertoTodos();

                funcoesContReaisTodos();
                fechaConexao();
                break;
            case R.id.radioPagoTodos:
                if (checkedTodos)
                    criarConexao();
                buscaTodasDividaAdpterPg();
                //configurarRecyclerPagoTodos();

                funcoesContReaisTodos();
                fechaConexao();

                break;
        }
    }
    public void onRadioButtonClickedTodosPorCredor(View view) {
        // Is the button now checked?
        boolean checkedTodosPorCredor = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioTodoPorCredor:
                if (checkedTodosPorCredor)

                    criarConexao();
                buscaDividaAdpterTodasPorCredor();
                //configurarRecyclerTodosPorCredor();

                funcoesContReaisSpinnerTodosPorCredor();
                fechaConexao();

                   /* adapterspinnerNomeCredorTodos = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                dataBase.buscarNomeCredorSppinerTodos());
                    spinnerNomeCredorTodos.setAdapter(adapterspinnerNomeCredorTodos);

                    spinnerNomeCredorTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            criarConexao();
                            buscaDividaAdpterTodasPorCredor();
                            configurarRecyclerTodosPorCredor();

                            funcoesContReaisSpinnerTodosPorCredor();
                            fechaConexao();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });*/

                break;
            case R.id.radioAbertoTodosPorCredor:
                if (checkedTodosPorCredor)
                   /* adapterspinnerNomecliente = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeClienteSippnerTodos());
                    spinnerNomeClienteTodos.setAdapter(adapterspinnerNomecliente);

                    spinnerNomeClienteTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            criarConexao();
                            buscaTodasDividaAdpterAbertasPorCliente();
                            configurarRecyclerAbertoTodosPorCliente();
                            funcContPG_Fitro();
                            funcTotalPG_Filtro();
                            funcContAberto_Filtro();
                            funcTotalAberto_Filtro();
                            fechaConexao();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    */

                    criarConexao();
                buscaTodasDividaAdpterAbertasPorCredor();
                //configurarRecyclerAbertoTodosPorCredor();

                funcoesContReaisSpinnerTodosPorCredor();
                fechaConexao();

                break;
            case R.id.radioPagoTodosPorCredor:
                if (checkedTodosPorCredor)

                    /*adapterspinnerNomecliente = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeClienteSippnerTodos());
                    spinnerNomeClienteTodos.setAdapter(adapterspinnerNomecliente);

                    spinnerNomeClienteTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            criarConexao();
                            buscaTodasDividaAdpterPgPorCliente();
                            configurarRecyclerPagoTodosPorCliente();
                            funcContPG_Fitro();
                            funcTotalPG_Filtro();
                            funcContAberto_Filtro();
                            funcTotalAberto_Filtro();
                            fechaConexao();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                     */

                    criarConexao();
                buscaTodasDividaAdpterPgPorCredor();
                //configurarRecyclerPagoTodosPorCredor();

                funcoesContReaisSpinnerTodosPorCredor();
                fechaConexao();

                break;
        }
    }

    //Busca para preenchimento do recyclerviw
    public void todasBuscas(){

        criarConexao();
        filtrarDividaPorData();
        fechaConexao();

        if(checkFiltrarPorCredor.isChecked()){

            criarConexao();
            buscaDivida_AdpterSpiner();

            funcoesContReaisPorDataCredor();
            fechaConexao();

            adapterspinnerNomeCredorPorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
            spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorPorData);

              /*  spinnerNomeDoCredorPorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        criarConexao();
                        buscaDivida_AdpterSpiner();

                        funcoesContReaisPorDataCredor();
                        fechaConexao();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
        } else if (checkDivRecTodos.isChecked()){

            criarConexao();
            buscaDividaAdpterTodas();
            fechaConexao();

            if(checkSpinneTodasPorCredor.isChecked()){

                criarConexao();
                buscaDividaAdpterTodasPorCredor();
                fechaConexao();

                adapterspinnerNomeCredorTodos = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                        dataBase.buscarNomeCredorSppinerTodos());
                spinnerNomeCredorTodos.setAdapter(adapterspinnerNomeCredorTodos);

               /* spinnerNomeCredorTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        criarConexao();
                        buscaDividaAdpterTodasPorCredor();
                        configurarRecyclerTodosPorCredor();

                        funcoesContReaisSpinnerTodosPorCredor();
                        fechaConexao();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
            }
        }
    }

    public void  filtrarDividaPorData(){
        List<Divida> dados = repo_divida.buscarData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }
    public void  filtrarDividaPorDataPg(){
        List<Divida> dados = repo_divida.buscarDataPagas(txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }
    public void  filtrarDividaPorDataAbertas(){
        List<Divida> dados = repo_divida.buscarDataAbertas(txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }

    public void  buscaDivida_AdpterSpiner(){
        List<Divida> dados2 = repo_divida.buscarTodosSpinner(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
        adapterDividas = new AdapterDividas(this, dados2);
        listDividas.setAdapter(adapterDividas);
    }
    public void  buscaDivida_AdpterSpinerPg(){
        List<Divida> dados2 = repo_divida.buscarTodosSpinnerPago(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
        adapterDividas = new AdapterDividas(this, dados2);
        listDividas.setAdapter(adapterDividas);
    }
    public void  buscaDivida_AdpterSpinerAbertas(){
        List<Divida> dados2 = repo_divida.buscarTodosSpinnerAbertas(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
        adapterDividas = new AdapterDividas(this, dados2);
        listDividas.setAdapter(adapterDividas);
    }

    public void buscaDividaAdpterTodas(){
        List<Divida> dados = repo_divida.buscarTodos();
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }
    public void buscaTodasDividaAdpterPg(){
        List<Divida> dados = repo_divida.buscarTodosPagos();
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }
    public void buscaTodasDividaAdpterAbertas(){
        List<Divida> dados = repo_divida.buscarTodasAbertas();
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }

    public void buscaDividaAdpterTodasPorCredor(){
        List<Divida> dados = repo_divida.buscarTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString());
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }
    public void buscaTodasDividaAdpterPgPorCredor(){
        List<Divida> dados = repo_divida.buscarTodosPorCredorPago(spinnerNomeCredorTodos.getSelectedItem().toString());
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }
    public void buscaTodasDividaAdpterAbertasPorCredor(){
        List<Divida> dados = repo_divida.buscarTodosPorCredorAberto(spinnerNomeCredorTodos.getSelectedItem().toString());
        adapterDividas = new AdapterDividas(this, dados);
        listDividas.setAdapter(adapterDividas);
    }

    // totais dos textviews
    public void func_ContPGPorData(){

        if(dataBase.totalPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){
            txtContPG.setText("("+dataBase.totalPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtContPG.setText("(0)");
        }
    }
    public void func_TotalPGPordata(){

        if(dataBase.somarPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG.setText("R$0.00");
        } else{
            txtTotalPG.setText("R$"+dataBase.somarPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void func_ContAbertoPordata(){

        if(dataBase.totalAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){
            txtContAberto.setText("("+dataBase.totalAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtContAberto.setText("(0)");
        }

    }
    public void func_TotalAbertoPordata(){

        if(dataBase.somarAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto.setText("R$0.00");
        } else{
            txtTotalAberto.setText("R$"+dataBase.somarAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void func_totalContPorData(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){
            txtTotalCont.setText("("+dataBase.totalContPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisPorData(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReais.setText("R$0.00");
        } else{
            txtTotalReais.setText("R$"+dataBase.totalReaisPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }

    public void funcoesContReaisPorData(){
        func_ContPGPorData();
        func_TotalPGPordata();
        func_ContAbertoPordata();
        func_TotalAbertoPordata();
        func_totalContPorData();
        func_totalReaisPorData();
    }

    public void func_ContPGPorDataCredor(){

        if(dataBase.totalPG_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){

            txtContPG.setText("("+dataBase.totalPG_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString()
                    , txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");

        }else{
            txtContPG.setText("(0)");
        }
    }
    public void func_TotalPGPorDataCredor(){

        if(dataBase.somarPG2_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){

            txtTotalPG.setText("R$0.00");
        } else{
            txtTotalPG.setText("R$"+dataBase.somarPG2_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void func_ContAbertoPorDataCredor(){

        if(dataBase.totalAberto_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){
            txtContAberto.setText("("+dataBase.totalAberto_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtContAberto.setText("(0)");
        }

    }
    public void func_TotalAbertoPorDataCredor(){

        if(dataBase.somarAberto2_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){

            txtTotalAberto.setText("R$0.00");
        } else{
            txtTotalAberto.setText("R$"+dataBase.somarAberto2_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void func_totalContPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContPorDataCredor(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){

            txtTotalCont.setText("("+dataBase.totalContPorDataCredor(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");

        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisPorDataCredor(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){

            txtTotalReais.setText("R$0.00");
        } else{
            txtTotalReais.setText("R$"+dataBase.totalReaisPorDataCredor(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }

    public void funcoesContReaisPorDataCredor(){
        func_ContPGPorDataCredor();
        func_TotalPGPorDataCredor();
        func_ContAbertoPorDataCredor();
        func_TotalAbertoPorDataCredor();
        func_totalContPorDataCredor();
        func_totalReaisPorDataCredor();

    }

    public void func_ContPGTodos(){

        if(dataBase.totalPGTodos() != null){
            txtContPG.setText("("+dataBase.totalPGTodos()+")");
        }else{
            txtContPG.setText("(0)");
        }
    }
    public void func_TotalPGTodos(){

        if(dataBase.somarPGTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG.setText("R$0.00");
        } else{
            txtTotalPG.setText("R$"+dataBase.somarPGTodos());
        }
    }
    public void func_ContAbertoTodos(){

        if(dataBase.totalAbertoTodos() != null){
            txtContAberto.setText("("+dataBase.totalAbertoTodos()+")");
        }else{
            txtContAberto.setText("(0)");
        }

    }
    public void func_TotalAbertoTodos(){

        if(dataBase.somarAbertoTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto.setText("R$0.00");
        } else{
            txtTotalAberto.setText("R$"+dataBase.somarAbertoTodos());
        }
    }
    public void func_totalContTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContTodosDiv() != null){
            txtTotalCont.setText("("+dataBase.totalContTodosDiv()+")");
        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisTodosDiv() == null){

            txtTotalReais.setText("R$0.00");
        } else{
            txtTotalReais.setText("R$"+dataBase.totalReaisTodosDiv());
        }
    }

    public void funcoesContReaisTodos(){
        func_ContPGTodos();
        func_TotalPGTodos();
        func_ContAbertoTodos();
        func_TotalAbertoTodos();
        func_totalContTodos();
        func_totalReaisTodos();
    }

    public void func_ContPGSpinnerTodosPorCredor(){

        if(dataBase.totalPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtContPG.setText("("+dataBase.totalPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString())+")");
        }else{
            txtContPG.setText("(0)");
        }
    }
    public void func_TotalPGSpinnerTodosPorCredor(){

        if(dataBase.somarPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){

            txtTotalPG.setText("R$"+dataBase.somarPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()));

        } else{
            txtTotalPG.setText("R$0.00");
        }
    }
    public void func_ContAbertoSpinnerTodosPorCredor(){

        if(dataBase.totalAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtContAberto.setText("("+dataBase.totalAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString())+")");
        }else{
            txtContAberto.setText("(0)");
        }

    }
    public void func_TotalAbertoSpinnerTodosPorCredor(){

        if(dataBase.somarAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtTotalAberto.setText("R$"+dataBase.somarAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()));
        } else{
            txtTotalAberto.setText("R$0.00");

        }
    }
    public void func_totalContSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtTotalCont.setText("("+dataBase.totalContTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString())+")");
        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtTotalReais.setText("R$"+dataBase.totalReaisTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()));
        } else{
            txtTotalReais.setText("R$0.00");

        }
    }

    public void funcoesContReaisSpinnerTodosPorCredor(){
        func_ContPGSpinnerTodosPorCredor();
        func_TotalPGSpinnerTodosPorCredor();
        func_ContAbertoSpinnerTodosPorCredor();
        func_TotalAbertoSpinnerTodosPorCredor();
        func_totalContSpinnerTodosPorCredor();
        func_totalReaisSpinnerTodosPorCredor();
    }

    public void trazerUpdate(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();

        divida = new Divida();

        if ((bundle != null) && (bundle.containsKey("ITEM_DIV"))){

            findViewById(update_divida).setVisibility(View.VISIBLE);
            findViewById(princi_div).setVisibility(View.GONE);
            findViewById(R.id.btnSalvar_Div).setVisibility(View.GONE);
            findViewById(R.id.textEdit_Div).setVisibility(View.VISIBLE);

            findViewById(R.id.btnEdit_Div).setVisibility(View.VISIBLE);
            findViewById(R.id.textAdd_Div).setVisibility(View.GONE);
            findViewById(R.id.lineraLParc).setVisibility(View.GONE);

            divida = (Divida) bundle.getSerializable("ITEM_DIV");

            edtNovaList_Div.setText(divida.NOMEDIV);
            edtValor_Div.setText(divida.VALORDIV);
            edtDataVenc_Div.setText(divida.DATAVENC);
            edtDataDiv.setText(divida.DATADIV);
            edtNomeCredorDiv.setText(divida.NOMEDOCREDOR);
            checkB_DiV.setChecked(divida.PG);
            //edtId_Div.setText(String.valueOf(divida.ID_LDIV));

            //toolbar.setTitle(dataBase.select_NomeListaDeDividas(Integer.parseInt(edtId_Div.getText().toString())));
        }
    }

    public void ClickAltomatico_AddDivida() {

        btnSalvarParc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCamposParcela() == false) try {

                    if (divida.ID >= 0) {
                        criarConexao();
                        repo_divida.inserir(divida);
                        adapterDividas.adicionarCliente(divida);

                    }

                    ArrayAdapter<String> adapter_spinner_NomeDivida = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            dataBase.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
                    spinnerNomeDoCredorPorData.setAdapter(adapter_spinner_NomeDivida);

                    spinnerNomeDoCredorPorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            criarConexao();
                            buscaDivida_AdpterSpiner();

                            funcoesContReaisPorDataCredor();
                            fechaConexao();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

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
    }

    private void criarConexao(){

        try {

            dataBase = new DataBaseLista(this);
            conexao = dataBase.getWritableDatabase();
            // Snackbar.make(itemContraintLayout, R.string.message_conexao_criada_sucesso, Snackbar.LENGTH_LONG)
            //        .setAction (getString(R.string.text_action_ok_conexao), null).show();
            repo_divida = new Repo_Divida(conexao);

        } catch (SQLException ex){

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
            repo_divida = new Repo_Divida(conexao);
            dataBase.close();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    private boolean validaCampos(){
        boolean res = false;
        String NOMEDIV = edtNovaList_Div.getText().toString();
        String VALORDIV =  edtValor_Div.getText().toString();
        String DATAVENC = edtDataVenc_Div.getText().toString();
        String DATADIV = edtDataDiv.getText().toString();
        String NUMEROPARC = edtParcelasDiv.getText().toString();
        // int ID_LDIV = Integer.parseInt(edtId_Div.getText().toString());
        boolean PG = checkB_DiV.isChecked();
        String NOMEDOCREDOR = edtNomeCredorDiv.getText().toString();


        divida.NOMEDIV = NOMEDIV;
        divida.VALORDIV = VALORDIV;
        divida.DATAVENC = DATAVENC;
        divida.DATADIV = DATADIV;
        // divida.ID_LDIV = ID_LDIV;
        divida.PG = PG;
        divida.NOMEDOCREDOR = NOMEDOCREDOR;

        if(res = isCampoVazio(NOMEDIV)){
            edtNovaList_Div.requestFocus();
        }else if(res = isCampoVazio(VALORDIV)){
            edtValor_Div.requestFocus();
        }else if(res = isCampoVazio(DATAVENC)){
            edtDataVenc_Div.requestFocus();
        }else if(res = isCampoVazio(NUMEROPARC)){
            edtParcelasDiv.requestFocus();
        }else if(res = isCampoVazio(NOMEDOCREDOR)){
            edtNomeCredorDiv.requestFocus();
        }
        if(res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage("Nome da dívida, credor, valor, vencimento e parcela são campos obrigatórios.");
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }
    private boolean validaCamposParcela(){
        boolean res = false;
        String NOMEDIV = edtNovaDivParc.getText().toString();
        String VALORDIV =  edtValor_Div.getText().toString();
        String DATAVENC = edtDataMutiplicada.getText().toString();
        String DATADIV = edtDataDiv.getText().toString();
        // int ID_LDIV = Integer.parseInt(edtId_Div.getText().toString());
        boolean PG = checkB_DiV.isChecked();
        String NOMEDOCREDOR = edtNomeCredorDiv.getText().toString();

        divida.NOMEDIV = NOMEDIV;
        divida.VALORDIV = VALORDIV;
        divida.DATAVENC = DATAVENC;
        divida.DATADIV = DATADIV;
        // divida.ID_LDIV = ID_LDIV;
        divida.PG = PG;
        divida.NOMEDOCREDOR = NOMEDOCREDOR;

        if(res = isCampoVazio(NOMEDIV)){
            edtNovaList_Div.requestFocus();
        }else
        if (res = isCampoVazio(VALORDIV)){
            edtValor_Div.requestFocus();
        } else
        if(res = isCampoVazio(DATAVENC)){
            edtDataVenc_Div.requestFocus();
        } else if(res = isCampoVazio(NOMEDOCREDOR)){
            edtNomeCredorDiv.requestFocus();
        }
        if(res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage("Nome da dívida, credor, valor, vencimento e parcela são campos obrigatórios.");
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }
    /* private boolean validaCampos_UptT(){

         boolean res = false;

         String Tarefa = edtTarefa_UpdT.getText().toString();
         String Prioridade = edtPriori_UpdT.getSelectedItem().toString();
         int ID_T = Integer.parseInt(edtId_UpdT.getText().toString());
         boolean check = checkB_UpdT.isChecked();

         tarefa.Tarefa = Tarefa;
         tarefa.Prioridade = Prioridade;
         tarefa.ID_T = ID_T;
         tarefa.check = check;

         if(res = isCampoVazio(Tarefa)){
             edtTarefa_UpdT.requestFocus();
         }
         if(res){
             AlertDialog.Builder dlg = new AlertDialog.Builder(this);
             dlg.setTitle(R.string.title_aviso);
             dlg.setMessage(R.string.message_campos_invalidosT);
             dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
             dlg.show();
         }

         return res;
     }

     */
    private boolean isCampoVazio(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, MenuInicial.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dividas, menu);
        return true;
    }
    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case android.R.id.home:

                if (lista_interstitial != null) {
                    lista_interstitial.show(List_Dividas.this);

                    lista_interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(List_Dividas.this,MenuInicial.class));
                        }
                    });

                } else {
                   // Toast.makeText(List_Dividas.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }

                break;

            case R.id.action_excluir_tudoDiv:

                dataBase = new DataBaseLista(this);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.itens_title_excluirtudoDiv)
                        .setMessage(R.string.item_message_extudoDiv)
                        .setPositiveButton(R.string.button_item_sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        criarConexao();
                                        repo_divida.excluirTdosItens();
                                        if(checkFiltrarPorCredor.isChecked()){
                                            buscaDivida_AdpterSpiner();

                                            funcoesContReaisPorDataCredor();

                                        } else {
                                            filtrarDividaPorData();
                                            //buscaDivida_Adpter();

                                            funcoesContReaisPorData();
                                        }

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

            case action_homeT:

                if (lista_interstitial != null) {
                    lista_interstitial.show(List_Dividas.this);

                    lista_interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(List_Dividas.this,MenuInicial.class));
                        }
                    });

                } else {
                    //Toast.makeText(List_Dividas.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }

                break;
            case salvarDividasBkp:

                pedirPermisos();

                if (lista_interstitial_SalvarArq != null) {
                    lista_interstitial_SalvarArq.show(List_Dividas.this);

                } else {
                    //Toast.makeText(List_Dividas.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                }

                criarConexao();

                repo_divida.geraPlanilaDivida();

                fechaConexao();

                AlertDialog.Builder builder = new AlertDialog.Builder(List_Dividas.this);
                builder.setTitle("Aviso")
                        .setMessage(R.string.gerarArquivoDivida)
                        .setNegativeButton("OK", null)
                        .show();


                break;

            case importarDividasBkp:

                new AlertDialog.Builder(this)
                        .setTitle(R.string.aviso_importar_bkpdivida)
                        .setMessage(R.string.importar_bkpdivida)
                        .setPositiveButton(R.string.button_item_sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        pedirPermisos();

                                        if (div_interstitial != null) {
                                            div_interstitial.show(List_Dividas.this);

                                        } else {
                                           // Toast.makeText(List_Dividas.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                                        }

                                        ReadExcelFileNomeListZero2();
                                        ReadExcelFileNomeList2();

                                        criarConexao();
                                        if(checkFiltrarPorCredor.isChecked()){
                                            buscaDivida_AdpterSpiner();

                                            funcoesContReaisPorDataCredor();

                                        } else {
                                            filtrarDividaPorData();
                                            //buscaDivida_Adpter();
                                            funcoesContReaisPorData();
                                        }
                                        fechaConexao();



                                    }
                                })
                        .setNegativeButton(R.string.button_item_exctudo_nao, null).show();



                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ReadExcelFileNomeListZero2() {

        //File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File root = new File(Environment.getExternalStorageDirectory() + "/Download");

        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        File xlsxArquivoDividas = new File(pasta, "listadividas.xlsx");

        if (root.exists()){

            if(xlsxArquivoDividas.exists()){

                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxArquivoDividas));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelListDividas.insertExcelToSqliteDividas(dbAdapter, sheet1);

                    fechaConexao();

                    Toast.makeText (List_Dividas.this, "Arquivo importado com sucesso.", Toast.LENGTH_LONG).show();

                    //xlsxArquivoDividas.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            else if (!xlsxArquivoDividas.exists()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("AVISO")
                        .setMessage(R.string.import_bkp_msg_arquivo_não_existe)
                        .setNegativeButton("VOLTA", null)
                        .show();
            }
        }


    }
    public void ReadExcelFileNomeList2() {

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File root = new File(Environment.getExternalStorageDirectory() + "/Download");

        File pasta = new File(root.getAbsolutePath());
        // pasta.mkdirs();

        for(int i = 0; i <= 100 ; i++) {

            File xlsxArquivoDividas = new File(pasta, "listadividas-" + i + ".xlsx");

            if(root.exists()){
                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxArquivoDividas));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelListDividas.insertExcelToSqliteDividas(dbAdapter, sheet1);

                    fechaConexao();

                    //dbAdapter.close();

                    // xlsxArquivoDividas.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

    }

    public boolean pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(List_Dividas.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(List_Dividas.this, new String[]
                            {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);

            AlertDialog.Builder builder = new AlertDialog.Builder(List_Dividas.this);
            builder.setTitle("Aviso")
                    .setMessage("Permissões concedidas.Tente importar ou gerar o arquivo novamente.")
                    .setNegativeButton("OK", null)
                    .show();



        }
        return false;
    }

    /*private void configurarRecycler() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        //buscaDivida_Adpter();
        filtrarDividaPorData();

        // Configurando um separador entre linhas, para uma melhor visualização.
        listDividas.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerFiltro() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);
        // Adiciona o adapter que irá anexar os objetos à lista.
        //buscaDivida_Adpter();
        buscaDivida_AdpterSpiner();

        // Configurando um separador entre linhas, para uma melhor visualização.
        listDividas.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/

    /*private void configurarRecycler() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        filtrarDividaPorData();

        // Configurando um separador entre linhas, para uma melhor visualização.
        // listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerFiltro() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);
        // Adiciona o adapter que irá anexar os objetos à lista.
        //buscaDivida_Adpter();
        buscaDivida_AdpterSpiner();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerTodos() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaDividaAdpterTodas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerPagoTodos() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterPg();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoTodos() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterAbertas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerTodosPorCredor() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaDividaAdpterTodasPorCredor();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerPagoTodosPorCredor() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterPgPorCredor();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoTodosPorCredor() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterAbertasPorCredor();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerPagoSpinner() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaDivida_AdpterSpinerPg();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoSpinner() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        buscaDivida_AdpterSpinerAbertas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerPagoPorData() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        filtrarDividaPorDataPg();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoPorData() {

        listDividas = (RecyclerView) findViewById(R.id.listDividas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas.setLayoutManager(layoutManager);

        filtrarDividaPorDataAbertas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/

}