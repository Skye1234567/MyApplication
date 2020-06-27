package Objects;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SessionDatabase extends Observable {

    private Session session;
    private int set;

    public SessionDatabase() {
        session = new Session();set =0;

    }
    public void setParam() {

        FirebaseDatabase.getInstance().getReference("markets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                session = dataSnapshot.getValue(Session.class);
                setChanged();
                notifyObservers(session);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
