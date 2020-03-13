package com.example.myapplication.Business_Logic;
import android.util.Log;

import Objects.Investor;

import Objects.Share;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.*;


import androidx.annotation.NonNull;



public class Manager_Logic {
    private final static String TAG ="Manager_Logic";

    private String company_symbol;
    private String manager_id;

    public Manager_Logic( String company_symbol, String manager_id) {
        this.company_symbol = company_symbol;
        this.manager_id= manager_id;

    }

    public void allocate_shares(){
        if (company_symbol==null)return;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        Query q = db.getReference("Investors");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String investor_id = d.getKey();
                     FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(company_symbol).setValue(new Share(investor_id, manager_id ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "******************************can't get the investors?***********************");

            }
        });





    }
}
