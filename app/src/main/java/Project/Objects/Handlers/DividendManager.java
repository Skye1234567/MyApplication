package Project.Objects.Handlers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import Project.Objects.Database.IntegerDatabase;
import Project.Objects.Economics.Share;
import androidx.annotation.NonNull;

public class DividendManager {
    String company_symbol;
    Integer dividend;

    public DividendManager(String company_symbol, Integer dividend) {
        this.company_symbol = company_symbol;
        this.dividend = dividend;
    }


    public void payDividends(){
        DatabaseReference q = FirebaseDatabase.getInstance()
                .getReference("Shares");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id;

                for (DataSnapshot d: dataSnapshot.getChildren()){
                    id = d.getKey();
                    for (DataSnapshot share: d.getChildren()){
                        Share s  = share.getValue(Share.class);
                        if (company_symbol.compareTo(s.getCompany())==0){
                            pay(id);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void  pay(String id){

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Investors")
                .child(id).child("cash");

        IntegerDatabase ID = new IntegerDatabase( ref);
        ID.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Integer callback;
                callback = (Integer) arg;
                Ledger ledger = new Ledger(callback, dividend,ref);
                new Thread(ledger).start();
            }
        });
        ID.updating();



    }
}
