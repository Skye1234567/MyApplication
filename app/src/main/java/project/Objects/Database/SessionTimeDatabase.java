package project.Objects.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import project.Objects.Economics.Schedule;
import androidx.annotation.NonNull;

public class SessionTimeDatabase extends Observable {
    private Schedule schedule;
    private DatabaseReference session_db_ref;


    public SessionTimeDatabase(DatabaseReference session_db_ref) {
        this.session_db_ref = session_db_ref;

    }


    public void setParam() {


        session_db_ref.child("Time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                schedule = dataSnapshot.getValue(Schedule.class);
                if (schedule!=null){
                setChanged();
                notifyObservers(schedule);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }

    public Integer getCurrentRound(){

        Integer current_round = 0;
        if (schedule!=null){
        long rn = System.currentTimeMillis();
        long now = (rn - schedule.getStart()) / (schedule.getReport() + schedule.getInvest());
        while (current_round < now) {
            current_round += 1;

        }}

        return current_round;
    }


}
