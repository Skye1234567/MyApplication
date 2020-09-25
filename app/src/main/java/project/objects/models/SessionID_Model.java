package project.objects.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class SessionID_Model extends ViewModel {

    private MutableLiveData<ArrayList<String>> livedata = new MutableLiveData<>();

    public LiveData<ArrayList<String>> getSessions() {


        return livedata;
    }

    public SessionID_Model() {

        livedata.setValue(new ArrayList<String>());
        update_sessions();


    }


    public void setSessionList(ArrayList<String> s) {
        livedata.setValue(s);

    }

    public void addSession(String session) {
        boolean contains = false;
        ArrayList a = livedata.getValue();
        if (session != null) {
            for (String s : livedata.getValue()) {
                if (s.compareTo(session) == 0) contains = true;


            }
            if (contains == false) {
                a.add(session);
            }

        }
        setSessionList(a);


    }

    public void update_sessions() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().compareTo("adminhub222")!=0)
                addSession(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                removeSession(snapshot.getKey());

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void removeSession(String value) {
        ArrayList<String> a = livedata.getValue();
        for (String id : a) {
            if (id.compareTo(value) == 0) {
                a.remove(id);
            }
           setSessionList(a);
        }
    }
}