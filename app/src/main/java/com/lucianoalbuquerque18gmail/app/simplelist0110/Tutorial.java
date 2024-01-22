package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class Tutorial extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;

    public Button bnt_compr_tre;
    public Button btn_div;

    public String url1RelacaoLista = "https://www.youtube.com/watch?v=BqcYIwPS9gY";
    public String url2ListaCompras = "https://www.youtube.com/watch?v=-1L5ultyGHA";
    public String url3ListaTarefas = "https://www.youtube.com/watch?v=qnfdszs2b28";
    public String url4ListaDividas = "https://www.youtube.com/watch?v=mib1E6Jerxo";

    public ImageView imgV_Relacao_List;
    public ImageView imageV_List_Compras;
    public ImageView imageV_ListTarefa;
    public ImageView imageV_ListDividas;

    public LinearLayout linearLayout20;
    public LinearLayout linearLayout21;
    public LinearLayout linearLayout22;
    public LinearLayout linearLayout23;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tela_tutorial);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Modo de Usar");
        toolbar.setSubtitle(" Tutorial ListSoma");
        toolbar.setLogo(R.drawable.logo_barra_tuto);
        setSupportActionBar(toolbar);

        AdView mAdView4 = (AdView) findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView4.loadAd(adRequest);

        imgV_Relacao_List = (ImageView) findViewById(R.id.imgV_Relacao_List);
        imageV_List_Compras = (ImageView) findViewById(R.id.imageV_List_Compras);
        imageV_ListTarefa = (ImageView) findViewById(R.id.imageV_ListTarefa);
        imageV_ListDividas = (ImageView) findViewById(R.id.imageV_ListDividas);

        linearLayout20 = (LinearLayout) findViewById(R.id.linearLayout20);
        linearLayout21 = (LinearLayout) findViewById(R.id.linearLayout21);
        linearLayout22 = (LinearLayout) findViewById(R.id.linearLayout22);
        linearLayout23 = (LinearLayout) findViewById(R.id.linearLayout23);

        // Prepare the Interstitial Ad
    /*    interstitial = new InterstitialAd(Tutorial.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });

     */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void RelacaoListas(View view){
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url1RelacaoLista));
        startActivity(it);
    }

    public void L_Compras(View view){
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url2ListaCompras));
        startActivity(it);
    }

    public void L_Tarefa(View view){
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url3ListaTarefas));
        startActivity(it);
    }

    public void L_Dividas(View view){
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url4ListaDividas));
        startActivity(it);
    }


    @SuppressLint("NewApi")
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, MenuInicial.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MenuInicial.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            default:break;
        }

        return true;
    }

}
