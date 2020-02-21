package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class Set_Parameters_Practice extends AppCompatActivity {
    Context context;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__parameters_practice);
        context=(Context) this;
        mauth = FirebaseAuth.getInstance();
        if (mauth.getCurrentUser().isAnonymous()|| mauth.getCurrentUser()==null){
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);
        };
    }
}
