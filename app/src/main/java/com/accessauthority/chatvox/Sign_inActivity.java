package com.accessauthority.chatvox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.accessauthority.chatvox.databinding.ActivitySignInBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sign_inActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_ID)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.createAccount.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));
         binding.btnSignin.setOnClickListener(view -> {
             if (isValidSignInDetails()){
                 signIn();
             }
         });
    }
           private  void signIn(){
                 loading(true);
               FirebaseFirestore database = FirebaseFirestore.getInstance();
               database.collection(Constants.KEY_COLLECTION_USERS)
                       .whereEqualTo(Constants.KEY_EMAIL, binding.email.getText().toString())
                       .whereEqualTo(Constants.KEY_PASSWORD, binding.password.getText().toString())
                       .get()
                       .addOnCompleteListener(task -> {
                           if (task.isSuccessful() && task.getResult() != null
                               && task.getResult().getDocuments().size() > 0) {
                               DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                               preferenceManager .putBoolean(Constants.KEY_IS_SIGNED_ID, true);
                               preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                               preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                               preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                               preferenceManager.putString(Constants.KEY_NUMBER, documentSnapshot.getString(Constants.KEY_NUMBER));
                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               startActivity(intent);
                           } else {
                               loading(false);
                               showToast("Unable to sign in");
                           }
                       });
           }

    private  void loading (Boolean isLoading) {
        if (isLoading) {
            binding.btnSignin.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.btnSignin.setVisibility(View.VISIBLE);
        }
    }
    private void  showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private Boolean isValidSignInDetails(){
        if (binding.email.getText().toString().trim().isEmpty()){
           showToast("Enter email");
            return  false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
            showToast("Enter valid email");
            return false;
        } else if (binding.password.getText().toString().trim().isEmpty()) {
             showToast("Enter Password");
             return false;
        }
        else {
            return true;
        }
    }
}