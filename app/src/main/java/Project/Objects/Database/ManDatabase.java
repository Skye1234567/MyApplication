package Project.Objects.Database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Observable;

import Project.Objects.Personel.Manager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ManDatabase extends Observable {
    private ArrayList<Manager> arrayList;

    public ManDatabase() {
        arrayList = new ArrayList<>();
    }

    public void listenForChanges(){

        FirebaseDatabase.getInstance().getReference("Managers").addChildEventListener(new ChildEventListener() {
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
    public void remove(Manager m){
        for (Manager manager :arrayList){
            if (manager==m){
                arrayList.remove(manager);
                return;
            }

        }

    }


}
