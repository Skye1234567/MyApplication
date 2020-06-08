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
    public void  addPrice(String key, Price p){
        if (livedata.getValue()==null) livedata.setValue(new HashMap<String, Price>());
        HashMap<String, Price>  h =livedata.getValue();
        h.put(key, p);
        livedata.setValue(h);


    }



    public void update_prices(){

        FirebaseDatabase.getInstance().getReference("Prices").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addPrice(dataSnapshot.getKey(), dataSnapshot.getValue(Price.class));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addPrice(dataSnapshot.getKey(), dataSnapshot.getValue(Price.class));

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                addPrice(dataSnapshot.getKey(), null);


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}