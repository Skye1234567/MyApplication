package Objects;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Observable;
import java.util.Observer;


import androidx.annotation.NonNull;

public class Value_Assessor implements Runnable {


    private DatabaseReference ref_share_num;
    private DatabaseReference ref_cash;
    private Query ref_trades_buyer;
    private Trade complete;


    public Value_Assessor(@NonNull String investor_id) {


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ref_share_num = db.getReference("Shares").child(investor_id);

        ref_cash = db.getReference("Investors");
        ref_trades_buyer = db.getReference("Trades").child("Completed").orderByChild("buyer_id").equalTo(investor_id);


    }

    public void query_for_trades() {

        ref_trades_buyer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    complete = d.getValue(Trade.class);
                    FirebaseDatabase.getInstance().getReference("Trades").child("Completed").child(complete.getId()).setValue(null);
                    FirebaseDatabase.getInstance().getReference("Trades").child("Archive").child(complete.getId()).setValue(complete);

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
        IntegerDatabase SD = new IntegerDatabase(ref);
        SD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                new Thread(new Ledger((Integer)arg, complete.getNum_shares(), ref)).start();

            }
        });
        SD.updating();

    }



    public void update_seller_cash() {
        final DatabaseReference r = ref_cash.child(complete.getSeller_id()).child("cash");

        IntegerDatabase CD = new IntegerDatabase(r);
        CD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                new Thread(new Ledger((Integer)arg, complete.getPrice_point(), r)).start();
            }
        });
        CD.updating();


    }


    @Override
    public void run() {
        query_for_trades();

    }
}