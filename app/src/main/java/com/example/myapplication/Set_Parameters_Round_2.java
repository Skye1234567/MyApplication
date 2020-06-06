package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import Objects.Market;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Set_Parameters_Round_2 extends AppCompatActivity {
    Context context;
    FirebaseAuth mauth;
    EditText p;
    EditText pi_h;
    EditText pi_l;
    EditText rounds;
    Button submit_round_2;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__parameters_round_2);
        context= this;
        mauth = FirebaseAuth.getInstance();
        if (mauth.getCurrentUser().isAnonymous()|| mauth.getCurrentUser()==null){
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);
        }

        p = findViewById(R.id.prob_ofhigh2);
        pi_h = findViewById(R.id.prob_outcome_high2);
        pi_l = findViewById(R.id.prob_outcome_low2);
        rounds = findViewById(R.id.num_rounds2);
        submit_round_2 = findViewById(R.id.button_submit_round_2);

        submit_round_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float fp = Float.parseFloat(p.getText().toString());
                Float fpi_h = Float.parseFloat(pi_h.getText().toString());
                Float fpi_l = Float.parseFloat(pi_l.getText().toString());
                Integer num_round = Integer.parseInt(rounds.getText().toString());

                if (!(fp==null || fpi_h==null || fpi_l==null || num_round==null)) {
                    Market round_2 = new Market(fpi_h,fpi_l,fp, num_round);
                    round_2.setType("BUST");
                    db = FirebaseDatabase.getInstance();
                    db.getReference("markets").child("round_2").setValue(round_2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(context, Admin_Menu.class);
                            context.startActivity(intent);
                            finish();
                        }
                    });

                }
                else{
                    Toast.makeText(context, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }


}

