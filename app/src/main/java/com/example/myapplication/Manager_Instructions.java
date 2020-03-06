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

        setContentView(R.layout.activity_manager__instructions);
        context=this;
        Intent intent = getIntent();
        final String manager_id = intent.getStringExtra("manager_id");
        final String company_symbol = intent.getStringExtra("company_symbol");
        Manager_Logic mLogic = new Manager_Logic( company_symbol, manager_id);
        mLogic.allocate_shares();
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
