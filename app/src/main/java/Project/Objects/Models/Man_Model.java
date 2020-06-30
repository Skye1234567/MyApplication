package Project.Objects.Models;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import Project.Objects.Personel.Manager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Man_Model extends ViewModel {
    private  MutableLiveData<HashMap<String, Manager>> livedata=new MutableLiveData<>();
   private HashMap<String, Manager> a;

    public LiveData<HashMap<String, Manager>> getMan(){
        update_manager();


        return livedata;
    }
    public Man_Model(){
        update_manager();
    }

   public void setMan(HashMap<String, Manager> manager){
        livedata.setValue(manager);


   }

public void update_manager(){
        if (livedata.getValue()==null) livedata.setValue(new HashMap<String, Manager>());
        a = livedata.getValue();

    DatabaseReference ref = FirebaseDatabase.getInstance()
            .getReference().child("Managers");
    ref.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Manager m =dataSnapshot.getValue(Manager.class);
            a.put(m.getCompany_symbol(), m);
            setMan(a);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Manager m =dataSnapshot.getValue(Manager.class);
            a.put(m.getCompany_symbol(), m);
            setMan(a);

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            Manager m =dataSnapshot.getValue(Manager.class);
            a.put(m.getCompany_symbol(), null);
            setMan(a);

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