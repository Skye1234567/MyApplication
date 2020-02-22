package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import Objects.Session;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

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
import Objects.Player;

public class Wait_Page extends AppCompatActivity {

    Integer player_count;
    String player_count_database_def;
    String player_id_list_database_def;
    String count_database;
    Market market;
    DatabaseReference ref_def ;
    DatabaseReference ref_count;
    Session sess;
    Context context;

    Integer player_count_definition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = (Context)this;
        setContentView(R.layout.activity_wait__page);
        Intent intent = getIntent();
        String player_id = intent.getStringExtra("player_id");
        player_count_definition=1;
        sess = new Session(player_count_definition);
        player_count_database_def="player_count";
        player_id_list_database_def = "player_list";
        count_database = "player_counter";
        ref_def = FirebaseDatabase .getInstance().getReference(player_count_database_def);
        ref_count = FirebaseDatabase .getInstance().getReference(count_database);
        ref_count.setValue(0);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        Player current = new Player(player_id);

        Query markets = db.getReference("markets");
        Query player_list = db.getReference(player_id_list_database_def);

        markets.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                market = dataSnapshot.getValue(Market.class);
                if (market.getType().compareTo("P")==0){
                    sess.setPractice(market);
                }
                if (market.getType().compareTo("BOOM")==0){
                    sess.setBoom(market);
                }
                if (market.getType().compareTo("BUST")==0){
                    sess.setBust(market);}
                when_Session_configured();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                market = dataSnapshot.getValue(Market.class);
                if (market.getType().compareTo("P")==0){
                    sess.setPractice(market);
                }
                if (market.getType().compareTo("BOOM")==0){
                    sess.setBoom(market);
                }
                if (market.getType().compareTo("BUST")==0){
                    sess.setBust(market);}
                }


            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                market = dataSnapshot.getValue(Market.class);
                if (market.getType().compareTo("P")==0){
                    sess.setPractice(null);
                }
                if (market.getType().compareTo("BOOM")==0){
                    sess.setBoom(null);
                }
                if (market.getType().compareTo("BUST")==0){
                    sess.setBust(null);}



            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        player_list.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updatePlayerCountadd(ref_count, player_count_definition);
                when_Session_configured();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                updatePlayerCountsubtract(ref_count);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db.getReference(player_id_list_database_def).child(player_id).setValue(current);






    }

    private void when_Session_configured(){
        if (sess!=null){
        if (sess.isValid()&&player_count!=null &&player_count_definition!=null){
            Intent intent = new Intent(context, MarketPlace.class );
            context.startActivity(intent);
            finish();
        }}
    }



    private void updatePlayerCountsubtract(DatabaseReference player_counter) {
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
                // Transaction completed
                //Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    private void updatePlayerCountadd(DatabaseReference player_counter, final Integer player_count_definition) {
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
/*
private void wait_for_settings_data(DatabaseReference r)

{
    Query get_defined_player_num  = (Query) r;
    get_defined_player_num.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            player_count_definition= dataSnapshot.getValue(Integer.class);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}*/



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return false;
    }

}
