package Objects;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Pricing_Model extends ViewModel {
    private MutableLiveData<HashMap<String, Price>> livedata = new MutableLiveData<>();

    private String current_user_id;


    public void setCurrent_user_id(String current_user_id) {
        this.current_user_id = current_user_id;
    }

    public MutableLiveData<HashMap<String,Price>> getPrices() {
        update_prices();
        return livedata;
    }

    public void setPrice(HashMap<String,Price> price) {
        livedata.setValue(price);
    }



    public void update_prices(){

        FirebaseDatabase.getInstance().getReference("Prices").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               HashMap<String, Price> hm= (HashMap<String, Price>) dataSnapshot.getValue();
               setPrice(hm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void update_share_price(){
        if (current_user_id!=null&&livedata.getValue()!=null) {
            for (String k: livedata.getValue().keySet()) {

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                db.getReference("Shares").child(current_user_id).child(k).child("market_price").setValue(livedata.getValue().get(k));
            }
        }
    }
}