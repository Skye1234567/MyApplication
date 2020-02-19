package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class Login_Admin extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__admin);


        final EditText password = (EditText) findViewById(R.id.admin_password);
        password.setOnKeyListener(new View.OnKeyListener() {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          // If the event is a key-down event on the "enter" button
                                          if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                              // Perform action on key press
                                              EditText email = (EditText) findViewById(R.id.admin_email);
                                              mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                                                      .addOnCompleteListener(getParent(), new OnCompleteListener<AuthResult>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<AuthResult> task) {
                                                              if (task.isSuccessful()) {
                                                                  // Sign in success, update UI with the signed-in user's information

                                                                  FirebaseUser user = mAuth.getCurrentUser();
                                                                  Intent intent = new Intent(context, Set_Parameters.class);
                                                                  context.startActivity(intent);



                                                              } else {
                                                                  // If sign in fails, display a message to the user.

                                                                  Toast.makeText(Login_Admin.this, "Authentication failed.",
                                                                          Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });
                                          }
                                          return true;
                                      }


                                  }
        );
    }
}




