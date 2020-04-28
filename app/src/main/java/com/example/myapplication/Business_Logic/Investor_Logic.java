package com.example.myapplication.Business_Logic;

import android.util.Log;

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
import androidx.annotation.NonNull;

public class Investor_Logic {

    private final static String TAG="INVESTOR_LOGIC";

    private String investor_id;
    boolean complete_id;
    boolean complete_shares;
    HashMap<String, String> symbol_id;
    ArrayList<Share>  investor_shares;
    public Investor_Logic(String investor_id) {
       this.investor_id =investor_id;
       this.complete_id=false;
       this.complete_shares=false;
    }

    public boolean isComplete_id() {
        return complete_id;
    }

    public boolean isComplete_shares() {
        return complete_shares;
    }

    public HashMap<String, String> getSymbol_id() {
        return symbol_id;
    }

    public ArrayList<Share> getInvestor_shares() {
        return investor_shares;
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
                complete_id=true;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "******************************can't get the investors?***********************");

            }
        });


    }




    public void retrieve_investor_data(HashMap<String, String> symbol_id) {

     investor_shares = new ArrayList<>();



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
                        investor_shares.add(new Share(investor_id,sym, manager_val ));

                    }else investor_shares.add(share);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

         }
        complete_shares=true;

    }
}
