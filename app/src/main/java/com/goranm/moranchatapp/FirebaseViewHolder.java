package com.goranm.moranchatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView displayName, status;
    public CircleImageView allUsersImage;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        displayName = itemView.findViewById(R.id.user_single_name);
        allUsersImage = itemView.findViewById(R.id.user_single_image);
        status = itemView.findViewById(R.id.user_single_status);
    }
}
