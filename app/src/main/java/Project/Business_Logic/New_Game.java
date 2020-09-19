package Project.Business_Logic;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Project.Objects.Economics.Share;
import Project.Objects.Personel.Manager;
import androidx.annotation.NonNull;

public class New_Game implements Runnable {
    private String id;
    private DatabaseReference session_db_ref;




    public New_Game(String investorID,DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
        this.id = investorID;
    }

    @Override
    public void run() {
        allocate_shares();

    }

    public void  allocate_shares(){

        Query q = session_db_ref.child("Managers");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String manager_id = d.getKey();
                    Manager m = d.getValue(Manager.class);
                    String company_symbol = m.getCompany_symbol();
                   session_db_ref.child("Shares").child(id).child(company_symbol).setValue(new Share(id,company_symbol, manager_id ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
