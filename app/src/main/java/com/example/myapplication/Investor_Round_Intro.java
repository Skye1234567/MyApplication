package com.example.myapplication;

import Objects.Investor;
import Objects.ManAdapter;
import Objects.ManDatabase;
import Objects.Manager;
import Objects.Market;
import Objects.Schedule;
import Objects.Session;
import Objects.SessionDatabase;
import Objects.SessionTimeDatabase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.valueOf;

public class Investor_Round_Intro extends AppCompatActivity {

    TextView round_title;
    Context context;
    Investor i;
    long start_time;
    Session session;
    TimerTask timerTask;
    ManAdapter manAdapter;
    Integer round;
    ArrayList<Manager> managersArray;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start_time = System.currentTimeMillis();

        setContentView(R.layout.activity_investor__round__intro);
        String title = getIntent().getStringExtra("Title");


        String  s =title.split(" ")[1];
        round = valueOf(s);
        managersArray = new ArrayList<>();

        round_title = findViewById(R.id.round_title_i);
        round_title.setText(title);
        manAdapter = new ManAdapter(this, managersArray);
        listView = findViewById(R.id.list_of_companies);
        listView.setAdapter(manAdapter);



        context=this;

        timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(context, MarketPlace.class);
                intent.putExtra("investor", i);
                startActivity(intent);
                finish();

            }
        };

        getInvestor();
        SessionTimeDatabase SD = new SessionTimeDatabase();
        SD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Schedule s  =(Schedule) arg;
                Timer timer = new Timer();
                timer.schedule(timerTask, s.getReport());
            }
        });
        ManDatabase MD = new ManDatabase();
        MD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                managersArray = (ArrayList<Manager>) arg;
            }
        });
        MD.listenForChanges();








    }

    public void getInvestor(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Investors").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 i =dataSnapshot.getValue(Investor.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
