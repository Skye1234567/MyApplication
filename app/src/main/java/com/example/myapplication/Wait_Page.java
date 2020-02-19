package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Market;
import Objects.Player;

public class Wait_Page extends AppCompatActivity {

    Market Game_Market;
    Integer player_count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait__page);
        Intent intent = getIntent();
        String player_id = intent.getStringExtra("player_id");


        DatabaseReference db = FirebaseDatabase.getInstance().getReference("player_list");
        Player current = new Player(player_id);
        db.child(player_id).setValue(current);
        DatabaseReference ref = FirebaseDatabase .getInstance().getReference("parameters").child("num_participants");
        updatePlayerCount(ref);
        DatabaseReference reference_admin = FirebaseDatabase.getInstance().getReference("settings");
        while(Game_Market ==null){wait_for_settings_data(reference_admin);}
        Integer num_participant_def = Game_Market.getNum_players();
        while(player_count<num_participant_def){



        }
    }
    private void updatePlayerCount(DatabaseReference player_counter) {
        player_counter.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer p = mutableData.getValue(Integer.class);
                if (p == null) {
                    p=1;
                }

                if (p==Game_Market.getNum_players()) {
                    return Transaction.success(mutableData);

                } else {
                   p+=1;
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                player_count= dataSnapshot.getValue(Integer.class);
                // Transaction completed
                //Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

private void wait_for_settings_data(DatabaseReference r)

{
    Query get_settings = r;
    get_settings.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Game_Market =dataSnapshot.getValue(Market.class);



        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}

}
