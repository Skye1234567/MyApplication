package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Player;

public class Wait_Page extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<Player> player_list;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait__page);
        Intent intent = getIntent();
        String player_id = intent.getStringExtra("player_id");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("player_list");
        Player current = new Player(player_id);
        db.child(player_id).setValue(current);
        
        boolean not_all_signed_in = true;
        while(not_all_signed_in){

            DatabaseReference ref = FirebaseDatabase .getInstance().getReference("parameters").child("num_participants");
            Query num_participants = ref;
            num_participants.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getValue();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }



}
