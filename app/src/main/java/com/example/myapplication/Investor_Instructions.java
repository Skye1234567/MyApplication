package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Investor_Instructions extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_investor__instructions);
        final String investor_id = getIntent().getStringExtra("investor_id");


        Button b = findViewById(R.id.next2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MarketPlace.class);
                intent.putExtra("user_id", investor_id );
                context.startActivity(intent);
            }
        });
    }
}
