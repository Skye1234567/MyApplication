package project.objects.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import project.objects.personel.Manager;

public class One_Man_Model extends ViewModel {
    private  MutableLiveData<Manager> livedata=new MutableLiveData<>();
    private String id;
    private String symbol;
    private DatabaseReference session_db_ref;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
        update_manager();
    }
    public void set_id(String id){
        this.id=id;

    }

    public LiveData<Manager> getMan(){
        update_manager();

        return livedata;
    }

    public DatabaseReference getSession_db_ref() {
        return session_db_ref;
    }

    public void setSession_db_ref(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
    }

    public void setMan(Manager manager){
        livedata.setValue(manager);
        id=manager.getID();

    }

    public void update_manager(){
        if (id!=null){
            DatabaseReference ref = session_db_ref.child("Managers").child(id);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    livedata.setValue(dataSnapshot.getValue(Manager.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("can't get m", databaseError.getMessage());

                }
            });
        }
        else if (symbol!=null){
            Query q = session_db_ref.child("Managers").orderByChild("company_symbol");
            q.equalTo(symbol).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        livedata.setValue(d.getValue(Manager.class));
                    }}

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}