package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Set_Parameters extends AppCompatActivity {
    FirebaseAuth mauth;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__parameters);
        context=(Context) this;
        mauth = FirebaseAuth.getInstance();
        if (mauth.getCurrentUser().isAnonymous()|| mauth.getCurrentUser()==null){
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);
        };
    }
}
