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

public class IntArrayDatabase extends Observable {
    private ArrayList<Integer> arrayList;
    private DatabaseReference session_db_ref;

    public IntArrayDatabase(DatabaseReference session_db_ref) {
        if(session_db_ref!=null)
        this.session_db_ref = session_db_ref;
        arrayList = new ArrayList<>();
    }

    public void listenForChanges(){if(session_db_ref!=null) {
        session_db_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayList.add(dataSnapshot.getValue(Integer.class));

                setChanged();
                notifyObservers(arrayList);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                remove(dataSnapshot.getValue(Integer.class));
                arrayList.add(dataSnapshot.getValue(Integer.class));
                setChanged();
                notifyObservers(arrayList);


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                remove(dataSnapshot.getValue(Integer.class));
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
    public void remove(Integer m){
        for (Integer integer :arrayList){
            if (integer.equals(m)){
                arrayList.remove(integer);
                return;
            }

        }

    }
    public void add_Int(Integer m){

    }


}
