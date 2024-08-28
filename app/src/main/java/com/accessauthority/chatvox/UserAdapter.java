package com.accessauthority.chatvox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.accessauthority.chatvox.databinding.ItemContainerUsersBinding;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHoolder> {

    private final List<User1> users;
    private final UserListener userListener;

    public UserAdapter(List<User1> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHoolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUsersBinding itemContainerUsersBinding = ItemContainerUsersBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        );
        return new UserViewHoolder(itemContainerUsersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoolder holder, int position) {
        holder.setUserData(users.get(position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHoolder extends RecyclerView.ViewHolder {
       ItemContainerUsersBinding binding;
       UserViewHoolder(ItemContainerUsersBinding itemContainerUsersBinding) {
           super(itemContainerUsersBinding.getRoot());
           binding = itemContainerUsersBinding;
       }
       void setUserData(User1 user1) {
           binding.txtName.setText(user1.name);
           binding.textEmail.setText(user1.email);
           binding.imageProfile.setImageBitmap(getUserImage(user1.image));
           binding.getRoot().setOnClickListener(view -> userListener.onUserClicked(user1));
       }
   }
   private Bitmap getUserImage(String encodedImage) {
       byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
       return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
   }
}
