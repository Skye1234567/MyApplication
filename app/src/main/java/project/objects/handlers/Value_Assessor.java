package project.objects.handlers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;
import project.objects.database.IntegerDatabase;
import project.objects.economics.Trade;

public class Value_Assessor implements Runnable {


    private DatabaseReference ref_share_num;
    private DatabaseReference ref_cash;
    private Query ref_trades_buyer;
    private Trade complete;
    private DatabaseReference session_db_ref;


    public Value_Assessor(@NonNull String investor_id, DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;


        DatabaseReference db = session_db_ref;
        ref_share_num = db.child("Shares").child(investor_id);

        ref_cash = db.child("Investors");
        ref_trades_buyer = db.child("Trades").child("Completed").orderByChild("buyer_id").equalTo(investor_id);


    }

    public void query_for_trades() {

        ref_trades_buyer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    complete = d.getValue(Trade.class);
                   session_db_ref.child("Trades").child("Completed").child(complete.getId()).setValue(null);
                   session_db_ref.child("Trades").child("Archive").child(complete.getId()).setValue(complete);

                    update_buyer_shares();
                    update_seller_cash();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void update_buyer_shares( ) {


        final DatabaseReference ref = ref_share_num.child(complete.getCompany()).child("number");
        final IntegerDatabase SD = new IntegerDatabase(ref);
        SD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                new Thread(new Ledger((Integer)arg, complete.getNum_shares(), ref)).start();
                SD.deleteObservers();
            }
        });
        SD.updating();

    }



    public void update_seller_cash() {
        final DatabaseReference r = ref_cash.child(complete.getSeller_id()).child("cash");

        final IntegerDatabase CD = new IntegerDatabase(r);
        CD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                new Thread(new Ledger((Integer)arg, complete.getPrice_point()*complete.getNum_shares(), r)).start();
                CD.deleteObservers();
            }
        });
        CD.updating();


    }


    @Override
    public void run() {
        query_for_trades();

    }
}