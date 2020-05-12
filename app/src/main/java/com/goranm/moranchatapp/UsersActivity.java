package com.goranm.moranchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.goranm.moranchatapp.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;

    private ArrayList<Users> arrayList;

    private FirebaseRecyclerOptions<Users> options;
    private FirebaseRecyclerAdapter<Users,FirebaseViewHolder> adapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.users_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(databaseReference, Users.class).build();

        adapter = new FirebaseRecyclerAdapter<Users, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull Users model) {

                holder.displayName.setText(model.getName());
                holder.status.setText(model.getStatus());
                Picasso.get().load(model.getImage()).into(holder.allUsersImage);

                final String user_id = getRef(position).getKey();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("user_id",user_id);
                        startActivity(profileIntent);
                    }
                });

            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(UsersActivity.this).inflate(R.layout.users_single_layout,parent,false));
            }
        };



        recyclerView.setAdapter(adapter);

    }


}
