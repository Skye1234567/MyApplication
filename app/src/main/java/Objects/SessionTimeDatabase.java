package Objects;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Observable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SessionTimeDatabase extends Observable {
    private Schedule schedule;


    public SessionTimeDatabase() {

    }


    public void setParam() {


        FirebaseDatabase.getInstance().getReference("Time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                schedule = dataSnapshot.getValue(Schedule.class);
                setChanged();
                notifyObservers(schedule);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }


}
