package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import Objects.Schedule;
import Objects.SessionTimeDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        context = this;


        FirebaseDatabase.getInstance().getReference("Time").setValue(new Schedule(System.currentTimeMillis(), 50000, 60000
        ));



        Button button = findViewById(R.id.select_admin);
        Button button1 = findViewById(R.id.select_player);
        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //put admin auth stuff
               Intent intent = new Intent(context, Sign_up_admin.class);
               context.startActivity(intent);
               finish();
               }});


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put stuff for player auth
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Intent intent = new Intent(context, Sign_up_player.class);
                context.startActivity(intent);
                finish();








            }
        });

    }


}



