package Project.Objects.Models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Project.Objects.Economics.Trade;
import Project.Objects.Personel.Investor;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Vest_Model extends ViewModel {
    private  MutableLiveData<Investor> livedata=new MutableLiveData<>();
    private String id;
    private ArrayList<Trade> t;
    private DatabaseReference session_db_ref;
    public LiveData<Investor> getMan(){
       update_investor();

        return livedata;
    }
    public Vest_Model(){

    }

    public DatabaseReference getSession_db_ref() {
        return session_db_ref;
    }

    public void setSession_db_ref(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
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
    DatabaseReference ref = session_db_ref.child("Investors").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                livedata.setValue(dataSnapshot.getValue(Investor.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("can't get man", databaseError.getMessage());

            }
        });
        }
}


}
