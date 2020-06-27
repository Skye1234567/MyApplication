package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import Objects.Session;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import Objects.Market;


public class Wait_Page extends AppCompatActivity  {
    private final static String TAG ="Wait_page_ACtivity";

    Integer player_count;

    String count_database;
    Market market;
    DatabaseReference ref_def ;
    DatabaseReference ref_count;
    DatabaseReference player_id_list_ref;
    Session sess;
    Context context;
    String current_user_id;



    Integer player_count_definition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        context = this;

        FirebaseDatabase.getInstance().getReference("ALLOW_TRADES").setValue(false);
        setContentView(R.layout.activity_wait__page);
        Intent intent = getIntent();
        String player_id = intent.getStringExtra("user_id");
        String player_count_database_def;
        String player_id_list_database_def = "player_list";
        count_database = "player_counter";
        current_user_id = player_id;
        FirebaseDatabase current_data= FirebaseDatabase.getInstance();

        player_id_list_ref = current_data.getReference(player_id_list_database_def);

        ref_count = current_data.getReference(count_database);
        ref_count.setValue(0);
        FirebaseDatabase db = FirebaseDatabase.getInstance();


       Query player_list = db.getReference(player_id_list_database_def);


        player_list.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(context," Adding players...", Toast.LENGTH_LONG).show();
                updatePlayerCountAdd(ref_count);



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                updatePlayerCountSubtract(ref_count);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });


    }

    private void updatePlayerCountSubtract(DatabaseReference player_counter) {
        player_counter.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer p;
                if ( mutableData==null) {
                    return Transaction.success(mutableData);
                }else {
                     p = mutableData.getValue(Integer.class);

                    p-=1;
                    if (p<0){p=0;}
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                player_count = p;
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                player_count= dataSnapshot.getValue(Integer.class);
                try {throw new Exception();}catch (Exception e ){}
                // Transaction completed
                //Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    private void updatePlayerCountAdd(DatabaseReference player_counter) {
        player_counter.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                if (mutableData==null) {
                    return Transaction.success(mutableData);
                }
                Integer p = mutableData.getValue(Integer.class);
                if (p == null) {
                    p=1;
                }else {
                    p+=1;

                }
                // Set value and report transaction success
                mutableData.setValue(p);
                player_count = p;
                when_sess_valid();
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

    private void when_sess_valid(){
        if (sess!=null){
        if (sess.isValid() &&player_count_definition.equals(player_count)){
            Intent intent = new Intent(context, MarketPlace.class);
            intent.putExtra("session", sess);
            intent.putExtra("user_id", current_user_id);
            startActivity(intent);
            finish();


        }}




    }







    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return keyCode == KeyEvent.KEYCODE_BACK;
    }

}
