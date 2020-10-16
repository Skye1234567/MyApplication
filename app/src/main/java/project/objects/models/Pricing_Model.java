package project.objects.models;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import project.objects.economics.Price;

public class Pricing_Model extends ViewModel {
    private MutableLiveData<HashMap<String, Price>> livedata = new MutableLiveData<>();
    private DatabaseReference session_db_ref;

    public DatabaseReference getSession_db_ref() {
        return session_db_ref;
    }

    public void setSession_db_ref(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
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

       session_db_ref.child("Prices").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Price p = dataSnapshot.getValue(Price.class);
                addPrice(dataSnapshot.getKey(), p);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Price p = dataSnapshot.getValue(Price.class);
                addPrice(dataSnapshot.getKey(), p);

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