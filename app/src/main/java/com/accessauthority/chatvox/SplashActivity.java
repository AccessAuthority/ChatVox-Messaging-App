package com.accessauthority.chatvox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.accessauthority.chatvox.databinding.ActivitySplashBinding;
import com.accessauthority.chatvox.databinding.ActivityUserDetailBinding;

public class SplashActivity extends AppCompatActivity {

      private ActivitySplashBinding binding;
      private PreferenceManager preferenceManager;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivitySplashBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            preferenceManager = new PreferenceManager(getApplicationContext());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent home=new Intent(SplashActivity.this, Sign_inActivity.class);
                    startActivity(home);
                }
            },3000);
        }
    }