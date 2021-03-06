package project.business_logic;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;
import project.objects.database.IntegerDatabase;
import project.objects.economics.Session;
import project.objects.handlers.Ledger;
// leon walras



public class Accountant {
    private Integer performance;
    private Session session;
    private Integer revenue;
    private ArrayList<String> aL;
    private DatabaseReference reference;
    private IntegerDatabase IntData;
    private Ledger cash;



    public Accountant(DatabaseReference ref, Session session) {
        this.reference=ref;
        this.session = session;
        revenue = session.getSmall_payoff();
        aL = new ArrayList();
        IntData = new IntegerDatabase(reference.child("cash"));
        IntData.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Integer c = (Integer) arg;
                cash = new Ledger(c, 0, reference.child("cash"));

            }
        });
        IntData.updating();



    }


    public Integer getPerformance() {
        return performance;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void generate_company_data(Float percent_chance_high){
        Double value = Math.random();
        if(value<percent_chance_high) {
            performance = 1;

        }else performance =0;

        reference.child("performance").setValue(performance);

    }
public void generate_round_data(Float percent_chance_profit_h, Float percent_chance_profit_l) {
    Double val = Math.random();
    if ((performance == 1 && val < percent_chance_profit_h) || (performance == 0 && val < percent_chance_profit_l)) {
        revenue = session.getBig_payoff();
    }
    reference.child("profit").setValue(revenue);
    cash.setUpdate(revenue);
    new Thread(cash).start();


}
//TODO reset investor can be done with newgame
//the following functions are meant to "reset" all the investor data for a new game.
// It will be important for testing purposes and well as after the practice rounds are finished

public void reset_investor(final String investor_id){
    DatabaseReference ref = reference.getParent().getParent();
    ref.child("Investors").child(investor_id).child("cash").setValue(100);
    ref.child("Investors").child(investor_id).child("value").setValue(100);
    ref.child("Shares").child(investor_id).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot d: dataSnapshot.getChildren()) {
                    aL.add(d.getKey());


            }
            reset_shares(investor_id);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


}
public void reset_shares(String investor_id){

        for (String s : aL){
        reference.getParent().getParent().child("Shares").child(investor_id).child(s).child("number").setValue(10);
        reference.getParent().getParent().child("Shares").child(investor_id).child(s).child("market_price").setValue(0);

}}

}
