package Objects;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Man_Model extends ViewModel {
    private  MutableLiveData<Manager> livedata=new MutableLiveData<>();
    private String id;
    public LiveData<Manager> getMan(){
        update_manager();

        return livedata;
    }
    public Man_Model(){
        update_manager();
    }

   public void setMan(Manager manager){
        livedata.setValue(manager);
        id=manager.getID();

   }

public void update_manager(){
        if (id==null){return;}else{
    DatabaseReference ref = FirebaseDatabase.getInstance()
            .getReference().child("Managers").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                livedata.setValue(dataSnapshot.getValue(Manager.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("can't get man for man_model", databaseError.getMessage());

            }
        });
        }
}
}
