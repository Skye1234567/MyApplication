package Project.Objects.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import Project.Objects.Economics.Schedule;
import androidx.annotation.NonNull;

public class SessionTimeDatabase extends Observable {
    private Schedule schedule;


    public SessionTimeDatabase() {

    }


    public void setParam() {


        FirebaseDatabase.getInstance().getReference("Time").addListenerForSingleValueEvent(new ValueEventListener() {
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
