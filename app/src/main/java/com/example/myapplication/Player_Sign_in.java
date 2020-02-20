package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

public class Player_Sign_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player__sign_in);
        Button b = findViewById(R.id.signin);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String player = auth.getCurrentUser().toString();

                auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("players").child(player);
                            Intent intent = new Intent(Player_Sign_in.this, Wait_Page.class);
                            intent.putExtra("player_id", player);
                            Player_Sign_in.this.startActivity(intent);
                        }
                        else{
                            Toast.makeText(Player_Sign_in.this, "Error in signing in", Toast.LENGTH_SHORT).show();
                        };

                    }
                });






            }
        });




    }

}
