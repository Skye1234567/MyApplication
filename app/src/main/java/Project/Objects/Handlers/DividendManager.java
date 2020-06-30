package Project.Objects.Handlers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import Project.Objects.Database.IntegerDatabase;
import Project.Objects.Economics.Share;
import androidx.annotation.NonNull;

public class DividendManager {
    private String company_symbol;
    private Integer dividend;
    private Integer cost;

    public DividendManager(String company_symbol, Integer dividend) {
        this.company_symbol = company_symbol;
        this.dividend = dividend;
        this.cost =0;


    }


    public void payDividends(){
        DatabaseReference q = FirebaseDatabase.getInstance()
                .getReference("Shares");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id;
                String manager=null;


                for (DataSnapshot d: dataSnapshot.getChildren()){
                    id = d.getKey();
                    for (DataSnapshot share: d.getChildren()){
                        Share s  = share.getValue(Share.class);
                        if (company_symbol.compareTo(s.getCompany())==0){
                            pay(id);
                            cost-=dividend;
                            manager=s.getManager_id();
                        }
                    }

                }
                if (manager!=null) incur_costs(manager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void  pay(String id){

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Investors")
                .child(id).child("cash");

        final IntegerDatabase ID = new IntegerDatabase( ref);
        ID.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Integer callback;
                callback = (Integer) arg;
                Ledger ledger = new Ledger(callback, dividend,ref);
                new Thread(ledger).start();
                ID.deleteObservers();
            }
        });
        ID.updating();




    }

    public void incur_costs(String manager){
        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Managers")
                .child(manager).child("cash");

        final IntegerDatabase ID2 = new IntegerDatabase( ref2);
        ID2.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Integer callback;
                callback = (Integer) arg;
                Ledger ledger = new Ledger(callback, cost,ref2);
                new Thread(ledger).start();
                ID2.deleteObservers();
            }
        });
        ID2.updating();


    }


}