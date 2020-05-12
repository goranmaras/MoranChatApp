package com.goranm.moranchatapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentId;
import com.goranm.moranchatapp.R;
import com.goranm.moranchatapp.model.Messages;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Messages> mMessageList;
    private LayoutInflater mInflater;
    private FirebaseAuth mAuth ;

    public MessageAdapter(List<Messages> mMessageList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.message_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();

        Messages c = mMessageList.get(position);
        String from_user = c.getFrom();

        if (from_user.equals(currentUserId)){

            holder.messageTextView.setBackgroundColor(Color.WHITE);
            holder.messageTextView.setTextColor(Color.BLACK);

        }else {
            holder.messageTextView.setBackgroundResource(R.drawable.message_text_background);
            holder.messageTextView.setTextColor(Color.WHITE);
        }
        holder.messageTextView.setText(c.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView messageTextView;
        public CircleImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text_layout);
            profileImage = itemView.findViewById(R.id.message_profile_image);
        }
    }
}
