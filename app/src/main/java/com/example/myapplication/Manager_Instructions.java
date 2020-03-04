package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Business_Logic.Manager_Logic;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Manager_Instructions extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Manager_Logic mLogic = new Manager_Logic();
        setContentView(R.layout.activity_manager__instructions);
        context=this;
        final String manager_id = getIntent().getStringExtra("manager_id");
        mLogic.allocate_shares(manager_id);
        Button b = findViewById(R.id.no_audit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, MarketPlace.class);
                intent.putExtra("user_id", manager_id);
                context.startActivity(intent);
            }
        });
    }
}
