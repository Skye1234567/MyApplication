package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
        Market m = new Market(0.9f, 0.6f, 0.8f);
        Button button = (Button) findViewById(R.id.select_admin);
        Button button1 = (Button) findViewById(R.id.select_player);
        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //put admin auth stuff
               Intent intent = new Intent(context, Sign_up_admin.class);
               context.startActivity(intent);
               }});


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put stuff for player auth
                FirebaseAuth auth = FirebaseAuth.getInstance();


                auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            String player = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            if (player!=null && FirebaseAuth.getInstance().getCurrentUser().isAnonymous()){
                            Intent intent = new Intent(context, Wait_Page.class);
                            intent.putExtra("player_id", player);
                            context.startActivity(intent);
                            finish();}
                            else{
                                Toast.makeText(context, "Error in signing in", Toast.LENGTH_SHORT).show();
                            };
                        }
                        else{
                            Toast.makeText(context, "Error in signing in", Toast.LENGTH_SHORT).show();
                        };

                    }
                });






            }
        });

    }


}



