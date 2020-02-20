package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

public class Sign_up_player extends AppCompatActivity {
    Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_player);
        Button b = findViewById(R.id.signin);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseApp.initializeApp(context);
                FirebaseAuth auth = FirebaseAuth.getInstance();


                auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseApp.initializeApp(context);
                            String player = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Intent intent = new Intent(context, Wait_Page.class);
                            intent.putExtra("player_id", player);
                            context.startActivity(intent);
                        }
                        else{
                            Toast.makeText(Sign_up_player.this, "Error in signing in", Toast.LENGTH_SHORT).show();
                        };

                    }
                });






            }
        });




    }

}
