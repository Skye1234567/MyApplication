package Project.Objects.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import androidx.annotation.NonNull;

public class ALLOWDatabase extends Observable {
    boolean Allow;
    DatabaseReference session_db_ref;

    public ALLOWDatabase(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref.child("ALLOW_TRADES");
    }

    public void addListener(){
        session_db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                Allow = dataSnapshot.getValue(boolean.class);

                setChanged();
                notifyObservers(Allow);}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
