package Objects;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Vest_Model extends ViewModel {
    private  MutableLiveData<Investor> livedata=new MutableLiveData<>();
    private String id;
    private ArrayList<Trade> t;
    public LiveData<Investor> getMan(){
        update_trade_info();

        return livedata;
    }
    public Vest_Model(){

    }

   public void setMan(Investor investor){
        livedata.setValue(investor);
        id=investor.getID();

   }

    public void setId(String id) {
        this.id = id;
    }

    public void update_investor(){
        if (id==null){return;}else{
    DatabaseReference ref = FirebaseDatabase.getInstance()
            .getReference().child("Investors").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                update_investor_trade(dataSnapshot.getValue(Investor.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("can't get man for man_model", databaseError.getMessage());

            }
        });
        }
}

public void update_trade_info(){
        update_investor();
        t=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Trades")
                .child("Completed").orderByChild("seller_id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Trade data = dataSnapshot.getValue(Trade.class);
                    t.add(data);
                    d.getRef().getParent().child("Archive").child(d.getKey()).setValue(data);
                    d.getRef().removeValue();
                }
                if (livedata.getValue()!=null) update_investor_trade(livedata.getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


}
 public void update_investor_trade(Investor investor){
        for (Trade trade: t ){
            Integer cash = investor.getCash();
            Integer pri = trade.getPrice_point();
            if (pri==null) investor.setCash(cash);
            else investor.setCash( cash+pri);
        }
        FirebaseDatabase.getInstance().getReference("Investors").child(id).setValue(investor);
        update_investor();
 }

}
