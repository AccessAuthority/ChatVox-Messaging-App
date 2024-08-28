package com.accessauthority.chatvox;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.accessauthority.chatvox.databinding.ActivityUserBinding;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class User_Activity extends BaseActivity  implements UserListener{
    private ActivityUserBinding binding;
    private PreferenceManager preferenceManager;
    private List<User1> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }
  private void setListeners(){
        binding.imgBack.setOnClickListener(this::onClick);
  }
    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null){
                        List<User1> users = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User1 user1 = new User1();
                            user1.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user1.number = queryDocumentSnapshot.getString(Constants.KEY_NUMBER);
                            user1.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user1.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user1.token= queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user1.id = queryDocumentSnapshot.getId();
                            users.add(user1);
                        }
                        if (users.size()>0){
                            UserAdapter userAdapter = new UserAdapter(users, this);
                            binding.userRecyclerView.setAdapter(userAdapter);
                            binding.userRecyclerView.setVisibility(View.VISIBLE);
                        }
                        else {
                            showErrorMessage();
                        }
                    }
                });
    }
    private void showErrorMessage(){
        binding.txtErrMsg.setText(String.format("%s","No user available"));
        binding.txtErrMsg.setVisibility(View.VISIBLE);
    }
    private void loading (Boolean isLoading) {
        if (isLoading){
            binding.progressbar.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressbar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User1 user1) {
        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user1);
        startActivity(intent);
        finish();
    }

    private void onClick(View view) {
        onBackPressed();
    }

}