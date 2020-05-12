package com.goranm.moranchatapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.goranm.moranchatapp.ChatActivity;
import com.goranm.moranchatapp.FirebaseViewHolder;
import com.goranm.moranchatapp.FriendsViewHolder;
import com.goranm.moranchatapp.ProfileActivity;
import com.goranm.moranchatapp.R;
import com.goranm.moranchatapp.UsersActivity;
import com.goranm.moranchatapp.model.Friends;
import com.goranm.moranchatapp.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private RecyclerView mFriendList;
    private View mMainView;

    private DatabaseReference mFriendDatabase;
    private DatabaseReference mUsersDatabse;
    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private ArrayList<Friends> arrayList;


    private FirebaseRecyclerOptions<Friends> options;
    private FirebaseRecyclerAdapter<Friends, FriendsViewHolder> adapter;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_friends, container, false);

        mFriendList = (RecyclerView) mMainView.findViewById(R.id.friend_list);
        mFriendList.setHasFixedSize(true);
        mFriendList.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendDatabase.keepSynced(true);

        mUsersDatabse = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabse.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Friends>()
                .setQuery(mFriendDatabase, Friends.class).build();

        adapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FriendsViewHolder holder, int position, @NonNull Friends model) {

                holder.displayDate.setText(model.getDate());

                final String list_user_id = getRef(position).getKey();

                mUsersDatabse.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();

                        if (dataSnapshot.hasChild("online")) {
                            boolean userOnline = (boolean)dataSnapshot.child("online").getValue();

                            if (userOnline){
                                holder.onlineImage.setVisibility(View.VISIBLE);
                            }else {
                                holder.onlineImage.setVisibility(View.INVISIBLE);
                            }
                        }

                        holder.displayName.setText(userName);
                        Picasso.get().load(image).into(holder.usersImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{"Open Profile","Send Message"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Select Options");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Click event for each item
                                        if (which == 0){
                                            Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                                            profileIntent.putExtra("user_id",list_user_id);
                                            startActivity(profileIntent);
                                        }else if (which == 1){

                                            Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                            chatIntent.putExtra("user_id",list_user_id);
                                            chatIntent.putExtra("user_name", userName);
                                            startActivity(chatIntent);

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FriendsViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.users_single_layout,parent,false));
            }
        };


        // Inflate the layout for this fragment
        mFriendList.setAdapter(adapter);

        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
