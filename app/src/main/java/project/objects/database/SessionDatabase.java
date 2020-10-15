package project.objects.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import project.objects.economics.Session;
import androidx.annotation.NonNull;

public class SessionDatabase extends Observable {

    private Session session;
    private DatabaseReference session_db_ref;

    public SessionDatabase(DatabaseReference session_db_ref) {
        if(session_db_ref!=null)
        this.session_db_ref = session_db_ref;
        this.session = new Session();

    }
    public void setParam() {
        if(session_db_ref!=null){
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
        });}
    }


}
