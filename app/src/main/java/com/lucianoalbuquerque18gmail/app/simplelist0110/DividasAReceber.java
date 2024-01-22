package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter.AdapterDividasAReceber;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Receber;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Crud_DividasAReceber;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.arquivos.ExcelListDividasAReceber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.Handler;
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

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.importarDividasBkp_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.menuSairT;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.princi_div_rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.salvarDividasBkp_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update_divida_rec;


public class DividasAReceber extends AppCompatActivity {

    private EditText edtNovaList_Div_Rec, edtNovaDivParc_Rec, edtNomeCliente_Div_Rec;
    private EditText edtValor_Div_Rec;
    private EditText edtDataVenc_Div_Rec, edtParcelasDiv_Rec, edtDataMes_Rec;
    private EditText edtDataMutiplicada_Rec, edtDataDia_Rec, edtDataAno_Rec;
    private EditText edtDataDiv_Rec;
    public static EditText edtId_Div_Rec;
    public  EditText edtId_Div2_Rec;
    public static EditText edtVencInicial_Rec, edtVencFinal_Rec;
    public static EditText txtDataInicial_Rec, txtDataFinal_Rec ;
    public int numparcela = 1;
    public int Mes1;

    private TextView textEdit_Div_Rec;
    private TextView textAdd_Div_Rec;
    public static TextView txtContAberto_Rec;
    public static TextView txtTotalAberto_Rec;
    public static TextView txtContPG_Rec;
    public static TextView txtTotalPG_Rec;
    public TextView txtUpdtDiv_Rec;
    public static TextView txtTotalContRec;
    public static TextView txtTotalReaisRec;

    private ImageButton btnDataVenc_Div_Rec;
    private ImageButton btnDataDiv_Rec;
    private ImageButton buttonAdd_Div_Rec;
    private ImageButton btnDataVencInicial_Rec, btnDataVencFinal_Rec;

    private Button butonVoltar_Div_Rec;
    private Button btnEdit_Div_Rec;
    private Button btnSalvar_Div_Rec, btnSalvarParc_Rec;

    public static RecyclerView listDividas_Rec;

    private ConstraintLayout dividaContraintLayout_Rec, constraintSpinner_Rec, constraintSpinner2_Rec;
    private ConstraintLayout constraintInicial_Rec, constraintFinal_Rec, constraintSpinnerTodasDiv;

    private SQLiteDatabase conexao;
    private DataBaseLista dataBase;
    private Crud_DividasAReceber crud_dividasAReceber;
    private Receber receber;
    private AdapterDividasAReceber adapterDividasAReceber;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    public InterstitialAd div_interstitial_Rec, lista_interstitial_Rec, lista_interstitial_SalvarArq_Rec;

    public LinearLayout lineraLParc_Rec;

    public ExcelListDividasAReceber excelListDividasAReceber;

    public String resultPgAberto;

    public static Spinner spinnerNomeClientePorData, spinnerNomeClienteTodos;
    public static ArrayAdapter<String> adapterspinnerNomeClientePorData, adapterspinnerNomeclienteTodasDivRec;

    public static CheckBox checkSpinneNomeClientePorData, checkDivRecTodos, checkSpinneTodasPorCliente;
    private CheckBox checkB_DiV_Rec;

    public static RadioButton radioPorData, radioAbertoPorData, radioPagoPorData;
    public static RadioButton radioTodosCliente, radioAbertoCliente, radioPagoCliente;
    public static RadioButton radioTodos, radioAbertoTodos, radioPagoTodos;
    public static RadioButton radioTodoPorCliente, radioAbertoTodosPorCliente, radioPagoTodosPorCliente;
    private RadioGroup radioGroupPorData, radioGroupPorCliente, radioGroupTodos, radioGroupTodosPorCliente;

    public int contadorPropagandaDivReceber = 0;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dividas_areceber);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" Dividas a Receber");
        toolbar.setLogo(R.drawable.logo_barra_div);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView_Div_Rec = (AdView) findViewById(R.id.adView_Div_Rec);
        AdView adViewListaDividas_Rec = (AdView) findViewById(R.id.adViewListaDividas_Rec);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_Div_Rec.loadAd(adRequest);
        adViewListaDividas_Rec.loadAd(adRequest);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        AdRequest adRequest_div_Rec = new AdRequest.Builder().build();
        InterstitialAd.load(this,getString(R.string.interstitial_div_receber), adRequest_div_Rec,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        div_interstitial_Rec = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        div_interstitial_Rec = null;
                    }
                });

        AdRequest adRequest_intest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.interstitial_div_receber), adRequest_intest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        lista_interstitial_Rec = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Toast.makeText(DividasAReceber.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        lista_interstitial_Rec = null;
                    }
                });

        InterstitialAd.load(this,getString(R.string.interstitial_div_receber), adRequest_intest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        lista_interstitial_SalvarArq_Rec = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Toast.makeText(DividasAReceber.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        lista_interstitial_SalvarArq_Rec = null;
                    }
                });

        findViewById(update_divida_rec).setVisibility(View.INVISIBLE);
        txtUpdtDiv_Rec = (TextView) findViewById(R.id.txtUpdtDiv_Rec);

        referenciasXML();
        criarConexao();
        trazerUpdate();

        listDividas_Rec.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(linearLayoutManager);

        crud_dividasAReceber = new Crud_DividasAReceber(conexao);

        btnDataVenc_Div_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtDataVenc_Div_Rec.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtDataVenc_Div_Rec.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataVenc_Div_Rec.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                        edtDataDia_Rec.setText(String.format("%02d", mDia));
                        edtDataMes_Rec.setText(String.format("%02d", mMes+1));
                        edtDataAno_Rec.setText(Integer.toString(mAno));
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        btnDataDiv_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtDataDiv_Rec.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtDataDiv_Rec.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataDiv_Rec.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        edtDataVenc_Div_Rec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtDataVenc_Div_Rec.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtDataVenc_Div_Rec.setTextIsSelectable(true);
                    }

                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //edtDataVenc_Div.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtDataVenc_Div_Rec.setText( String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                            edtDataDia_Rec.setText(String.format("%02d", mDia));
                            edtDataMes_Rec.setText(String.format("%02d", mMes+1));
                            edtDataAno_Rec.setText(Integer.toString(mAno));
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();

                }
            }
        });

        edtDataDiv_Rec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtDataDiv_Rec.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtDataDiv_Rec.setTextIsSelectable(true);
                    }

                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //   edtDataListDiv_D.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtDataDiv_Rec.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();
                }
            }
        });

        edtVencInicial_Rec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus ) {
                if (hasFocus) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtVencInicial_Rec.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtVencInicial_Rec.setTextIsSelectable(true);
                    }
                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //   edtDataListDiv_D.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtVencInicial_Rec.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                            try {
                                txtDataInicial_Rec.setText(converteStringEmData(edtVencInicial_Rec.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            edtVencFinal_Rec.requestFocus();

                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();

                }

            }
        });

        edtVencFinal_Rec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus ) {
                if (hasFocus) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                        edtVencFinal_Rec.setShowSoftInputOnFocus(false);
                    } else { // API 11-20
                        edtVencFinal_Rec.setTextIsSelectable(true);
                    }

                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            //   edtDataListDiv_D.setText(mDia + "/" + (mMes+1) + "/" + mAno);
                            edtVencFinal_Rec.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                            try {
                                txtDataFinal_Rec.setText(converteStringEmData(edtVencFinal_Rec.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            criarConexao();
                            filtrarDividaPorData();
                            //configurarRecycler();

                            funcoesContReaisPorData();

                            fechaConexao();

                            adapterspinnerNomeClientePorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                    dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                            spinnerNomeClientePorData.setAdapter(adapterspinnerNomeClientePorData);

                            spinnerNomeClientePorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    criarConexao();
                                    buscaDivida_AdpterSpiner();
                                    //configurarRecyclerFiltro();

                                    funcoesContReaisPorDataCliente();
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

        btnDataVencInicial_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtVencInicial_Rec.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtVencInicial_Rec.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtVencInicial_Rec.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));
                        try {
                            txtDataInicial_Rec.setText(converteStringEmData(edtVencInicial_Rec.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        edtVencFinal_Rec.requestFocus();
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        btnDataVencFinal_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    edtVencFinal_Rec.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    edtVencFinal_Rec.setTextIsSelectable(true);
                }

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(DividasAReceber.this,0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtVencFinal_Rec.setText(String.format("%02d", mDia) + "/" + String.format("%02d", mMes+1) + "/" + Integer.toString(mAno));

                        try {
                            txtDataFinal_Rec.setText(converteStringEmData(edtVencFinal_Rec.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        criarConexao();
                        filtrarDividaPorData();
                        //configurarRecycler();

                        funcoesContReaisPorData();

                        fechaConexao();

                        adapterspinnerNomeClientePorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                        spinnerNomeClientePorData.setAdapter(adapterspinnerNomeClientePorData);

                        spinnerNomeClientePorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                criarConexao();
                                buscaDivida_AdpterSpiner();
                                //configurarRecyclerFiltro();

                                funcoesContReaisPorDataCliente();
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

        btnSalvar_Div_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //contarPropaganda();

                if(Integer.parseInt(edtParcelasDiv_Rec.getText().toString())<=100){
                    if (validaCampos() == false) try {

                        if (receber.ID >= 0) {
                            criarConexao();
                            crud_dividasAReceber.inserir(receber);
                            adapterDividasAReceber.adicionarCliente(receber);

                            calendar = Calendar.getInstance();
                            int dia1 = Integer.parseInt((edtDataDia_Rec.getText().toString()));
                            int mes1 = Integer.parseInt((edtDataMes_Rec.getText().toString()));
                            int mes2 = Integer.parseInt((edtDataMes_Rec.getText().toString()));
                            int mes3 = Integer.parseInt((edtDataMes_Rec.getText().toString()));
                            int ano1 = Integer.parseInt((edtDataAno_Rec.getText().toString()));
                            // edtNovaDivParc.setText(edtNovaList_Div.getText().toString());

                            for(int parcelas = Integer.parseInt(edtParcelasDiv_Rec.getText().toString()); parcelas > 1; parcelas--){

                                /*  Mes1 = ++mes1; //(++mes1par);
                                 Mes2 = ++mes2; //(++mes2par);


                               if (Mes1 > 12){
                                    //Mes1= (Mes1-Mes1)+1;
                                    mes1= (mes1-mes1);
                                    mes1= ++mes1;

                                   // mes1par = mes1par-mes1par;
                                    if(Mes2 == 13){
                                        edtDataMutiplicada.setText(String.format("%02d", dia1) + "/" + String.format("%02d", mes1) + "/" + (ano1 + (++anopar)));
                                    }
                                }


                              //  if (Mes2 < 13){
                                   edtDataMutiplicada.setText(String.format("%02d", dia1) + "/" + String.format("%02d", Mes1) + "/" + ano1);
                                //    }
                               if(Mes2 == 13){
                                    Mes2 = (Mes2 - Mes2) + 1;
                                    //mes2par = mes2par-mes2par;
                                }*/
                                edtNovaDivParc_Rec.setText(edtNovaList_Div_Rec.getText().toString() + " Parcela "+(++numparcela));
                                Mes1 = ++mes1;

                                if (Mes1<=12){
                                    edtDataMutiplicada_Rec.setText(String.format("%02d", dia1) + "/" + String.format("%02d", Mes1) + "/" + ano1);
                                }

                                if (Mes1>12){
                                    mes1 = mes1-12;
                                    ano1=ano1+1;
                                    edtDataMutiplicada_Rec.setText(String.format("%02d", dia1) + "/" + String.format("%02d", mes1) + "/" + ano1);

                                }

                                ClickAltomatico_AddDivida();
                                btnSalvarParc_Rec.performClick();
                            }

                            numparcela = (numparcela-numparcela) +1;

                            edtNovaList_Div_Rec.setText("");
                            edtValor_Div_Rec.setText("");
                            edtDataVenc_Div_Rec.setText("");
                            edtDataDiv_Rec.setText("");
                            edtDataDia_Rec.setText("");
                            edtDataMes_Rec.setText("");
                            edtDataMutiplicada_Rec.setText("");
                            edtParcelasDiv_Rec.setText("01");

                            findViewById(update_divida_rec).setVisibility(View.GONE);
                            findViewById(princi_div_rec).setVisibility(View.VISIBLE);

                        }

                        todasBuscas();
                        //filtrarDividaPorData();
                        //buscaDivida_Adpter();
                        funcoesContReaisPorData();
                        fechaConexao();

                       /* if (checkSpinneNomeClientePorData.isChecked()){
                            ArrayAdapter<String> adapter_spinner_NomeDivida_Rec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                    dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                            spinnerNomeClientePorData.setAdapter(adapter_spinner_NomeDivida_Rec);

                            spinnerNomeClientePorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    criarConexao();
                                    buscaDivida_AdpterSpiner();

                                    funcoesContReaisPorDataCliente();

                                    fechaConexao();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else
                        if (checkSpinneTodasPorCliente.isChecked()){
                            adapterspinnerNomeclienteTodasDivRec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                    dataBase.buscarNomeClienteSppinerTodosRec());
                            spinnerNomeClienteTodos.setAdapter(adapterspinnerNomeclienteTodasDivRec);

                            spinnerNomeClienteTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    criarConexao();
                                    buscaDividaAdpterTodasPorCliente();
                                    //configurarRecyclerTodosPorCliente();


                                    funcoesContReaisSpinnerTodosPorCliente();
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
                    Toast.makeText (DividasAReceber.this, "100 é o número máximo de parcelas permitidas ", Toast.LENGTH_LONG).show();

                }


            }
        });

        btnEdit_Div_Rec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validaCampos() == false) try {

                    if (receber.ID > 0) {
                        criarConexao();
                        crud_dividasAReceber.alterar(receber);

                        edtNovaList_Div_Rec.setText("");
                        edtValor_Div_Rec.setText("");
                        edtDataVenc_Div_Rec.setText("");
                        edtDataDiv_Rec.setText("");
                        edtNomeCliente_Div_Rec.setText("");

                    }

                    todasBuscas();
                    //filtrarDividaPorData();

                    findViewById(update_divida_rec).setVisibility(View.GONE);
                    findViewById(princi_div_rec).setVisibility(View.VISIBLE);

                    funcoesContReaisPorData();
                    fechaConexao();

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtNovaList_Div_Rec.getWindowToken(), 0);

                } catch (SQLException ex) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                    dlg.setTitle("Erro");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton("R.string.text_action_ok_conexao", null);
                    dlg.show();
                }

            }

        });

        butonVoltar_Div_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtNovaList_Div_Rec.setText("");
                edtValor_Div_Rec.setText("");
                edtDataVenc_Div_Rec.setText("");
                edtDataDiv_Rec.setText("");
                edtNomeCliente_Div_Rec.setText("");

                findViewById(update_divida_rec).setVisibility(View.GONE);
                findViewById(princi_div_rec).setVisibility(View.VISIBLE);
                //finish();
            }
        });

        buttonAdd_Div_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        findViewById(update_divida_rec).setVisibility(View.VISIBLE);
                        findViewById(princi_div_rec).setVisibility(View.GONE);

                        findViewById(R.id.btnEdit_Div_Rec).setVisibility(View.GONE);
                        findViewById(R.id.textAdd_Div_Rec).setVisibility(View.VISIBLE);

                        findViewById(R.id.btnSalvar_Div_Rec).setVisibility(View.VISIBLE);
                        findViewById(R.id.textEdit_Div_Rec).setVisibility(View.GONE);

                        findViewById(R.id.lineraLParc_Rec).setVisibility(View.VISIBLE);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String currentDateTimeString = dateFormat.format(new Date());
                        edtDataDiv_Rec.setText(currentDateTimeString);

                    }
                },1000);

            }
        });

        spinnerNomeClientePorData.setVisibility(View.GONE);

        preencherEdtData();

        constraintInicial_Rec.setVisibility(View.VISIBLE);
        constraintFinal_Rec.setVisibility(View.VISIBLE);

        criarConexao();
        todasBuscas();
        //filtrarDividaPorData();
        //configurarRecycler();

        funcoesContReaisPorData();
        fechaConexao();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                txtUpdtDiv_Rec.setVisibility(View.GONE);

            }
        },20000);
    }

    public void contarPropaganda(){
        contadorPropagandaDivReceber++;

        if(contadorPropagandaDivReceber == 2){

            if (div_interstitial_Rec != null) {
                div_interstitial_Rec.show(DividasAReceber.this);

            } else {
                //Toast.makeText(DividasAReceber.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static String converteStringEmData(String stringData) throws ParseException {
        SimpleDateFormat simpleDateForma2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date receivedDate = simpleDateForma2.parse(stringData);

        String formatedDate = DateFormat.format("yyyy-MM-dd", receivedDate).toString();
        return formatedDate;
    }

    public void referenciasXML(){

        radioGroupPorData = (RadioGroup) findViewById(R.id.radioGroupPorData);
        radioGroupPorCliente = (RadioGroup) findViewById(R.id.radioGroupPorCliente);
        radioGroupTodos = (RadioGroup) findViewById(R.id.radioGroupTodos);
        radioGroupTodosPorCliente = (RadioGroup) findViewById(R.id.radioGroupTodosPorCliente);

        radioPorData = (RadioButton) findViewById(R.id.radioPorData);
        radioAbertoPorData = (RadioButton) findViewById(R.id.radioAbertoPorData);
        radioPagoPorData = (RadioButton) findViewById(R.id.radioPagoPorData);
        radioTodosCliente = (RadioButton) findViewById(R.id.radioTodosCliente);
        radioAbertoCliente = (RadioButton) findViewById(R.id.radioAbertoCliente);
        radioPagoCliente = (RadioButton) findViewById(R.id.radioPagoCliente);
        radioTodoPorCliente = (RadioButton) findViewById(R.id.radioTodoPorCliente);
        radioAbertoTodosPorCliente = (RadioButton) findViewById(R.id.radioAbertoTodosPorCliente);
        radioPagoTodosPorCliente= (RadioButton) findViewById(R.id.radioPagoTodosPorCliente);
        radioTodos = (RadioButton) findViewById(R.id.radioTodo);
        radioAbertoTodos = (RadioButton) findViewById(R.id.radioAbertoTodos);
        radioPagoTodos= (RadioButton) findViewById(R.id.radioPagoTodos);

        edtNovaList_Div_Rec = (EditText) findViewById(R.id.edtNovaList_Div_Rec);
        edtValor_Div_Rec = (EditText) findViewById(R.id.edtValor_Div_Rec);
        edtId_Div_Rec = (EditText) findViewById(R.id.edtId_Div_Rec);
        edtId_Div2_Rec = (EditText) findViewById(R.id.edtId_Div2_Rec);
        edtDataVenc_Div_Rec = (EditText) findViewById(R.id.edtDataVenc_Div_Rec);
        edtDataDiv_Rec = (EditText) findViewById(R.id.edtDataDiv_Rec);
        edtNomeCliente_Div_Rec = (EditText) findViewById(R.id.edtNomeCliente_Div_Rec);
        edtNovaDivParc_Rec = (EditText) findViewById(R.id.edtNovaDivParc_Rec);
        edtDataAno_Rec = (EditText) findViewById(R.id.edtDataAno_Rec);
        edtDataDia_Rec = (EditText) findViewById(R.id.edtDataDia_Rec);
        edtDataMutiplicada_Rec = (EditText) findViewById(R.id.edtDataMutiplicada_Rec);
        edtDataMes_Rec = (EditText) findViewById(R.id.edtDataMes_Rec);
        edtParcelasDiv_Rec= (EditText) findViewById(R.id.edtParcelasDiv_Rec);
        edtVencInicial_Rec= (EditText) findViewById(R.id.edtVencInicial_Rec);
        edtVencFinal_Rec= (EditText) findViewById(R.id.edtVencFinal_Rec);
        txtDataInicial_Rec = (EditText) findViewById(R.id.txtDataInicial_Rec);
        txtDataFinal_Rec= (EditText) findViewById(R.id.txtDataFinal_Rec);

        lineraLParc_Rec = (LinearLayout) findViewById(R.id.lineraLParc_Rec);

        btnDataVencInicial_Rec = (ImageButton) findViewById(R.id.btnDataVencInicial_Rec);
        btnDataVencFinal_Rec = (ImageButton) findViewById(R.id.btnDataVencFinal_Rec);
        btnDataVenc_Div_Rec = (ImageButton) findViewById(R.id.btnDataVenc_Div_Rec);
        btnDataDiv_Rec = (ImageButton) findViewById(R.id.btnDataDiv_Rec);
        buttonAdd_Div_Rec = (ImageButton) findViewById(R.id.buttonAdd_Div_Rec);

        constraintInicial_Rec = (ConstraintLayout) findViewById(R.id.constraintInicial_Rec);
        constraintFinal_Rec = (ConstraintLayout) findViewById(R.id.constraintFinal_Rec);
        constraintSpinner_Rec = (ConstraintLayout) findViewById(R.id.constraintSpinner_Rec);
        constraintSpinner2_Rec = (ConstraintLayout) findViewById(R.id.constraintSpinner2_Rec);
        constraintSpinnerTodasDiv = (ConstraintLayout) findViewById(R.id.constraintSpinnerTodasDiv);
        dividaContraintLayout_Rec = (ConstraintLayout) findViewById(R.id.dividaContraintLayout);

        spinnerNomeClientePorData = (Spinner) findViewById(R.id.spinnerNomeClientePorData);
        spinnerNomeClienteTodos = (Spinner) findViewById(R.id.spinnerNomeClienteTodos);

        checkSpinneNomeClientePorData = (CheckBox) findViewById(R.id.checkSpinneNomeClientePorData);
        checkDivRecTodos = (CheckBox) findViewById(R.id.checkDivRecTodos);
        checkSpinneTodasPorCliente = (CheckBox) findViewById(R.id.checkSpinneTodasPorCliente);
        checkB_DiV_Rec = (CheckBox) findViewById(R.id.checkB_DiV_Rec);

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        butonVoltar_Div_Rec = (Button) findViewById(R.id.butonVoltar_Div_Rec);
        btnEdit_Div_Rec = (Button) findViewById(R.id.btnEdit_Div_Rec);
        btnSalvar_Div_Rec = (Button) findViewById(R.id.btnSalvar_Div_Rec);
        btnSalvarParc_Rec = (Button) findViewById(R.id.btnSalvarParc_Rec);

        textEdit_Div_Rec = (TextView) findViewById(R.id.textEdit_Div_Rec);
        textAdd_Div_Rec = (TextView) findViewById(R.id.textAdd_Div_Rec);
        txtTotalAberto_Rec = (TextView) findViewById(R.id.txtTotalAberto_Rec);
        txtContAberto_Rec = (TextView) findViewById(R.id.txtContAberto_Rec);
        txtTotalPG_Rec = (TextView) findViewById(R.id.txtTotalPG_Rec);
        txtContPG_Rec = (TextView) findViewById(R.id.txtContPG_Rec);
        txtTotalContRec = (TextView) findViewById(R.id.txtTotalContRec);
        txtTotalReaisRec = (TextView) findViewById(R.id.txtTotalReaisRec);

    }

    public void preencherEdtData(){

        calendar = Calendar.getInstance();
        int dia1 = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int mes1 = calendar.get(Calendar.MONTH);
        int ano1 = calendar.get(Calendar.YEAR);

        edtVencInicial_Rec.setText(String.format("%02d", dia1) + "/" + String.format("%02d", mes1+1) + "/" + ano1);


        try {
            txtDataInicial_Rec.setText(converteStringEmData(edtVencInicial_Rec.getText().toString()));
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

        edtVencFinal_Rec.setText(String.format("%02d", dia) + "/" + String.format("%02d", mes+1) + "/" + ano);


        try {
            txtDataFinal_Rec.setText(converteStringEmData(edtVencFinal_Rec.getText().toString()));
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
        edtVencInicial_Rec.setText(currentDateTimeString);

        try {
            txtDataInicial_Rec.setText(converteStringEmData(edtVencInicial_Rec.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar = Calendar.getInstance();
        int dia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        edtVencFinal_Rec.setText(String.format("%02d", dia) + "/" + String.format("%02d", mes+1) + "/" + ano);


        try {
            txtDataFinal_Rec.setText(converteStringEmData(edtVencFinal_Rec.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void clickCheckBoxClientePorData(View view){
        if(checkSpinneNomeClientePorData.isChecked()){

            spinnerNomeClientePorData.setVisibility(View.VISIBLE);
            //checkDivRecTodos.setVisibility(view.GONE);
            radioGroupPorCliente.setVisibility(View.VISIBLE);
            radioGroupPorData.setVisibility(view.GONE);
            checkDivRecTodos.setVisibility(view.GONE);

            preencherEdtDataFiltro();

            adapterspinnerNomeClientePorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
            spinnerNomeClientePorData.setAdapter(adapterspinnerNomeClientePorData);

            spinnerNomeClientePorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (radioTodosCliente.isChecked()){
                        criarConexao();
                        buscaDivida_AdpterSpiner();
                        //configurarRecyclerFiltro();

                        funcoesContReaisPorDataCliente();
                        fechaConexao();
                    } else
                    if (radioAbertoCliente.isChecked()){
                        criarConexao();
                        buscaDivida_AdpterSpinerAbertas();
                        //configurarRecyclerAbertoSpinner();

                        funcoesContReaisPorDataCliente();
                        fechaConexao();
                    } else
                    if (radioPagoCliente.isChecked()) {
                        criarConexao();
                        buscaDivida_AdpterSpinerPg();
                        //configurarRecyclerPagoSpinner();

                        funcoesContReaisPorDataCliente();
                        fechaConexao();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        if(!checkSpinneNomeClientePorData.isChecked()) {

            spinnerNomeClientePorData.setVisibility(View.GONE);
            //checkDivRecTodos.setVisibility(view.GONE);
            radioGroupPorCliente.setVisibility(View.GONE);
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
            btnDataVencInicial_Rec.setVisibility(view.GONE);
            btnDataVencFinal_Rec.setVisibility(view.GONE);
            constraintInicial_Rec.setVisibility(view.GONE);
            constraintFinal_Rec.setVisibility(view.GONE);
            constraintSpinner2_Rec.setVisibility(view.GONE);
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
            btnDataVencInicial_Rec.setVisibility(view.VISIBLE);
            btnDataVencFinal_Rec.setVisibility(view.VISIBLE);
            constraintInicial_Rec.setVisibility(view.VISIBLE);
            constraintFinal_Rec.setVisibility(view.VISIBLE);
            constraintSpinner2_Rec.setVisibility(view.VISIBLE);
            spinnerNomeClientePorData.setVisibility(view.GONE);
            constraintSpinnerTodasDiv.setVisibility(view.GONE);
            //checkSpinneTodasPorCliente.setChecked(false);

            criarConexao();
            filtrarDividaPorData();
            //configurarRecycler();

            funcoesContReaisPorData();
            fechaConexao();

        }

    }
    public void clickCheckBoxTodosPorCLiente(View view){
        if(checkSpinneTodasPorCliente.isChecked()){

            radioGroupPorCliente.setVisibility(view.GONE);
            radioGroupPorData.setVisibility(view.GONE);
            radioGroupTodos.setVisibility(view.GONE);
            radioGroupTodosPorCliente.setVisibility(view.VISIBLE);

            spinnerNomeClienteTodos.setVisibility(view.VISIBLE);
            checkDivRecTodos.setVisibility(view.GONE);
            spinnerNomeClientePorData.setVisibility(view.GONE);

            checkDivRecTodos.setChecked(false);

            adapterspinnerNomeclienteTodasDivRec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeClienteSppinerTodosRec());
            spinnerNomeClienteTodos.setAdapter(adapterspinnerNomeclienteTodasDivRec);

            spinnerNomeClienteTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    criarConexao();
                    buscaDividaAdpterTodasPorCliente();
                    //configurarRecyclerTodosPorCliente();

                    funcoesContReaisSpinnerTodosPorCliente();
                    fechaConexao();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else {

            checkDivRecTodos.setChecked(true);
            radioGroupTodos.setVisibility(view.VISIBLE);
            radioGroupTodosPorCliente.setVisibility(view.GONE);
            spinnerNomeClienteTodos.setVisibility(view.GONE);
            checkDivRecTodos.setVisibility(view.VISIBLE);

            criarConexao();
            buscaDividaAdpterTodas();
            //configurarRecyclerTodos();

            funcoesContReaisTodos();
            fechaConexao();

        }
    }

    public void atualizarListaUpdateDivAReceber(){
        if(checkSpinneNomeClientePorData.isChecked()){

            preencherEdtDataFiltro();

            adapterspinnerNomeClientePorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
            spinnerNomeClientePorData.setAdapter(adapterspinnerNomeClientePorData);

            spinnerNomeClientePorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (radioTodosCliente.isChecked()){
                        criarConexao();

                        funcoesContReaisPorDataCliente();
                        fechaConexao();
                    } else
                    if (radioAbertoCliente.isChecked()){
                        criarConexao();

                        funcoesContReaisPorDataCliente();
                        fechaConexao();
                    } else
                    if (radioPagoCliente.isChecked()) {
                        criarConexao();

                        funcoesContReaisPorDataCliente();
                        fechaConexao();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else
        if(!checkSpinneNomeClientePorData.isChecked()) {

            preencherEdtData();

            criarConexao();

            funcoesContReaisPorData();
            fechaConexao();

        } else
        if(checkDivRecTodos.isChecked()){

            criarConexao();

            funcoesContReaisTodos();
            fechaConexao();

        } else
        if(!checkDivRecTodos.isChecked()){

            criarConexao();

            funcoesContReaisPorData();
            fechaConexao();

        } else
        if(checkSpinneTodasPorCliente.isChecked()){

            adapterspinnerNomeclienteTodasDivRec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeClienteSppinerTodosRec());
            spinnerNomeClienteTodos.setAdapter(adapterspinnerNomeclienteTodasDivRec);

            spinnerNomeClienteTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    criarConexao();

                    funcoesContReaisSpinnerTodosPorCliente();
                    fechaConexao();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else
        if(!checkSpinneTodasPorCliente.isChecked()) {

            criarConexao();

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
    public void onRadioButtonClickedPorCliente(View view) {
        // Is the button now checked?
        boolean checkedPorCliente = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioTodosCliente:
                if (checkedPorCliente)

                    criarConexao();
                buscaDivida_AdpterSpiner();
                //configurarRecyclerFiltro();
                funcoesContReaisPorDataCliente();
                fechaConexao();

                 /*   adapterspinnerNomeClientePorData = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                spinnerNomeClientePorData.setAdapter(adapterspinnerNomeClientePorData);

                spinnerNomeClientePorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        criarConexao();
                        buscaDivida_AdpterSpiner();
                        //configurarRecyclerFiltro();

                        funcoesContReaisPorDataCliente();
                        fechaConexao();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

                break;
            case R.id.radioAbertoCliente:
                if (checkedPorCliente)
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
                funcoesContReaisPorDataCliente();
                fechaConexao();

                break;
            case R.id.radioPagoCliente:
                if (checkedPorCliente)
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
                funcoesContReaisPorDataCliente();
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
    public void onRadioButtonClickedTodosPorCliente(View view) {
        // Is the button now checked?
        boolean checkedTodosPorCliente = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioTodoPorCliente:
                if (checkedTodosPorCliente)

                    criarConexao();
                buscaDividaAdpterTodasPorCliente();
                //configurarRecyclerTodosPorCliente();

                funcoesContReaisSpinnerTodosPorCliente();
                fechaConexao();

                 /*   adapterspinnerNomeclienteTodasDivRec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            dataBase.buscarNomeClienteSppinerTodosRec());
                spinnerNomeClienteTodos.setAdapter(adapterspinnerNomeclienteTodasDivRec);

                spinnerNomeClienteTodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        criarConexao();
                        buscaDividaAdpterTodasPorCliente();
                        //configurarRecyclerTodosPorCliente();

                        funcoesContReaisSpinnerTodosPorCliente();
                        fechaConexao();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

                break;
            case R.id.radioAbertoTodosPorCliente:
                if (checkedTodosPorCliente)
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
                buscaTodasDividaAdpterAbertasPorCliente();
                //configurarRecyclerAbertoTodosPorCliente();

                funcoesContReaisSpinnerTodosPorCliente();
                fechaConexao();

                break;
            case R.id.radioPagoTodosPorCliente:
                if (checkedTodosPorCliente)

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
                buscaTodasDividaAdpterPgPorCliente();
                //configurarRecyclerPagoTodosPorCliente();

                funcoesContReaisSpinnerTodosPorCliente();
                fechaConexao();

                break;
        }
    }

    //Busca para preenchimento do recyclerviw
    public void todasBuscas(){

        criarConexao();
        filtrarDividaPorData();
        fechaConexao();

        if(checkSpinneNomeClientePorData.isChecked()){

            criarConexao();
            buscaDivida_AdpterSpiner();
            funcoesContReaisPorDataCliente();
            fechaConexao();

            ArrayAdapter<String> adapter_spinner_NomeDivida_Rec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
            spinnerNomeClientePorData.setAdapter(adapter_spinner_NomeDivida_Rec);


        } else if (checkDivRecTodos.isChecked()){

            criarConexao();
            buscaDividaAdpterTodas();
            fechaConexao();

            if(checkSpinneTodasPorCliente.isChecked()){

                criarConexao();
                buscaDividaAdpterTodasPorCliente();
                fechaConexao();

                adapterspinnerNomeclienteTodasDivRec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                        dataBase.buscarNomeClienteSppinerTodosRec());
                spinnerNomeClienteTodos.setAdapter(adapterspinnerNomeclienteTodasDivRec);

            }
        }
    }

    public void  filtrarDividaPorData(){
        List<Receber> dados = crud_dividasAReceber.buscarData(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void  filtrarDividaPorDataPg(){
        List<Receber> dados = crud_dividasAReceber.buscarDataPagas(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void  filtrarDividaPorDataAbertas(){
        List<Receber> dados = crud_dividasAReceber.buscarDataAbertas(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }

    public void  buscaDivida_AdpterSpiner(){
        List<Receber> dados2 = crud_dividasAReceber.buscarTodosSpinner(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados2);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void  buscaDivida_AdpterSpinerPg(){
        List<Receber> dados2 = crud_dividasAReceber.buscarTodosSpinnerPago(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados2);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void  buscaDivida_AdpterSpinerAbertas(){
        List<Receber> dados2 = crud_dividasAReceber.buscarTodosSpinnerAbertas(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados2);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }

    public void buscaDividaAdpterTodas(){
        List<Receber> dados = crud_dividasAReceber.buscarTodos();
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void buscaTodasDividaAdpterPg(){
        List<Receber> dados = crud_dividasAReceber.buscarTodosPagos();
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void buscaTodasDividaAdpterAbertas(){
        List<Receber> dados = crud_dividasAReceber.buscarTodasAbertas();
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }

    public void buscaDividaAdpterTodasPorCliente(){
        List<Receber> dados = crud_dividasAReceber.buscarTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void buscaTodasDividaAdpterPgPorCliente(){
        List<Receber> dados = crud_dividasAReceber.buscarTodosPorClientePago(spinnerNomeClienteTodos.getSelectedItem().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }
    public void buscaTodasDividaAdpterAbertasPorCliente(){
        List<Receber> dados = crud_dividasAReceber.buscarTodosPorClienteAberto(spinnerNomeClienteTodos.getSelectedItem().toString());
        adapterDividasAReceber = new AdapterDividasAReceber(this, dados);
        listDividas_Rec.setAdapter(adapterDividasAReceber);
    }

    // totais dos textviews
    public void func_ContPGPorData(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){
            txtContPG_Rec.setText("("+dataBase.totalPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGPordata(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG_Rec.setText("R$0.00");
        } else{
            txtTotalPG_Rec.setText("R$"+dataBase.somarPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_ContAbertoPordata(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){
            txtContAberto_Rec.setText("("+dataBase.totalAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoPordata(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto_Rec.setText("R$0.00");
        } else{
            txtTotalAberto_Rec.setText("R$"+dataBase.somarAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_totalContPorData(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){
            txtTotalContRec.setText("("+dataBase.totalContPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisPorData(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReaisRec.setText("R$0.00");
        } else{
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
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

    public void func_ContPGPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalPG_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){

            txtContPG_Rec.setText("("+dataBase.totalPG_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString()
                    , txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");

        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarPG2_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){

            txtTotalPG_Rec.setText("R$0.00");
        } else{
            txtTotalPG_Rec.setText("R$"+dataBase.somarPG2_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_ContAbertoPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalAberto_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){
            txtContAberto_Rec.setText("("+dataBase.totalAberto_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarAberto2_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){
            txtTotalAberto_Rec.setText("R$0.00");
        } else{
            txtTotalAberto_Rec.setText("R$"+dataBase.somarAberto2_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_totalContPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){

            txtTotalContRec.setText("("+dataBase.totalContPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");

        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){

            txtTotalReaisRec.setText("R$0.00");
        } else{
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }

    public void funcoesContReaisPorDataCliente(){
        func_ContPGPorDataCliente();
        func_TotalPGPorDataCliente();
        func_ContAbertoPorDataCliente();
        func_TotalAbertoPorDataCliente();
        func_totalContPorDataCliente();
        func_totalReaisPorDataCliente();

    }

    public void func_ContPGTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalPG_RecTodos() != null){
            txtContPG_Rec.setText("("+dataBase.totalPG_RecTodos()+")");
        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarPG_RecTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG_Rec.setText("R$0.00");
        } else{
            txtTotalPG_Rec.setText("R$"+dataBase.somarPG_RecTodos());
        }
    }
    public void func_ContAbertoTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalAberto_RecTodos() != null){
            txtContAberto_Rec.setText("("+dataBase.totalAberto_RecTodos()+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarAberto_RecTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto_Rec.setText("R$0.00");
        } else{
            txtTotalAberto_Rec.setText("R$"+dataBase.somarAberto_RecTodos());
        }
    }
    public void func_totalContTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContTodosDivRec() != null){
            txtTotalContRec.setText("("+dataBase.totalContTodosDivRec()+")");
        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisTodos(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisTodosDivRec() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReaisRec.setText("R$0.00");
        } else{
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisTodosDivRec());
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

    public void func_ContPGSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){

            txtContPG_Rec.setText("("+dataBase.totalPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString())+")");

        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){

            txtTotalPG_Rec.setText("R$"+dataBase.somarPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()));
        } else{
            txtTotalPG_Rec.setText("R$0.00");
        }
    }
    public void func_ContAbertoSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){
            txtContAberto_Rec.setText("("+dataBase.totalAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString())+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.somarAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){

            txtTotalAberto_Rec.setText("R$"+dataBase.somarAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()));
        } else{
            txtTotalAberto_Rec.setText("R$0.00");

        }
    }
    public void func_totalContSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalContTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){
            txtTotalContRec.setText("("+dataBase.totalContTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString())+")");
        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(this);
        if(dataBase.totalReaisTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString()));

        } else{
            txtTotalReaisRec.setText("R$0.00");
        }
    }

    public void funcoesContReaisSpinnerTodosPorCliente(){
        func_ContPGSpinnerTodosPorCliente();
        func_TotalPGSpinnerTodosPorCliente();
        func_ContAbertoSpinnerTodosPorCliente();
        func_TotalAbertoSpinnerTodosPorCliente();
        func_totalContSpinnerTodosPorCliente();
        func_totalReaisSpinnerTodosPorCliente();
    }

    public void trazerUpdate(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();

        receber = new Receber();

        if ((bundle != null) && (bundle.containsKey("ITEM_DIV_REC"))){

            findViewById(update_divida_rec).setVisibility(View.VISIBLE);
            findViewById(princi_div_rec).setVisibility(View.GONE);
            findViewById(R.id.btnSalvar_Div_Rec).setVisibility(View.GONE);
            findViewById(R.id.textEdit_Div_Rec).setVisibility(View.VISIBLE);

            findViewById(R.id.btnEdit_Div_Rec).setVisibility(View.VISIBLE);
            findViewById(R.id.textAdd_Div_Rec).setVisibility(View.GONE);
            findViewById(R.id.lineraLParc_Rec).setVisibility(View.GONE);

            receber = (Receber) bundle.getSerializable("ITEM_DIV_REC");

            edtNovaList_Div_Rec.setText(receber.NOMEDIV_REC);
            edtNomeCliente_Div_Rec.setText(receber.NOMECLIENTE);
            edtValor_Div_Rec.setText(receber.VALORDIV_REC);
            edtDataVenc_Div_Rec.setText(receber.DATAVENC_REC);
            edtDataDiv_Rec.setText(receber.DATADIV_REC);
            checkB_DiV_Rec.setChecked(receber.PG_REC);

        }
    }

    public void ClickAltomatico_AddDivida() {

        btnSalvarParc_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCamposParcela() == false) try {

                    if (receber.ID >= 0) {
                        criarConexao();
                        crud_dividasAReceber.inserir(receber);
                        adapterDividasAReceber.adicionarCliente(receber);

                    }

                    ArrayAdapter<String> adapter_spinner_NomeDivida_Rec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            dataBase.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                    spinnerNomeClientePorData.setAdapter(adapter_spinner_NomeDivida_Rec);

                    spinnerNomeClientePorData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            criarConexao();
                            buscaDivida_AdpterSpiner();

                            funcoesContReaisPorDataCliente();
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
            crud_dividasAReceber = new Crud_DividasAReceber(conexao);

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
            crud_dividasAReceber = new Crud_DividasAReceber(conexao);
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
        String NOMEDIV = edtNovaList_Div_Rec.getText().toString();
        String NOMECLIENTE = edtNomeCliente_Div_Rec.getText().toString();
        String VALORDIV =  edtValor_Div_Rec.getText().toString();
        String DATAVENC = edtDataVenc_Div_Rec.getText().toString();
        String DATADIV = edtDataDiv_Rec.getText().toString();
        String NUMEROPARC = edtParcelasDiv_Rec.getText().toString();
        boolean PG = checkB_DiV_Rec.isChecked();

        receber.NOMEDIV_REC = NOMEDIV;
        receber.NOMECLIENTE = NOMECLIENTE;
        receber.VALORDIV_REC = VALORDIV;
        receber.DATAVENC_REC = DATAVENC;
        receber.DATADIV_REC = DATADIV;
        receber.PG_REC = PG;

        if(res = isCampoVazio(NOMEDIV)){
            edtNovaList_Div_Rec.requestFocus();
        }else if(res = isCampoVazio(NOMECLIENTE)){
            edtNomeCliente_Div_Rec.requestFocus();
        }else if(res = isCampoVazio(VALORDIV)){
            edtValor_Div_Rec.requestFocus();
        }else if(res = isCampoVazio(DATAVENC)){
            edtDataVenc_Div_Rec.requestFocus();
        }else if(res = isCampoVazio(NUMEROPARC)){
            edtParcelasDiv_Rec.requestFocus();
        }
        if(res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage("Nome da dívida, nome do cliente, valor, vencimento e parcela são campos obrigatórios.");
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }
    private boolean validaCamposParcela(){
        boolean res = false;
        String NOMEDIV = edtNovaDivParc_Rec.getText().toString();
        String NOMECLIENTE = edtNomeCliente_Div_Rec.getText().toString();
        String VALORDIV =  edtValor_Div_Rec.getText().toString();
        String DATAVENC = edtDataMutiplicada_Rec.getText().toString();
        String DATADIV = edtDataDiv_Rec.getText().toString();
        boolean PG = checkB_DiV_Rec.isChecked();

        receber.NOMEDIV_REC = NOMEDIV;
        receber.NOMECLIENTE = NOMECLIENTE;
        receber.VALORDIV_REC = VALORDIV;
        receber.DATAVENC_REC = DATAVENC;
        receber.DATADIV_REC = DATADIV;
        receber.PG_REC = PG;

        if(res = isCampoVazio(NOMEDIV)){
            edtNovaList_Div_Rec.requestFocus();
        }else
        if (res = isCampoVazio(NOMECLIENTE)){
            edtNomeCliente_Div_Rec.requestFocus();
        } else
        if (res = isCampoVazio(VALORDIV)){
            edtValor_Div_Rec.requestFocus();
        } else
        if(res = isCampoVazio(DATAVENC)){
            edtDataVenc_Div_Rec.requestFocus();
        }
        if(res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage("Nome da divida, valor da divida e vencimento são campos obrigatorios");
            dlg.setNeutralButton(R.string.lbl_validacao_ok, null);
            dlg.show();
        }

        return res;
    }

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
        getMenuInflater().inflate(R.menu.menu_dividas_a_receber, menu);
        return true;
    }
    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case android.R.id.home:

                if (lista_interstitial_Rec != null) {
                    lista_interstitial_Rec.show(DividasAReceber.this);
                    lista_interstitial_Rec.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(DividasAReceber.this,MenuInicial.class));
                        }
                    });

                } else {
                    //Toast.makeText(DividasAReceber.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }

                break;

            case R.id.action_excluir_tudoDiv_Rec:

                dataBase = new DataBaseLista(this);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.itens_title_excluirtudoDiv)
                        .setMessage(R.string.item_message_extudoDiv)
                        .setPositiveButton(R.string.button_item_sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        criarConexao();
                                        crud_dividasAReceber.excluirTdosItens();
                                        if(checkSpinneNomeClientePorData.isChecked()){
                                            buscaDivida_AdpterSpiner();
                                            funcoesContReaisPorDataCliente();

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

                if (lista_interstitial_Rec != null) {
                    lista_interstitial_Rec.show(DividasAReceber.this);

                    lista_interstitial_Rec.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(DividasAReceber.this,MenuInicial.class));
                        }
                    });

                } else {
                    //Toast.makeText(DividasAReceber.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MenuInicial.class));
                    finishAffinity();
                }

                break;
            case salvarDividasBkp_Rec:

                pedirPermisos();

                if (lista_interstitial_SalvarArq_Rec != null) {
                    lista_interstitial_SalvarArq_Rec.show(DividasAReceber.this);

                } else {
                    //Toast.makeText(DividasAReceber.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                }

                criarConexao();

                crud_dividasAReceber.geraPlanilaDividaReceber();

                fechaConexao();

                AlertDialog.Builder builder = new AlertDialog.Builder(DividasAReceber.this);
                builder.setTitle("Aviso")
                        .setMessage(R.string.gerarArquivoDivida)
                        .setNegativeButton("OK", null)
                        .show();


                break;

            case importarDividasBkp_Rec:

                new AlertDialog.Builder(this)
                        .setTitle(R.string.aviso_importar_bkpdivida)
                        .setMessage(R.string.importar_bkpdivida)
                        .setPositiveButton(R.string.button_item_sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        pedirPermisos();

                                        if (div_interstitial_Rec != null) {
                                            div_interstitial_Rec.show(DividasAReceber.this);

                                        } else {
                                           // Toast.makeText(DividasAReceber.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                                        }

                                        ReadExcelFileNomeListZero2();
                                        ReadExcelFileNomeList2();

                                        criarConexao();
                                        if(checkSpinneNomeClientePorData.isChecked()){
                                            buscaDivida_AdpterSpiner();
                                            funcoesContReaisPorDataCliente();

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

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        File xlsxArquivoDividas = new File(pasta, "listadividasreceber.xlsx");

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

                    excelListDividasAReceber.insertExcelToSqliteDividas(dbAdapter, sheet1);

                    fechaConexao();

                    Toast.makeText (DividasAReceber.this, "Arquivo importado com sucesso.", Toast.LENGTH_LONG).show();

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

        File pasta = new File(root.getAbsolutePath());
        // pasta.mkdirs();

        for(int i = 0; i <= 100 ; i++) {

            File xlsxArquivoDividas = new File(pasta, "listadividasreceber-" + i + ".xlsx");

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

                    excelListDividasAReceber.insertExcelToSqliteDividas(dbAdapter, sheet1);

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
        if(ContextCompat.checkSelfPermission(DividasAReceber.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DividasAReceber.this, new String[]
                            {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
            AlertDialog.Builder builder = new AlertDialog.Builder(DividasAReceber.this);
            builder.setTitle("Aviso")
                    .setMessage("Permissões concedidas.Tente importar ou gerar o arquivo novamente.")
                    .setNegativeButton("OK", null)
                    .show();

        }
        return false;
    }

   /* private void configurarRecycler() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        filtrarDividaPorData();

        // Configurando um separador entre linhas, para uma melhor visualização.
       // listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerFiltro() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);
        // Adiciona o adapter que irá anexar os objetos à lista.
        //buscaDivida_Adpter();
        buscaDivida_AdpterSpiner();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerTodos() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaDividaAdpterTodas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerPagoTodos() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterPg();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoTodos() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterAbertas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerTodosPorCliente() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaDividaAdpterTodasPorCliente();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerPagoTodosPorCliente() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterPgPorCliente();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoTodosPorCliente() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaTodasDividaAdpterAbertasPorCliente();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerPagoSpinner() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaDivida_AdpterSpinerPg();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoSpinner() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        buscaDivida_AdpterSpinerAbertas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecyclerPagoPorData() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        filtrarDividaPorDataPg();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void configurarRecyclerAbertoPorData() {

        listDividas_Rec = (RecyclerView) findViewById(R.id.listDividas_Rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDividas_Rec.setLayoutManager(layoutManager);

        filtrarDividaPorDataAbertas();

        // Configurando um separador entre linhas, para uma melhor visualização.
        //listDividas_Rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/

}