package project.objects.models;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import project.objects.economics.Trade;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Trade_Model extends ViewModel {
    private  MutableLiveData<ArrayList<ArrayList<Trade>>> livedata=new MutableLiveData<>();
    DatabaseReference session_db_ref;

    public DatabaseReference getSession_db_ref() {
        return session_db_ref;
    }

    public void setSession_db_ref(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    private String id;



    public void removeTrade(Trade t, int position ){
        ArrayList<Trade>  a=livedata.getValue().get(position);
       a.remove(t);
        ArrayList b = livedata.getValue();
        b.set(position, a);
        livedata.setValue(b);

    }
    public LiveData<ArrayList<ArrayList<Trade>>> getTrades(){
        update_trade();

        return livedata;
    }
    public Trade_Model(){
        ArrayList<ArrayList<Trade>> a = new ArrayList<>();
        a.add(new ArrayList<Trade>());
        a.add(new ArrayList<Trade>());
        livedata.setValue(a) ;


    }



   public void addTrade(Trade trade, int position){
        ArrayList a = livedata.getValue().get(position);
        if (trade!=null){
        if (!a.contains(trade)) a.add(trade);}

       ArrayList b = livedata.getValue();
       b.set(position, a);
       livedata.setValue(b);



   }
   public void update_trade(){
        update_BUy_Trades();
        update_sell_Trades();
   }

public void update_BUy_Trades(){

    Query ref = session_db_ref.child("Trades").child("Buy").orderByChild("buyer_id").equalTo(id);
        ref.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            addTrade(dataSnapshot.getValue(Trade.class), 0);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            removeTrade(dataSnapshot.getValue(Trade.class), 0);

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


}

    public void update_sell_Trades(){


        Query ref = session_db_ref.child("Trades").child("Sell").orderByChild("seller_id").equalTo(id);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addTrade(dataSnapshot.getValue(Trade.class), 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                removeTrade(dataSnapshot.getValue(Trade.class),1);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
public Trade filter_for_company(String comp){
       Trade t=null;
        for (ArrayList<Trade> at: livedata.getValue()){
            for (Trade trade: at){
                if (trade.getCompany().compareTo(comp)==0){
                    t=trade;
                    break;
                }
                if (trade!=null) break;

            }
        }


       return t;
}

}
