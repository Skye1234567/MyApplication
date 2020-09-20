package project.Objects.Handlers;

import com.google.firebase.database.DatabaseReference;

import java.util.Timer;
import java.util.TimerTask;

public class AdminTime implements Runnable {
    long delay;
    long nextdelay;
    DatabaseReference session_db_ref;
    long end;
    TimerTask set;


//todo make singleton?
    public AdminTime(final long delay, final long nextdelay, final long end, final boolean ALLOW, final DatabaseReference db_ref) {
        this.session_db_ref = db_ref.child("ALLOW_TRADES");
        this.delay = delay;
        this.nextdelay = nextdelay;

        this.end = end;
        set = new TimerTask() {
            @Override
            public void run() {
                session_db_ref.setValue(ALLOW);
                new Thread( new AdminTime(nextdelay, delay, end, !ALLOW, db_ref)).start();
            }
        };

    }

    @Override
    public void run() {
        if (System.currentTimeMillis()<end){
            Timer timer = new Timer();
            timer.schedule(set, delay);


        }

    }
}
