package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import com.lucianoalbuquerque18gmail.app.simplelist0110.databinding.ActMenuBinding;
import com.lucianoalbuquerque18gmail.app.simplelist0110.databinding.ContentMenuBinding;
import com.lucianoalbuquerque18gmail.app.simplelist0110.databinding.ContentSplashBinding;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Lista_De_List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.drakeet.materialdialog.MaterialDialog;

public class MenuInicial extends AppCompatActivity {

    private MaterialDialog mMaterialDialog;

    public static InterstitialAd interstitial;
    public static InterstitialAd compr_interstitial;
    public static InterstitialAd tare_interstitial;
    public static InterstitialAd div_interstitial;

    public SharedPreferences preferences;
    public SharedPreferences preferences_tarefa;
    public SharedPreferences preferences_div;

    //public SharedPreferences.Editor editor;
    public int contagem_list_compras = 0;
    public int contagem_tarefa = 0;
    public int contagem_div = 0;

    private Lista_De_List lista_de_list;
    private String urlListSomaPro = "https://play.google.com/store/apps/details?id=com.lalp.sist.listsomapro";
    public ContentMenuBinding menuBinding;
    public ActMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_menu);

        binding = ActMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        menuBinding = ContentMenuBinding.inflate(getLayoutInflater());
        setContentView(menuBinding.getRoot());

        //pedirPermisos();
       /* MobileAds.initialize(MenuInicial.this,getString(R.string.app_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView3 = findViewById(R.id.adView3);
        mAdView3.loadAd(adRequest);*/

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //mAdView3 = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        menuBinding.adView3.loadAd(adRequest);

        /*AdRequest adRequest_intest = new AdRequest.Builder().build();
        compr_interstitial = new InterstitialAd(MenuInicial.this);
        compr_interstitial.setAdUnitId(getString(R.string.admob_interstitial_compras));
        compr_interstitial.loadAd(adRequest_intest);
        compr_interstitial = new InterstitialAd(this);*/

        //AdRequest adRequest_intest = new AdRequest.Builder().build();

       /* InterstitialAd.load(this,getString(R.string.admob_interstitial_compras), adRequest_intest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        compr_interstitial = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                      compr_interstitial = null;
                        Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();

                    }


                });*/

       // AdRequest adRequest_tarefa = new AdRequest.Builder().build();
        /*tare_interstitial = new InterstitialAd(MenuInicial.this);
        tare_interstitial.setAdUnitId(getString(R.string.admob_interstitial_tarefa));
        tare_interstitial.loadAd(adRequest_tarefa);*/

       /* InterstitialAd.load(this,getString(R.string.admob_interstitial_tarefa), adRequest_tarefa,
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
                        Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        tare_interstitial = null;
                    }
                });*/


        //AdRequest adRequest_div = new AdRequest.Builder().build();
        /*div_interstitial = new InterstitialAd(MenuInicial.this);
        div_interstitial.setAdUnitId(getString(R.string.admob_interstitial_div));
        div_interstitial.loadAd(adRequest_div);*/

     /*   InterstitialAd.load(this,getString(R.string.admob_interstitial_div), adRequest_div,
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
                        Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        div_interstitial = null;
                    }
                });*/

       // AdRequest adRequest_tuto = new AdRequest.Builder().build();
        /*interstitial = new InterstitialAd(MenuInicial.this);
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
        interstitial.loadAd(adRequest_tuto);*/

      /*  InterstitialAd.load(this,getString(R.string.admob_interstitial_id), adRequest_tuto,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitial = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        interstitial = null;
                    }
                });*/

        /*findViewById(R.id.linearLayout3).setVisibility(View.GONE);
        findViewById(R.id.scrollView2).setVisibility(View.GONE);
        findViewById(R.id.adView3).setVisibility(View.GONE);
        findViewById(R.id.linearLayout3).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
        findViewById(R.id.adView3).setVisibility(View.VISIBLE);

        texto = (TextView)findViewById(R.id.texto);
        texto_tarefa = (TextView)findViewById(R.id.texto_tarefa);
        texto_div = (TextView)findViewById(R.id.texto_div);

         */

       /* preferences = getSharedPreferences("Toque", MODE_PRIVATE);
        contagem_list_compras = preferences.getInt("contador", 0);
        texto.setText("C:"+ contagem_list_compras);

        preferences_tarefa = getSharedPreferences("Toque2", MODE_PRIVATE);
        contagem_tarefa = preferences_tarefa.getInt("contador2", 0);
        texto_tarefa.setText("T:"+ contagem_tarefa);

        preferences_div = getSharedPreferences("Toque3", MODE_PRIVATE);
        contagem_div = preferences_div.getInt("contador3", 0);
        texto_div.setText("D:"+ contagem_div);*/

        /*bus = (ImageView)findViewById(R.id.bus);
        bus1 = (ImageView)findViewById(R.id.bus1);
        cycle = (ImageView)findViewById(R.id.cycle);

        cardViewTutorial = (CardView) findViewById(R.id.cardViewTutorial);
        cardViewCompras = (CardView) findViewById(R.id.cardViewCompras);
        cardViewTarefa = (CardView) findViewById(R.id.cardViewTarefa);
        cardViewDivida = (CardView) findViewById(R.id.cardViewDivida);
        cardViewDivReceber = (CardView) findViewById(R.id.cardViewDivReceber);

        bus_Div = (ImageView)findViewById(R.id.bus_Div);
        bus_DivReceber = (ImageView)findViewById(R.id.bus_DivReceber);

        txtCompAmigos = (TextView)findViewById(R.id.txtCompAmigos);
        linearLayoutPago = (LinearLayout) findViewById(R.id.linearLayoutPago);

         */

        menuBinding.linearLayoutPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(urlListSomaPro));
                startActivity(it);
            }
        });

        menuBinding.cardViewCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuInicial.this,Lista.class));

              //if (contagem_list_compras == 0){
              /*      new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                    if (compr_interstitial != null) {
                        compr_interstitial.show(MenuInicial.this);

                        compr_interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                startActivity(new Intent(MenuInicial.this,Add_RelacaoList.class));
                            }
                        });

                    } else {
                        Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MenuInicial.this,Add_RelacaoList.class));
                    }
                        }
                    },4000);*/

            }
        });

        menuBinding.cardViewTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuInicial.this,List_t.class));

               // if (contagem_tarefa == 0){
                  /*  new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tare_interstitial != null) {
                                tare_interstitial.show(MenuInicial.this);

                                tare_interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        startActivity(new Intent(MenuInicial.this,Add_RelacaoLisTare.class));
                                    }
                                });

                            } else {
                                    Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(MenuInicial.this,Add_RelacaoLisTare.class);
                                    startActivity(it);
                                  }
                               }
                             },4000);*/

            }
        });

        menuBinding.cardViewDivida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuInicial.this,List_Dividas.class));

               // if (contagem_div == 0){
                    /*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (div_interstitial != null) {
                                div_interstitial.show(MenuInicial.this);

                                div_interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        startActivity(new Intent(MenuInicial.this,List_Dividas.class));
                                    }
                                });

                            } else {
                                Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                               Intent it = new Intent(MenuInicial.this,List_Dividas.class);
                               startActivity(it);
                            }
                        }
                    },4000);*/
            }
        });

        menuBinding.cardViewDivReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuInicial.this,DividasAReceber.class));

                // if (contagem_div == 0){
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (div_interstitial != null) {
                            div_interstitial.show(MenuInicial.this);

                            div_interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    startActivity(new Intent(MenuInicial.this,DividasAReceber.class));
                                }
                            });

                        } else {
                            Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(MenuInicial.this,DividasAReceber.class);
                            startActivity(it);
                        }
                    }
                },4000);*/
            }
        });

        menuBinding.cardViewTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuInicial.this,Tutorial.class));

                   /* if (interstitial != null) {
                        interstitial.show(MenuInicial.this);

                        interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                startActivity(new Intent(MenuInicial.this,Tutorial.class));
                            }
                        });
                    } else {

                        Toast.makeText(MenuInicial.this, "Falha na apresentação da propaganda.", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(MenuInicial.this, Tutorial.class);
                        startActivity(it);
                     }*/

            }
        });

    }

    public void enviarEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setData(Uri.parse("mailto:"));
        intent.setType("text/text");
        intent.putExtra(Intent.EXTRA_TEXT, "Baixe o app LISTSOMA e crie suas listas acesse o link da Play Store  https://bit.ly/listsoma_app_new" );

        startActivity(intent);
    }

    public void AderirVersPaga(View view){
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(urlListSomaPro));
        startActivity(it);
    }

}