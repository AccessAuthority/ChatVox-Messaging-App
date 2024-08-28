package com.accessauthority.chatvox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.accessauthority.chatvox.databinding.ActivityUserDetailBinding;

public class UserDetailActivity extends AppCompatActivity {

     private ActivityUserDetailBinding binding;
     private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
           setContentView(binding.getRoot());
           preferenceManager = new PreferenceManager(getApplicationContext());
           loaduser();
    }

    private void loaduser(){
        binding.txtName.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.textEmail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        binding.textnumber.setText(preferenceManager.getString(Constants.KEY_NUMBER));
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }
}