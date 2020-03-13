package com.example.myapplication.Business_Logic;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Objects.Manager;
import Objects.Share;
import androidx.annotation.NonNull;

public class Investor_Logic {
    private final static String TAG="INVESTOR_LOGIC";

    private String investor_id;

    public Investor_Logic(  String investor_id) {

       this.investor_id =investor_id;

    }


    public void allocate_shares(){


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        Query q = db.getReference("Managers");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    for (DataSnapshot manager :dataSnapshot.getChildren()){
                        String manager_id = manager.getKey();
                        String company_symbol = manager.getValue(Manager.class).getCompany_symbol();
                        FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(company_symbol).setValue(new Share(investor_id, manager_id));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "******************************can't get the investors?***********************");

            }
        });





    }
}
