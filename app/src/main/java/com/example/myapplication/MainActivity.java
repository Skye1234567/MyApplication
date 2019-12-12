package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import Objects.Market;
import Objects.Player;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Market m = new Market(0.9f, 0.6f, 0.8f);
        Button button = (Button) findViewById(R.id.next1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id = (EditText) findViewById(R.id.name);
                String name = id.getText().toString();
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                fb.getReference("Players").child(name).setValue(new Player(name));

            }
        });


    }
}



