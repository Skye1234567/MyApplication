package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Sign_up_admin extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_admin);


        EditText password = (EditText) findViewById(R.id.admin_password);

                                              // Perform action on key press
        EditText email = (EditText) findViewById(R.id.admin_email);
        mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
              .addOnCompleteListener(getParent(), new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information

                          FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                          Intent intent = new Intent(context, Set_Parameters.class);
                          intent.putExtra("player_id", user);
                          context.startActivity(intent);



                      } else {
                          // If sign in fails, display a message to the user.

                          Toast.makeText(Sign_up_admin.this, "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();
                      }
                  }
              });

    }
}



