package com.example.myapplication;

import Objects.Trade;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class MarketPlace extends AppCompatActivity {
    TextView a;
    TextView b;
    TextView high_price;
    TextView low_price;
    Spinner stocks;
    TextView e;
    TextView f;
    Button place_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();

        place_order = findViewById(R.id.place_order_button);

        stocks = findViewById(R.id.spinner_stocks);
        String current_company = stocks.getSelectedItem().toString();
        Query share = ref.child("shares"). child(current_company).orderByValue();
        share.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String high;
                String low;
                high=dataSnapshot.child("high").toString();
               low = dataSnapshot.child("low").toString();
               high_price.setText(high);
               low_price.setText(low);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query qa = ref.child("managers").orderByChild(a.getText().toString());
        Query qb = ref.child("managers").orderByChild(b.getText().toString());
        Query qc = ref.child("managers").orderByChild(b.getText().toString());
        Query qd = ref.child("managers").orderByChild(b.getText().toString());
        Query qe = ref.child("managers").orderByChild(b.getText().toString());
        Query qf = ref.child("managers").orderByChild(b.getText().toString());

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }
        });
    }
}
