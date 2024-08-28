package com.accessauthority.chatvox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.accessauthority.chatvox.databinding.ItemContainerRecentUserBinding;

import java.util.List;

public class RecentConversationsAdapter  extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder>{

    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  ConversionViewHolder(
                ItemContainerRecentUserBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
    holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {
        ItemContainerRecentUserBinding binding;
        ConversionViewHolder(ItemContainerRecentUserBinding itemContainerRecentUserBinding){
            super(itemContainerRecentUserBinding.getRoot());
            binding = itemContainerRecentUserBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversationImage(chatMessage.conversionImage));
            binding.txtName.setText(chatMessage.conversionName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(view -> {
                User1 user1 = new User1();
                user1.id = chatMessage.conversionID;
                user1.name = chatMessage.conversionName;
                user1.image = chatMessage.conversionImage;
                conversionListener.onConversionClicked(user1);
            });
        }
    }
    private Bitmap getConversationImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
