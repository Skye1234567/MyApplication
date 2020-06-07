package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import Objects.SimpleLoginHelper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Objects.Market;

public class MainActivity extends AppCompatActivity {
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;



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



