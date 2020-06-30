package Project.Business_Logic;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Project.Objects.Economics.Share;
import Project.Objects.Personel.Manager;
import androidx.annotation.NonNull;

public class New_Game implements Runnable {
    String id;


    public New_Game(String investorID) {
        this.id = investorID;
    }

    @Override
    public void run() {
        allocate_shares();

    }

    public void  allocate_shares(){


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        Query q = db.getReference("Managers");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String manager_id = d.getKey();
                    Manager m = d.getValue(Manager.class);
                    String company_symbol = m.getCompany_symbol();
                    FirebaseDatabase.getInstance().getReference("Shares").child(id).child(company_symbol).setValue(new Share(id,company_symbol, manager_id ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
