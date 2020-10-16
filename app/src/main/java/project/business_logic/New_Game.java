package project.business_logic;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import project.objects.economics.Share;
import project.objects.personel.Manager;

public class New_Game implements Runnable {
    //set the cash and allocate the shares
    private String id;
    private DatabaseReference session_db_ref;




    public New_Game(String investorID,DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
        this.id = investorID;
    }

    @Override
    public void run() {
        allocate_shares();
        set_cash();


    }

    private void set_cash() {
        session_db_ref.child("markets").child("starting_sum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    session_db_ref.child("Investors").child(id).child("cash")
                            .setValue(snapshot.getValue(Integer.class));}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
