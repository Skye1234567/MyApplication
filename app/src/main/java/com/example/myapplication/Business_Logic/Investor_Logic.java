package com.example.myapplication.Business_Logic;

import android.util.Log;

import com.example.myapplication.Investor_Instructions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import Objects.Manager;
import Objects.Share;
import Objects.ShareAdapter;
import androidx.annotation.NonNull;

public class Investor_Logic {

    private final static String TAG="INVESTOR_LOGIC";
    private Investor_Instructions activity;
    private String investor_id;
    private HashMap<String, String> symbol_id;
    private ShareAdapter shareAdapter;

    public Investor_Logic(String investor_id, Investor_Instructions activity, ShareAdapter shareAdapter) {
       this.investor_id =investor_id;
       this.activity = activity;
       this.shareAdapter = shareAdapter;



    }



    public HashMap<String, String> getSymbol_id() {
        return symbol_id;
    }


    public void get_symbols(){
       symbol_id= new HashMap<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        Query q = db.getReference("Managers");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    for (DataSnapshot manager :dataSnapshot.getChildren()){
                        String manager_id = manager.getKey();
                        String company_symbol = manager.getValue(Manager.class).getCompany_symbol();
                        symbol_id.put(company_symbol,manager_id);

                    }

                }
               retrieve_investor_data(symbol_id);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "******************************can't get the investors?***********************");

            }
        });


    }




    public void retrieve_investor_data(HashMap<String, String> symbol_id) {
        Log.d(TAG, "IN RETRIEVE INVESTOR DATA");





        for (final String sym: symbol_id.keySet()){
            final String manager_val=symbol_id.get(sym);
            final Query shares =FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(sym);
            shares.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Share share = dataSnapshot.getValue(Share.class);
                    if (share==null){
                        DatabaseReference r = FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(sym);
                        r.setValue(new Share(investor_id,sym, manager_val ));
                        shareAdapter.add(new Share(investor_id,sym, manager_val ));

                    }else shareAdapter.add(share);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


         }
        //activity.set_table_values(investor_shares);



    }
}
