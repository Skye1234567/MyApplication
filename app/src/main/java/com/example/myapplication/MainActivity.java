package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import Objects.Market;
import Objects.Player;
import Objects.Admin;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Market m = new Market(0.9f, 0.6f, 0.8f);
        Button button = (Button) findViewById(R.id.select_admin);
        Button button1 = (Button) findViewById(R.id.select_player);
        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //put admin auth stuff
               Intent intent = new Intent(getBaseContext(), Manager_Instructions.class);
        //intent.putExtra("PLAYER_ID", name);
        //startActivity(intent);
               }});


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put stuff for player auth
            }
        });


    }
}



