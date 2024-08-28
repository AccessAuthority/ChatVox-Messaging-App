package com.accessauthority.chatvox;


import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.accessauthority.chatvox.databinding.ReceivedMessageBinding;
import com.accessauthority.chatvox.databinding.SentMessageBinding;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private final List<ChatMessage> chatMessages;
    private  Bitmap receiverProfileImage;
    private  final String senderId;
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public  void setReceiverProfileImage(Bitmap bitmap){
        receiverProfileImage = bitmap;
    }

    public ChatAdapter( List<ChatMessage> chatMessages,Bitmap receiverProfileImage, String senderId) {
        this.receiverProfileImage = receiverProfileImage;
        this.chatMessages = chatMessages;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(SentMessageBinding.inflate(LayoutInflater.from(parent.getContext()),
            parent, false));
        }
       else {
           return new ReceiverMessageViewHolder(ReceivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()),
                   parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)== VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        }
        else {
            ((ReceiverMessageViewHolder) holder).setData(chatMessages.get(position),receiverProfileImage);
        }

    }


    @Override
    public int getItemCount()  {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
      if (chatMessages.get(position).senderId.equals(senderId)){
          return VIEW_TYPE_SENT;
      } else {
          return VIEW_TYPE_RECEIVED;
      }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private final SentMessageBinding binding;
        SentMessageViewHolder(SentMessageBinding sentMessageBinding){
            super(sentMessageBinding.getRoot());
            binding = sentMessageBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.txtSentMessage.setText(chatMessage.message);
            binding.txtDateTime.setText(chatMessage.dateTime);
        }
    }
static class ReceiverMessageViewHolder extends RecyclerView.ViewHolder{
        private final ReceivedMessageBinding binding;
        ReceiverMessageViewHolder(ReceivedMessageBinding receivedMessageBinding){
            super(receivedMessageBinding.getRoot());
            binding = receivedMessageBinding;
        }
        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
            binding.textReceiveMesaage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            if (receiverProfileImage != null){
                binding.imgProfile.setImageBitmap(receiverProfileImage);
            }
        }
}
   
}
