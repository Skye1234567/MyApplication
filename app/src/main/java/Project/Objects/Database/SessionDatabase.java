package Project.Objects.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import Project.Objects.Economics.Session;
import androidx.annotation.NonNull;

public class SessionDatabase extends Observable {

    private Session session;
    private int set;
    private DatabaseReference session_db_ref;

    public SessionDatabase(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;
        session = new Session();
        set =0;

    }
    public void setParam() {

        session_db_ref.child("markets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                session = dataSnapshot.getValue(Session.class);

                if (session!=null){
                setChanged();
                notifyObservers(session);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
