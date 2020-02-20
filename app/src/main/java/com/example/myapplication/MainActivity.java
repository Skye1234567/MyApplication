package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Objects.Market;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Context context = this;
        Market m = new Market(0.9f, 0.6f, 0.8f);
        Button button = (Button) findViewById(R.id.select_admin);
        Button button1 = (Button) findViewById(R.id.select_player);
        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //put admin auth stuff
               Intent intent = new Intent(context, Login_Admin.class);
               context.startActivity(intent);
               }});


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put stuff for player auth
                Intent intent = new Intent(context, Player_Sign_in.class);
                context.startActivity(intent);

            }
        });


    }
}



