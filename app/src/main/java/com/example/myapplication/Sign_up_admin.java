 package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Sign_up_admin extends AppCompatActivity {
    Context context;
    FirebaseAuth mAuth;
    EditText email;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_admin);
        context = Sign_up_admin.this;




        Button button = findViewById(R.id.sign_in_admin_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = findViewById(R.id.admin_password);
                email = findViewById(R.id.admin_email);
                String ps =password.getText().toString();
                String em = email.getText().toString();
                if (ps==null) {ps ="";}
                if(em==null){em="";}
                if ( 0!=ps.compareTo("") && 0!=em.compareTo("")) {
                    Activity activity = (Activity) context;
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(em, ps)
                            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        Intent intent = new Intent(context, Set_Parameters_Practice.class);
                                        intent.putExtra("player_id", user);
                                        context.startActivity(intent);


                                    } else {
                                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    //Toast.makeText(context, "Whats up",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}