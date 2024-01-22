package com.lucianoalbuquerque18gmail.app.simplelist0110;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.lucianoalbuquerque18gmail.app.simplelist0110.databinding.ActSplashBinding;
import com.lucianoalbuquerque18gmail.app.simplelist0110.databinding.ContentAddRelacaoListBinding;
import com.lucianoalbuquerque18gmail.app.simplelist0110.databinding.ContentSplashBinding;

public class SplashAct extends AppCompatActivity {

  public   AdView mAdView3;
  public ContentSplashBinding splashBinding;
  public ActSplashBinding binding;

  @RequiresApi(api = Build.VERSION_CODES.P)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.act_splash);

    binding = ActSplashBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(binding.toolbar);
    getSupportActionBar().hide();

    splashBinding = ContentSplashBinding.inflate(getLayoutInflater());
    setContentView(splashBinding.getRoot());

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

            startActivity(new Intent(SplashAct.this, MenuInicial.class));
            finish();

          }
        },1500);
      }
  }


