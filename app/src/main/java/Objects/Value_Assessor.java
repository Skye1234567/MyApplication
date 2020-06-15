package Objects;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import androidx.annotation.NonNull;

public class Value_Assessor {


    private DatabaseReference ref_share_num;
    private DatabaseReference ref_cash;
    private Query ref_trades_buyer;

    private String company;


    public Value_Assessor(@NonNull String investor_id) {





        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ref_share_num = db.getReference("Shares").child(investor_id);

        ref_cash = db.getReference("Investors");
        ref_trades_buyer = db.getReference("Trades").child("Completed").orderByChild("buyer_id").equalTo(investor_id);
        query_for_trades();

    }

    public void query_for_trades() {

        ref_trades_buyer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    add_shares_bought( d.getValue(Trade.class));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void add_shares_bought(final Trade t) {


        ref_share_num.child(t.getCompany()).child("number").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer old_num = dataSnapshot.getValue(Integer.class);
                update_shares(old_num, t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}




    public void update_shares(Integer old_num, Trade complete) {

                    old_num += complete.getNum_shares();
                    update_seller_cash(complete.getSeller_id(),complete.getPrice_point());
                    FirebaseDatabase.getInstance().getReference("Trades").child("Completed").child(complete.getId()).setValue(null);
                    FirebaseDatabase.getInstance().getReference("Trades").child("Archive").child(complete.getId()).setValue(complete);
                    ref_share_num.child(complete.getCompany()).child("number").setValue(old_num);





    }

    public void update_seller_cash(String seller_id, final Integer price_point){
            ref_cash =ref_cash.child(seller_id).child("cash");
            ref_cash.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref_cash.setValue(dataSnapshot.getValue(Integer.class)+price_point);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}