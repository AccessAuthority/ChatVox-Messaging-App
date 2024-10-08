 package com.accessauthority.chatvox;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.accessauthority.chatvox.databinding.ActivitySignUpBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

 public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }

    private  void setListeners(){
        binding.signin.setOnClickListener(view -> onBackPressed());
        binding.btnContinue.setOnClickListener(view -> {
            if (isValidSignUpDetails()) {
                signUp();
            }
        });

        binding.layoutImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private void  showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
     private void signUp(){
       loading(true);
         FirebaseFirestore database = FirebaseFirestore.getInstance();
         HashMap<String, Object> user = new HashMap<>();
         user.put(Constants.KEY_NAME, binding.name.getText().toString());
         user.put(Constants.KEY_EMAIL, binding.email.getText().toString());
         user.put(Constants.KEY_PASSWORD,binding.password.getText().toString());
         user.put(Constants.KEY_NUMBER, binding.number.getText().toString());
         user.put(Constants.KEY_IMAGE, encodedImage);
         database.collection(Constants.KEY_COLLECTION_USERS)
                 .add(user)
                 .addOnSuccessListener(DocumentReference -> {
                   loading(false);
                   preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_ID, true);
                   preferenceManager.putString(Constants.KEY_USER_ID, DocumentReference.getId());
                   preferenceManager.putString(Constants.KEY_NAME, binding.name.getText().toString());
                   preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
                   preferenceManager.putString(Constants.KEY_NUMBER, binding.number.getText().toString());
                   Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
                 })
                 .addOnFailureListener(exception -> {
                    loading( false);
                    showToast(exception.getMessage());
                 });
     }
     private String encodedImage (Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getHeight();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth, previewHeight, false);
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
         byte [] bytes = byteArrayOutputStream.toByteArray();
         return Base64.encodeToString(bytes, Base64.DEFAULT);
     }
     private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
             new ActivityResultContracts.StartActivityForResult(),
             result -> {
                 if (result.getResultCode() == RESULT_OK) {
                     if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imgProfile.setImageBitmap(bitmap);
                            binding.txtAddimg.setVisibility(View.GONE);
                            encodedImage = encodedImage (bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                     }
                 }
             }
     );

     private Boolean isValidSignUpDetails() {
      if (encodedImage == null) {
          showToast("Select profile image");
          return false;
      } else if (binding.name.getText().toString().trim().isEmpty()) {
          showToast("Enter name");
          return false;
      } else if (binding.email.getText().toString().trim().isEmpty()) {
          showToast("Enter email");
          return false;
      } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
          showToast("Enter valid email");
          return false;
      } else if (binding.password.getText().toString().trim().isEmpty()) {
          showToast("Enter password");
          return false;
      } else if (binding.number.getText().toString().trim().isEmpty()) {
          showToast("Enter your number");
          return false;
      }
      else {
          return true;
      }
     }
     private  void loading (Boolean isLoading) {
        if (isLoading) {
            binding.btnContinue.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
     }
}