package project.Business_Logic;
import android.util.Log;

import project.Objects.Economics.Share;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;



public class Manager_Logic {
    private final static String TAG ="Manager_Logic";
    private DatabaseReference session_db_ref;

    private String company_symbol;
    private String manager_id;

    public Manager_Logic( String company_symbol, String manager_id, DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
        this.company_symbol = company_symbol;
        this.manager_id= manager_id;



    }



    public void  allocate_shares(){
        if (company_symbol==null)return;
        Query q = session_db_ref.child("Investors");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String investor_id = d.getKey();
                    session_db_ref.child("Shares").child(investor_id).child(company_symbol).setValue(new Share(investor_id,company_symbol, manager_id ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "******************************can't get the investors?***********************");

            }
        });





    }
}
