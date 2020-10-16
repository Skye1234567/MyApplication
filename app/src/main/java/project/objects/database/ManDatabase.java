package project.objects.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Observable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import project.objects.personel.Manager;

public class ManDatabase extends Observable {
    private ArrayList<Manager> arrayList;
    private DatabaseReference session_db_ref;

    public ManDatabase(DatabaseReference session_db_ref) {
        if(session_db_ref!=null)
        this.session_db_ref = session_db_ref;
        arrayList = new ArrayList<>();
    }

    public void listenForChanges(){if(session_db_ref!=null) {
        session_db_ref.child("Managers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayList.add(dataSnapshot.getValue(Manager.class));

                setChanged();
                notifyObservers(arrayList);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                remove(dataSnapshot.getValue(Manager.class));
                arrayList.add(dataSnapshot.getValue(Manager.class));
                setChanged();
                notifyObservers(arrayList);


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                remove(dataSnapshot.getValue(Manager.class));
                setChanged();
                notifyObservers(arrayList);

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
    public void remove(Manager m){
        for (Manager manager :arrayList){
            if (manager.equals(m)){
                arrayList.remove(manager);
                return;
            }

        }

    }


}
