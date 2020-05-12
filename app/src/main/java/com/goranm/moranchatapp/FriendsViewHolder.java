package com.goranm.moranchatapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsViewHolder extends RecyclerView.ViewHolder {

    public TextView displayDate;
    public TextView displayName;
    public CircleImageView usersImage;
    public ImageView onlineImage;

    public FriendsViewHolder(@NonNull View itemView) {
        super(itemView);
        displayDate = itemView.findViewById(R.id.user_single_status);
        displayName = itemView.findViewById(R.id.user_single_name);
        usersImage = itemView.findViewById(R.id.user_single_image);
        onlineImage = itemView.findViewById(R.id.users_single_online_icon);
    }

}
