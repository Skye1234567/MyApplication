package com.example.myapplication.Business_Logic;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import Objects.Market;
import androidx.annotation.NonNull;
// leon walras



public class Accountant {
    private Integer performance;
    private Integer revenue;
    private ArrayList<String> aL;



    public Accountant() {
        revenue = 10;
        aL = new ArrayList();



    }


    public Integer getPerformance() {
        return performance;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void generate_company_data(Float percent_chance_high){
        Double value = Math.random();
        if(value<percent_chance_high) {
            performance = 1;

        }else performance =0;

    }
public void generate_round_data(Float percent_chance_profit_h, Float percent_chance_profit_l) {
    Double val = Math.random();
    if ((performance==1 && val < percent_chance_profit_h) || (performance==0 && val < percent_chance_profit_l)) {
        revenue = 50;
    }
}


public void reset_investor(final String investor_id){
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ref.child("Investors").child(investor_id).child("cash").setValue(100);
    ref.child("Investors").child(investor_id).child("value").setValue(0);
    ref.child("Shares").child(investor_id).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot d: dataSnapshot.getChildren()) {
                    aL.add(d.getKey());


            }
            reset_shares(investor_id);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


}
public void reset_shares(String investor_id){

        for (String s : aL){
        FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(s).child("number").setValue(10);
        FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(s).child("market_price").setValue(0);

}}

}
