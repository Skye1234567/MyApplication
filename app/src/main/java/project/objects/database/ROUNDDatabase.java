package project.objects.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import androidx.annotation.NonNull;

public class ROUNDDatabase extends Observable {
    String round;
    DatabaseReference session_db_ref;

    public ROUNDDatabase(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref.child("ROUND");
    }

    public void addListener(){
        session_db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                round = dataSnapshot.getValue(String.class);

                setChanged();
                notifyObservers(round);}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
