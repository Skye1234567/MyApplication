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

import Objects.Player;

public class Wait_Page extends AppCompatActivity {
    Integer num_participant_def;



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
        DatabaseReference reference_admin = FirebaseDatabase.getInstance().getReference();
        wait_for_settings_data(reference_admin);

        boolean not_all_signed_in = true;
        while(not_all_signed_in){



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

                if (p==num_participant_def) {
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
                // Transaction completed
                //Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

private void wait_for_settings_data(DatabaseReference r)
{
    //
}

}
