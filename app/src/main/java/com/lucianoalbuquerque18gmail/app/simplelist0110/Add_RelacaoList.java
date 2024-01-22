package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter.ListaDeListasAdapter;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Item;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Lista_De_List;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.arquivos.ExcelItensCompras;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.arquivos.ExcelNomeLCompras;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaDeListReposi;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaRepositorio;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static android.app.PendingIntent.getActivity;

//import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.importCompras;


//import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.idcopialist;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.interstitial_HomeListCompr;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_home;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.importCompras_xlsx;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.importWhatsAppBusiness;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.principal;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.update;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.menuSair;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.action_excluir_tudo;
//import static com.lucianoalbuquerque18gmail.app.simplelist0110.R.id.idcopialist;

import static java.util.Calendar.getInstance;


//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Add_RelacaoList extends AppCompatActivity {

    public RecyclerView listRelaLists;
    private ConstraintLayout linearRelacaoListas;

    private Button butonVoltar_c;

    private ListaDeListasAdapter listaDeListasAdapter;

    public ListaDeListReposi listaDeListReposi;
    public ListaRepositorio listaRepositorio;
    public DataBaseLista dataBaseLista;
    public SQLiteDatabase conexaoList, conexao;

    public static EditText edtIdListAdd, edtNovaList, edtNovaList_D, edtId_D, edtId_Upd;
    private EditText edtComp, edtDataListDiv, edtDataListDiv_D;

    private ConstraintLayout constrLayCriaList;
    public Lista_De_List lista_de_list;

    private ImageButton buttonAdd;
    private ImageButton buttonEdit;

    private Button btnEdit_D;

    public Button Tutorial;

    private SharedPreferences preferencesT;
    public int contagemT = 0;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private ImageButton btnDataListDiv_D;

    public SharedPreferences preferences2;
    public SharedPreferences.Editor editor2;
    public int contagem_list_compras2 = 0;
    public Button contagemProgressiva2, btnCriaLista2;
    public TextView texto2;

    public static final int requestcode = 1;

    public String ultimoID;

    private Spinner Spinner_NomeList, Spinner_NomeList_ID;

    public List<Lista_De_List> listaListas = new ArrayList<>();
    public List<Item> itemimportlistacomp = new ArrayList<>();

    public static final int PERMISSION_REQUEST_MEMORY_ACCESS = 0;
    public static String fileType = "";
    public static String extensionXLXS = "XLXS";
    private static String extensionXLS = "XLS";
    public View mLayout;

    public TextView lbl;
    public DataBaseLista controller = new DataBaseLista(this);
    ActivityResultLauncher<Intent> filePicker;

    public ExcelNomeLCompras excelNomeLCompras;
    public ExcelItensCompras excelItensCompras;

    //public static MenuItem idcopialist;

    public TextView txtComoImportaList, txtCopiarLCompr;

    public InterstitialAd lista_interstitial, interstitial_import_compras, interstitial_compartilha_list_compras;

    public Context context;

    private final int CHOSE_FROM_DEVICE = 1001;

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


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_relacao_list);

        AdView mAdView3 = (AdView) findViewById(R.id.adView3);
        AdView adViewupdatRelaListCompras = (AdView) findViewById(R.id.adViewupdatRelaListCompras);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView3.loadAd(adRequest);
        adViewupdatRelaListCompras.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Todas as Listas");

        toolbar.setLogo(R.drawable.logo_barra);
        setSupportActionBar(toolbar);
        // preferences = getSharedPreferences("Toque", MODE_PRIVATE);
        //contagem_compr = preferences.getInt("contador", 0);

        AdRequest adRequest_intest = new AdRequest.Builder().build();
        /*lista_interstitial = new InterstitialAd(Add_RelacaoList.this);
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
                        //Toast.makeText(Add_RelacaoList.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        lista_interstitial = null;
                    }
                });

        AdRequest adRequest_import_compras = new AdRequest.Builder().build();

        InterstitialAd.load(this, getString(R.string.intersticial_importa_listadecompras), adRequest_import_compras,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitial_import_compras = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Toast.makeText(Add_RelacaoList.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        interstitial_import_compras = null;
                    }
                });

        preferences2 = getSharedPreferences("Toque", MODE_PRIVATE);
        contagem_list_compras2 = preferences2.getInt("contador", 0);
        editor2 = preferences2.edit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewById(update).setVisibility(View.GONE);

        // buttonClick_Conta();
        // contagemProgressiva.performClick();

        referenciaXML();

        criarConexao();
        //ALterTabelaRelaCompr();

        //pedirPermisos();
        permissaoCompartilhar();
        //compartilhaLista();
        verificaParametroList();
        verificaParametroListCopia();

        listRelaLists.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listRelaLists.setLayoutManager(linearLayoutManager);

        listaDeListReposi = new ListaDeListReposi(conexaoList);

        //btnAddNomeList.setOnClickListener(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        String currentDateTimeString = dateFormat.format(new Date());
        edtDataListDiv.setText(currentDateTimeString);
        btnDataListDiv_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Add_RelacaoList.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        edtDataListDiv_D.setText(mDia + "/" + (mMes + 1) + "/" + mAno);
                        //edtDataListDiv_D.setText((mMes + 1) + "/" + mAno);
                    }
                }, ano, mes, dia);

                datePickerDialog.show();

            }
        });

        edtDataListDiv_D.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH);
                    int ano = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(Add_RelacaoList.this, 0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                            edtDataListDiv_D.setText(mDia + "/" + (mMes + 1) + "/" + mAno);
                            //edtDataListDiv_D.setText((mMes + 1) + "/" + mAno);
                        }
                    }, ano, mes, dia);

                    datePickerDialog.show();
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCamposAdd() == false) try {

                    if (lista_de_list.ID >= 0) {

                        criarConexao();
                        listaDeListReposi.inserirLista(lista_de_list);
                        //listaDeListasAdapter.adicionarCliente(lista_de_list);

                        edtIdListAdd.setText(dataBaseLista.idIgualUm());

                        //System.out.println(dataBaseLista.idIgualUm());

                        Intent it = new Intent(Add_RelacaoList.this, Lista.class);
                        //it.putExtra("list", edtId.getText().toString());
                        startActivity(it);

                        //it.putExtra("listnome", edtNovaList.getText().toString());
                        //startActivity(it);

                        //  ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        //       .hideSoftInputFromWindow(edtNovaList.getWindowToken(), 0);

                        //edtNovaList.setText("");
                    }

                    //BuscarAdapter();
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

        btnEdit_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaCamposDialog() == false)
                    try {

                        if (lista_de_list.ID >= 0) {
                            criarConexao();
                            listaDeListReposi.alterarLista(lista_de_list);
                            //listaDeListasAdapter.atualizarNomeList(lista_de_list);
                            // BuscarAdapter();
                            // finish();

                            //edtNovaList.setText("");
                            findViewById(update).setVisibility(View.INVISIBLE);
                            BuscarAdapter();
                            fechaConexao();
                            //       atualizartela();
                            //finish();

                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(edtNovaList_D.getWindowToken(), 0);

                        }


                    } catch (SQLException ex) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(null);
                        dlg.setTitle("Erro");
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
                        dlg.show();
                    }
            }
        });

        BuscarAdapter();
        //configurarRecycler();
        PrimeiraLista();
        fechaConexao();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                txtComoImportaList.setVisibility(View.GONE);
                txtCopiarLCompr.setVisibility(View.GONE);

            }
        }, 20000);

    }

    public void referenciaXML() {
        edtNovaList = (EditText) findViewById(R.id.edtNovaList);
        //edtId = (EditText) findViewById(R.id.edtId);
        edtIdListAdd = (EditText) findViewById(R.id.edtIdListAdd);
        edtId_Upd = (EditText) findViewById(R.id.edtId_Upd);
        edtComp = (EditText) findViewById(R.id.edtComp);

        edtNovaList_D = (EditText) findViewById(R.id.edtNovaList_D);
        edtId_D = (EditText) findViewById(R.id.edtId_D);
        btnEdit_D = (Button) findViewById(R.id.btnEdit_D);
        edtDataListDiv_D = (EditText) findViewById(R.id.edtDataListDiv_D);

        txtComoImportaList = (TextView) findViewById(R.id.txtComoImportaList);
        txtCopiarLCompr = (TextView) findViewById(R.id.txtCopiarLCompr);

        buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);

        listRelaLists = (RecyclerView) findViewById(R.id.listRelaLists);

        linearRelacaoListas = (ConstraintLayout) findViewById(R.id.linearRelacaoListas);

        butonVoltar_c = (Button) findViewById(R.id.butonVoltar_c);

        edtDataListDiv = (EditText) findViewById(R.id.edtDataListDiv);

        btnDataListDiv_D = (ImageButton) findViewById(R.id.btnDataListDiv_D);
    }

    public void volta(View view) {
        findViewById(update).setVisibility(View.GONE);
    }

    public void PrimeiraLista() {
        if (listaDeListasAdapter.getItemCount() == 0) {
            findViewById(principal).setVisibility(View.GONE);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, List.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    public void BuscarAdapter() {
        List<Lista_De_List> rela = listaDeListReposi.buscarTodasListas();
        listaDeListasAdapter = new ListaDeListasAdapter(rela);
        listRelaLists.setAdapter(listaDeListasAdapter);
    }

    public void permissaoCompartilhar() {

        Bundle bundle = getIntent().getExtras();

        lista_de_list = new Lista_De_List();

        if ((bundle != null) && (bundle.containsKey("PERMISSAO_COMPART"))) {

            lista_de_list = (Lista_De_List) bundle.getSerializable("PERMISSAO_COMPART");

            pedirPermisos();

         /*   if (interstitial_compartilha_list_compras != null) {
                interstitial_compartilha_list_compras.show(Add_RelacaoList.this);
            } else {
                pedirPermisos();
                Toast.makeText(Add_RelacaoList.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
            }

          */

        }

    }

    private void compartilhaLista() {

        Bundle bundle = getIntent().getExtras();

        lista_de_list = new Lista_De_List();

        if ((bundle != null) && (bundle.containsKey("COMPART"))) {

            lista_de_list = (Lista_De_List) bundle.getSerializable("COMPART");

            //final File pasta = new File(Environment.getExternalStorageDirectory() + "/ListSomaCompartCompras");

            final File pasta = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
            criarConexaoC();

            edtComp.setText(String.valueOf(lista_de_list.ID));

            final String nomeLista = "NomelistCompras";
            //final String nomeLista = listaDeListReposi.nomeListaCompras(Integer.parseInt(edtComp.getText().toString()));

            final String nomeArquivo = "ItensListCompras";
            //final String nomeArquivo = "intens_da_lista_"+ listaDeListReposi.nomeListaCompras(Integer.parseInt(edtComp.getText().toString()));

            listaDeListReposi.geraPlanilhaNomeLista(Integer.parseInt(edtComp.getText().toString()));

            listaRepositorio.geraPlanila(Integer.parseInt(edtComp.getText().toString()));

            fechaConexaoC();

            AlertDialog.Builder enviaEmail = new AlertDialog.Builder(this);
            enviaEmail.setTitle("Compartilha lista de compras via WhatSapp");
            enviaEmail.setCancelable(false);

            LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            enviaEmail.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (lista_interstitial != null) {
                        lista_interstitial.show(Add_RelacaoList.this);
                    } else {
                        enviarEmail(pasta, nomeLista, nomeArquivo);
                    }
                    /*lista_interstitial.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            enviarEmail(pasta,nomeLista, nomeArquivo);
                        }
                    });*/
                }

            });
            enviaEmail.setNegativeButton("Cancelar", null);
            enviaEmail.create().show();

        }

    }

    private void compartilhaLista2() {

        final File pasta = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));

        criarConexaoC();

        edtComp.setText(String.valueOf(lista_de_list.ID));

        final String nomeLista = "NomelistCompras";
        //final String nomeLista = listaDeListReposi.nomeListaCompras(Integer.parseInt(edtComp.getText().toString()));

        final String nomeArquivo = "ItensListCompras";
        //final String nomeArquivo = "intens_da_lista_"+ listaDeListReposi.nomeListaCompras(Integer.parseInt(edtComp.getText().toString()));

        listaDeListReposi.geraPlanilhaNomeLista(Integer.parseInt(edtComp.getText().toString()));

        listaRepositorio.geraPlanila(Integer.parseInt(edtComp.getText().toString()));

        fechaConexaoC();

        AlertDialog.Builder enviaEmail = new AlertDialog.Builder(this);
        enviaEmail.setTitle("Compartilha lista de compras via WhatSapp");
        enviaEmail.setCancelable(false);

        LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        enviaEmail.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                enviarEmail(pasta, nomeLista, nomeArquivo);

                   /* if (lista_interstitial != null) {
                        lista_interstitial.show(Add_RelacaoList.this);
                    } else {
                        enviarEmail(pasta,nomeLista, nomeArquivo);
                    }*/

                    /*lista_interstitial.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            enviarEmail(pasta,nomeLista, nomeArquivo);
                        }
                    });*/
            }

        });
        enviaEmail.setNegativeButton("Cancelar", null);
        enviaEmail.create().show();

    }

    public void enviarEmail(File pasta, String nomeLista, String nomeArquivo) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setPackage("com.whatsapp");
        // intent.setPackage("com.whatsapp business");
        // intent.setData(Uri.parse("mailto:"));
        //intent.putExtra(Intent.EXTRA_SUBJECT, "ListSoma Lista de Compras" );
        intent.setType("text/text");

        Uri uri1 = Uri.parse("file://" + pasta + "/" + nomeLista + ".xlsx");
        Uri uri2 = Uri.parse("file://" + pasta + "/" + nomeArquivo + ".xlsx");

        ArrayList<Uri> arrayList = new ArrayList<Uri>();
        arrayList.add(uri1);
        arrayList.add(uri2);

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);

        startActivity(intent);
    }

    public void verificaParametroList() {

        Bundle bundle = getIntent().getExtras();

        lista_de_list = new Lista_De_List();

        if ((bundle != null) && (bundle.containsKey("LISTA_DE_LIST"))) {

            findViewById(update).setVisibility(View.VISIBLE);

            lista_de_list = (Lista_De_List) bundle.getSerializable("LISTA_DE_LIST");

            edtId_D.setText(String.valueOf(lista_de_list.ID));
            edtNovaList_D.setText(lista_de_list.NOMELISTA);
            edtDataListDiv_D.setText(lista_de_list.DATALISTA);

        }
    }

    public void verificaParametroListCopia() {

        Bundle bundle = getIntent().getExtras();

        lista_de_list = new Lista_De_List();

        if ((bundle != null) && (bundle.containsKey("LISTA_DE_LIST_COPIA"))) {

            lista_de_list = (Lista_De_List) bundle.getSerializable("LISTA_DE_LIST_COPIA");

            edtId_Upd.setText(String.valueOf(lista_de_list.ID));
            edtNovaList.setText(lista_de_list.NOMELISTA);
            edtDataListDiv.setText(lista_de_list.DATALISTA);

            if (validaCampos() == false) try {

                if (lista_de_list.ID > 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.logo_barra)
                            .setTitle(" ListSoma Lista de Compras")
                            .setMessage("Copiar lista?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    criarConexao();
                                    ListaDeListReposi repositorio = new ListaDeListReposi(conexaoList);
                                    repositorio.inserirListaC(lista_de_list);
                                    dataBaseLista.insertMultiplo(lista_de_list.ID);
                                    edtNovaList.setText("");
                                    BuscarAdapter();
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

    /*public void verificaParametroListCopia() {

         Bundle bundle = getIntent().getExtras();

          lista_de_list = new Lista_De_List();

          if (contagem_list_compras2 >= 1){
              idcopialist.setVisible(true);
          }

         if ((bundle != null) && (bundle.containsKey("LISTA_DE_LIST_COPIA"))) {

             lista_de_list = (Lista_De_List) bundle.getSerializable("LISTA_DE_LIST_COPIA");

             idcopialist.setVisible(true);

             edtId_Upd.setText(String.valueOf(lista_de_list.ID));
             edtNovaList.setText(lista_de_list.NOMELISTA);
             edtDataListDiv.setText(lista_de_list.DATALISTA);

             buttonClick_Conta();
             contagemProgressiva2.performClick();

         }

     }

     */
   /* public void buttonClick_Conta() {

        texto2 = (TextView)findViewById(R.id.texto);
        contagemProgressiva2 = (Button) findViewById(R.id.contagemProgressiva2);

        // contagem = preferences.getInt("contador", 0);
        texto2.setText("Contagem atual:"+ contagem_list_compras2);

        contagemProgressiva2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (contagem_list_compras2 == 1){
                    contagem_list_compras2 = contagem_list_compras2 - contagem_list_compras2;
                }


                contagem_list_compras2++;
                texto2.setText("Contagem atual: " + contagem_list_compras2);
                editor2.putInt("contador", contagem_list_compras2);
                editor2.commit();
            }
        });
    }
    */
    private void criarConexao() {
        try {
            dataBaseLista = new DataBaseLista(this);
            conexaoList = dataBaseLista.getWritableDatabase();
            listaDeListReposi = new ListaDeListReposi(conexaoList);

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
            listaDeListReposi = new ListaDeListReposi(conexaoList);
            dataBaseLista.close();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    private void criarConexaoC() {
        try {
            dataBaseLista = new DataBaseLista(this);
            conexao = dataBaseLista.getWritableDatabase();
            listaRepositorio = new ListaRepositorio(conexao);

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.text_action_ok_conexao, null);
            dlg.show();
        }
    }

    private void fechaConexaoC() {
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

    private boolean validaCampos() {

        boolean res = false;

        int ID = Integer.parseInt(edtId_Upd.getText().toString());
        String NOMELISTA = edtNovaList.getText().toString();
        String DATALISTA = edtDataListDiv.getText().toString();

        lista_de_list.ID = ID;
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

    private boolean validaCamposDialog() {
        boolean res = false;

        int ID = Integer.parseInt(edtId_D.getText().toString());
        String NOMELISTA = edtNovaList_D.getText().toString();
        String DATALISTA = edtDataListDiv_D.getText().toString();

        lista_de_list.ID = ID;
        lista_de_list.NOMELISTA = NOMELISTA;
        lista_de_list.DATALISTA = DATALISTA;

        if (res = isCampoVazio(NOMELISTA)) {
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

    private boolean validaCamposAdd() {
        boolean res = false;

        lista_de_list = new Lista_De_List();

        // int ID = Integer.parseInt(edtId.getText().toString());
        String NOMELISTA = edtNovaList.getText().toString();
        String DATALISTA = edtDataListDiv.getText().toString();


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

    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_rela_list, menu);
        //  idcopialist = menu.findItem(R.id.idcopialist);
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
                                        criarConexao();
                                        dataBaseLista.excluirtudaslistas();
                                        dataBaseLista.excluirtudo();
                                        fechaConexao();

                                        Intent it = new Intent(Add_RelacaoList.this, Add_RelacaoList.class);
                                        startActivity(it);
                                    }
                                })
                        .setNegativeButton(R.string.txt_listas_nao, null).show();

                break;

            case menuSair:
                finishAffinity();
                break;
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Lista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case importWhatsAppBusiness:
                pedirPermisosImport();

                if (lista_interstitial != null) {
                    lista_interstitial.show(Add_RelacaoList.this);
                } else {
                    ImportNomeListZeroWhatSappBusiness();
                    ImportNomeListWhatSappBusiness();
                }
               /* lista_interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        ImportNomeListZeroWhatSappBusiness();
                        ImportNomeListWhatSappBusiness();
                    }
                });*/

                criarConexao();
                BuscarAdapter();
                fechaConexao();
                break;

            case importCompras_xlsx:

                pedirPermisosImport();

                if (interstitial_import_compras != null) {
                    interstitial_import_compras.show(Add_RelacaoList.this);
                    ReadExcelFileNomeListZero2();
                    ReadExcelFileNomeList2();
                    criarConexao();
                    BuscarAdapter();
                    fechaConexao();

                } else {
                    ReadExcelFileNomeListZero2();
                    ReadExcelFileNomeList2();
                    criarConexao();
                    BuscarAdapter();
                    fechaConexao();
                    //Toast.makeText(Add_RelacaoList.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                }


                /*lista_interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        ReadExcelFileNomeListZero2();
                        ReadExcelFileNomeList2();

                    }
                });*/

               /* ReadExcelFileNomeListZero2();
                ReadExcelFileNomeList2();
                criarConexao();
                BuscarAdapter();
                fechaConexao();*/
                break;

            case action_home:
                if (interstitial_HomeListCompr != null) {
                    interstitial_HomeListCompr.show(Add_RelacaoList.this);

                    interstitial_HomeListCompr.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(Add_RelacaoList.this, MenuInicial.class));
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

        listRelaLists = (RecyclerView) findViewById(R.id.listRelaLists);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listRelaLists.setLayoutManager(layoutManager);
        // Adiciona o adapter que irá anexar os objetos à lista.
         BuscarAdapter();
        // Configurando um separador entre linhas, para uma melhor visualização.
        listRelaLists.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/

    public void ReadExcelFileNomeListZero() {

        File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Documents");
        //File root = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents");

        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        File xlsxFileAddresszeroNome = new File(pasta, "NomelistCompras.xlsx");
        File xlsxFileAddreszeroItem = new File(pasta, "ItensListCompras.xlsx");

        if (xlsxFileAddresszeroNome.exists()) {

            try {
                InputStream inStream;
                Workbook wb = null;

                try {
                    inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddresszeroNome));
                    wb = new XSSFWorkbook(inStream);

                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                DataBaseLista dbAdapter = new DataBaseLista(this);
                Sheet sheet1 = wb.getSheetAt(0);

                criarConexao();

                excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                fechaConexao();

                //dbAdapter.close();

                xlsxFileAddresszeroNome.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                InputStream inStream;
                Workbook wb = null;

                try {
                    inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddreszeroItem));

                    wb = new XSSFWorkbook(inStream);

                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                DataBaseLista dbAdapter = new DataBaseLista(this);
                Sheet sheet1 = wb.getSheetAt(0);

                criarConexaoC();

                excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                fechaConexaoC();

                //dbAdapter.close();

                xlsxFileAddreszeroItem.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (!xlsxFileAddresszeroNome.exists()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("AVISO")
                    .setMessage(R.string.msg_arquivo_não_existe)
                    .setNegativeButton("VOLTA", null)
                    .show();

            // Toast.makeText(this, R.string.msg_arquivo_não_existe, Toast.LENGTH_LONG).show();
        }

    }

    public void ReadExcelFileNomeList() {

        File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Documents");


        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        for (int i = 0; i <= 100; i++) {

            File xlsxFileAddressNome = new File(pasta, "NomelistCompras-" + i + ".xlsx");
            File xlsxFileAddressItens = new File(pasta, "ItensListCompras-" + i + ".xlsx");

            try {
                InputStream inStream;
                Workbook wb = null;

                try {
                    inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressNome));
                    wb = new XSSFWorkbook(inStream);

                    inStream.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                DataBaseLista dbAdapter = new DataBaseLista(this);
                Sheet sheet1 = wb.getSheetAt(0);

                criarConexao();

                excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                fechaConexao();

                //dbAdapter.close();

                xlsxFileAddressNome.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                InputStream inStream;
                Workbook wb = null;

                try {
                    inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressItens));

                    wb = new XSSFWorkbook(inStream);

                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                DataBaseLista dbAdapter = new DataBaseLista(this);
                Sheet sheet1 = wb.getSheetAt(0);

                criarConexaoC();

                excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                fechaConexaoC();

                //dbAdapter.close();

                xlsxFileAddressItens.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void ReadExcelFileNomeListZero2() {

       /* Intent it = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        it.addCategory(Intent.CATEGORY_OPENABLE);
        it.setType("/");
        startActivityForResult(it, CHOSE_FROM_DEVICE);*/

        //File cacheDir = new File(Environment.getExternalStorageDirectory() + File.separator + "");

      /* File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Documents");
        File root2 = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents");
        File root3 = new File(Environment.getExternalStorageDirectory() + "/WhatsApp Business/Media/WhatsApp Business Documents");

       */
        //File root = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents");

        File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Documents");
        File root2 = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents");


        File pasta = new File(root.getAbsolutePath());
        //pasta.mkdirs();

        File pasta2 = new File(root2.getAbsolutePath());
        //pasta2.mkdirs();


        File xlsxFileAddresszeroNome = new File(pasta, "NomelistCompras.xlsx");
        File xlsxFileAddreszeroItem = new File(pasta, "ItensListCompras.xlsx");

        File xlsxFileAddresszeroNome2 = new File(pasta2, "NomelistCompras.xlsx");
        File xlsxFileAddreszeroItem2 = new File(pasta2, "ItensListCompras.xlsx");

        if (root.exists()) {
            if (xlsxFileAddresszeroNome.exists()) {

                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddresszeroNome));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                    fechaConexao();

                    xlsxFileAddresszeroNome.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream inStream;
                    Workbook wb = null;


                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddreszeroItem));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexaoC();

                    excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                    fechaConexaoC();

                    xlsxFileAddreszeroItem.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (!xlsxFileAddresszeroNome.exists()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("AVISO")
                        .setMessage(R.string.msg_arquivo_não_existe)
                        .setNegativeButton("VOLTA", null)
                        .show();
            }
        } else if (root2.exists()) {
            if (xlsxFileAddresszeroNome2.exists()) {

                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddresszeroNome2));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                    fechaConexao();

                    xlsxFileAddresszeroNome2.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream inStream;
                    Workbook wb = null;


                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddreszeroItem2));
                        wb = new XSSFWorkbook(inStream);
                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);


                    criarConexaoC();

                    excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);


                    fechaConexaoC();

                    //dbAdapter.close();

                    xlsxFileAddreszeroItem2.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (!xlsxFileAddresszeroNome2.exists()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("AVISO")
                        .setMessage(R.string.msg_arquivo_não_existe)
                        .setNegativeButton("VOLTA", null)
                        .show();
            }
        }

    }

    public void ReadExcelFileNomeList2() {

        /*File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Documents");
        File root2 = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents");
        File root3 = new File(Environment.getExternalStorageDirectory() + "/WhatsApp Business/Media/WhatsApp Business Documents");     */

        File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Documents");
        File root2 = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents");


        File pasta = new File(root.getAbsolutePath());
        // pasta.mkdirs();

        File pasta2 = new File(root2.getAbsolutePath());
        //pasta2.mkdirs();

        for (int i = 0; i <= 100; i++) {

            File xlsxFileAddressNome = new File(pasta, "NomelistCompras-" + i + ".xlsx");
            File xlsxFileAddressItens = new File(pasta, "ItensListCompras-" + i + ".xlsx");

            File xlsxFileAddressNome2 = new File(pasta2, "NomelistCompras-" + i + ".xlsx");
            File xlsxFileAddressItens2 = new File(pasta2, "ItensListCompras-" + i + ".xlsx");

            if (root.exists()) {
                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressNome));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                    fechaConexao();

                    //dbAdapter.close();

                    xlsxFileAddressNome.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressItens));

                        wb = new XSSFWorkbook(inStream);

                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexaoC();

                    excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                    fechaConexaoC();

                    //dbAdapter.close();

                    xlsxFileAddressItens.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (root2.exists()) {
                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressNome2));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                    fechaConexao();

                    xlsxFileAddressNome2.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressItens2));

                        wb = new XSSFWorkbook(inStream);

                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexaoC();

                    excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                    fechaConexaoC();

                    xlsxFileAddressItens2.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            File root = new File(String.valueOf(data.getData()));

            File pasta = new File(root.getAbsolutePath());

            File xlsxFileAddresszeroNome = new File(root, "NomelistCompras.xlsx");
            File xlsxFileAddreszeroItem = new File(root, "ItensListCompras.xlsx");

            if (root.exists()) {

                if (xlsxFileAddresszeroNome.exists()) {

                    try {
                        InputStream inStream;
                        Workbook wb = null;

                        try {
                            inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddresszeroNome));
                            wb = new XSSFWorkbook(inStream);

                            inStream.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DataBaseLista dbAdapter = new DataBaseLista(this);
                        Sheet sheet1 = wb.getSheetAt(0);

                        criarConexao();

                        excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                        fechaConexao();

                        xlsxFileAddresszeroNome.delete();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        InputStream inStream;
                        Workbook wb = null;


                        try {
                            inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddreszeroItem));
                            wb = new XSSFWorkbook(inStream);

                            inStream.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DataBaseLista dbAdapter = new DataBaseLista(this);
                        Sheet sheet1 = wb.getSheetAt(0);

                        criarConexaoC();

                        excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                        fechaConexaoC();

                        xlsxFileAddreszeroItem.delete();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (!xlsxFileAddresszeroNome.exists()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("AVISO")
                            .setMessage(R.string.msg_arquivo_não_existe)
                            .setNegativeButton("VOLTA", null)
                            .show();
                }
            }

        }
    }

    public void ImportNomeListZeroWhatSappBusiness() {

       /* Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("gagt/sdf");
        try {
            startActivityForResult(fileintent, requestcode);
        } catch (ActivityNotFoundException e){
            Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
        }

        */

        File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp Business/Media/WhatsApp Business Documents");
        File root2 = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Business Documents");

        File pasta = new File(root.getAbsolutePath());

        File xlsxFileAddresszeroNome = new File(pasta, "NomelistCompras.xlsx");
        File xlsxFileAddreszeroItem = new File(pasta, "ItensListCompras.xlsx");

        if (root.exists()) {
            if (xlsxFileAddresszeroNome.exists()) {

                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddresszeroNome));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                    fechaConexao();

                    xlsxFileAddresszeroNome.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream inStream;
                    Workbook wb = null;


                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddreszeroItem));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexaoC();

                    excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                    fechaConexaoC();

                    xlsxFileAddreszeroItem.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (!xlsxFileAddresszeroNome.exists()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("AVISO")
                        .setMessage(R.string.msg_arquivo_não_existe)
                        .setNegativeButton("VOLTA", null)
                        .show();
            }
        } else if (root2.exists()) {
            if (xlsxFileAddresszeroNome.exists()) {

                try {
                    InputStream inStream;
                    Workbook wb = null;

                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddresszeroNome));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexao();

                    excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                    fechaConexao();

                    xlsxFileAddresszeroNome.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream inStream;
                    Workbook wb = null;


                    try {
                        inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddreszeroItem));
                        wb = new XSSFWorkbook(inStream);

                        inStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DataBaseLista dbAdapter = new DataBaseLista(this);
                    Sheet sheet1 = wb.getSheetAt(0);

                    criarConexaoC();

                    excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                    fechaConexaoC();

                    xlsxFileAddreszeroItem.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (!xlsxFileAddresszeroNome.exists()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("AVISO")
                        .setMessage(R.string.msg_arquivo_não_existe)
                        .setNegativeButton("VOLTA", null)
                        .show();
            }
        }

    }

    public void ImportNomeListWhatSappBusiness() {

        File root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp Business/Media/WhatsApp Business Documents");
        File root2 = new File(Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Business Documents");

        File pasta = new File(root.getAbsolutePath());

        for (int i = 0; i <= 100; i++) {

            File xlsxFileAddressNome = new File(pasta, "NomelistCompras-" + i + ".xlsx");
            File xlsxFileAddressItens = new File(pasta, "ItensListCompras-" + i + ".xlsx");

            if (root.exists()) {
                if (xlsxFileAddressNome.exists()) {
                    try {
                        InputStream inStream;
                        Workbook wb = null;

                        try {
                            inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressNome));
                            wb = new XSSFWorkbook(inStream);

                            inStream.close();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DataBaseLista dbAdapter = new DataBaseLista(this);
                        Sheet sheet1 = wb.getSheetAt(0);

                        criarConexao();

                        excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                        fechaConexao();

                        xlsxFileAddressNome.delete();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        InputStream inStream;
                        Workbook wb = null;

                        try {
                            inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressItens));

                            wb = new XSSFWorkbook(inStream);

                            inStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DataBaseLista dbAdapter = new DataBaseLista(this);
                        Sheet sheet1 = wb.getSheetAt(0);

                        criarConexaoC();

                        excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                        fechaConexaoC();

                        //dbAdapter.close();

                        xlsxFileAddressItens.delete();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (root2.exists()) {
                if (xlsxFileAddressNome.exists()) {
                    try {
                        InputStream inStream;
                        Workbook wb = null;

                        try {
                            inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressNome));
                            wb = new XSSFWorkbook(inStream);

                            inStream.close();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DataBaseLista dbAdapter = new DataBaseLista(this);
                        Sheet sheet1 = wb.getSheetAt(0);

                        criarConexao();

                        excelNomeLCompras.insertExcelToSqlite(dbAdapter, sheet1);

                        fechaConexao();

                        xlsxFileAddressNome.delete();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        InputStream inStream;
                        Workbook wb = null;

                        try {
                            inStream = getContentResolver().openInputStream(Uri.fromFile(xlsxFileAddressItens));

                            wb = new XSSFWorkbook(inStream);

                            inStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DataBaseLista dbAdapter = new DataBaseLista(this);
                        Sheet sheet1 = wb.getSheetAt(0);

                        criarConexaoC();

                        excelItensCompras.insertExcelToSqliteItemC(dbAdapter, sheet1);

                        fechaConexaoC();

                        //dbAdapter.close();

                        xlsxFileAddressItens.delete();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    public boolean pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if (ContextCompat.checkSelfPermission(Add_RelacaoList.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Add_RelacaoList.this);
            builder.setTitle("Aviso")
                    .setMessage("Se as permissões de acesso foram concedidas, tente compartilhar novamente.")
                    .setNegativeButton("OK", null)
                    .show();

            ActivityCompat.requestPermissions(Add_RelacaoList.this, new String[]
                            {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);

        } else {

            compartilhaLista2();
        }

        return false;


    }

    public boolean pedirPermisosImport() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if (ContextCompat.checkSelfPermission(Add_RelacaoList.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Add_RelacaoList.this);
            builder.setTitle("Aviso")
                    .setMessage("Se as permissões de acesso foram concedidas, tente importar novamente.")
                    .setNegativeButton("OK", null)
                    .show();

            ActivityCompat.requestPermissions(Add_RelacaoList.this, new String[]
                            {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);


        }

        return false;


    }

}

