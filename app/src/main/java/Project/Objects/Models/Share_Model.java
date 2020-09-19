package Project.Objects.Models;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Project.Objects.Economics.Share;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Share_Model extends ViewModel {
    private  MutableLiveData<ArrayList<Share>> livedata=new MutableLiveData<>();
    private String id;
    private DatabaseReference session_db_ref;

    public DatabaseReference getSession_db_ref() {
        return session_db_ref;
    }

    public void setSession_db_ref(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
    }


    public void removeShare(Share s){
        ArrayList<Share>  a=livedata.getValue();
        for (Share share: a ){
            if (share.getCompany() == s.getCompany()){
                a.remove(share);
            }

        }
        livedata.setValue(a);

    }
    public LiveData<ArrayList<Share>> getShares(){
        update_shares();

        return livedata;
    }
    public Share_Model(){
        livedata.setValue(new ArrayList<Share>());


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShareList(ArrayList<Share>s){
        livedata.setValue(s);

   }
   public void addShare(Share share){
        ArrayList a = livedata.getValue();
        if (share!=null){
        if (!a.contains(share)) a.add(share);}
        setShareList(a);


   }

public void update_shares(){
        if (id==null){return;}else{
    DatabaseReference ref = session_db_ref.child("Shares").child(id);
    ref.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            addShare(dataSnapshot.getValue(Share.class));
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            removeShare(dataSnapshot.getValue(Share.class));

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



}
