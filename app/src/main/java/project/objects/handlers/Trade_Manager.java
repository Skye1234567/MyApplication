package project.objects.handlers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import project.objects.economics.Trade;

public class Trade_Manager {

    private String bos;
    private String comp_path="company";
    private Trade optimal_trade;
    private Trade my_trade;
    private Integer price;
    private DatabaseReference session_db_ref;




    public Trade_Manager(Trade my_trade, String bos, Integer price,DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
        this.my_trade=my_trade;
        this.bos = bos;
        this.price = price;


    }


    public void search_for_trade(){
        Query q = session_db_ref.child("Trades").child(bos);
        q.orderByChild(comp_path).equalTo(my_trade.getCompany()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Trade> t = new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    add_to_sorted(t, d.getValue(Trade.class)); }
                if (t.size()>0) parse_trade_options(t, bos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void parse_trade_options(ArrayList<Trade> trades, String bos){
        if (bos.compareTo("Buy")==0){
            while (my_trade.getBuyer_id()==null&&trades.size()>0){

            optimal_trade= trades.remove(trades.size()-1);
            if (optimal_trade.getPrice_point()>=price&&my_trade.getPrice_point()<=price)
            perform_exchange(my_trade, optimal_trade);}
       }
       else {
            while (my_trade.getSeller_id() == null && trades.size() > 0) {
                optimal_trade = trades.remove(0);

                if (optimal_trade.getPrice_point()<=price&&my_trade.getPrice_point()>=price)
                perform_exchange(optimal_trade, my_trade);
            }
        }

    }

    public void add_to_sorted(ArrayList<Trade> trades, Trade item){
        int i=0;
        for (Trade trade: trades){
            if (trade.greater_than(item)){
                trades.add(i, item);
                return;
            }
            i+=1;
        }
        trades.add(i, item);
    }

    public void perform_exchange(Trade seller_trade, Trade buyer_trade){
        DatabaseReference db = session_db_ref;
        Integer i = seller_trade.getNum_shares()-buyer_trade.getNum_shares();


        if (i>0){
            buyer_trade.setSeller_id(seller_trade.getSeller_id());
            seller_trade.setNum_shares(i);
            db.child("Trades").child("Sell").child(seller_trade.getId()).setValue(seller_trade);
            db.child("Trades").child("Buy").child(buyer_trade.getId()).setValue(null);
            buyer_trade.setPrice_point(price);
            db.child("Trades").child("Completed").child(buyer_trade.getId()).setValue(buyer_trade);



        }else if(i==0){
            seller_trade.setBuyer_id(buyer_trade.getBuyer_id());
            db.child("Trades").child("Buy").child(buyer_trade.getId()).setValue(null);
            db.child("Trades").child("Sell").child(seller_trade.getId()).setValue(null);
            seller_trade.setPrice_point(price);
            db.child("Trades").child("Completed").child(seller_trade.getId()).setValue(seller_trade);

        }else{
            seller_trade.setBuyer_id(buyer_trade.getBuyer_id());
            buyer_trade.setNum_shares(-i);
            db.child("Trades").child("Buy").child(buyer_trade.getId()).setValue(buyer_trade);
            db.child("Trades").child("Sell").child(seller_trade.getId()).setValue(null);
            seller_trade.setPrice_point(price);
            db.child("Trades").child("Completed").child(seller_trade.getId()).setValue(seller_trade);



        }



    }




}
