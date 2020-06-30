package Project.Objects.Handlers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class AdminTime implements Runnable {
    long delay;
    long nextdelay;
    DatabaseReference ref;
    long end;
    TimerTask set;


//todo make singleton?
    public AdminTime(final long delay, final long nextdelay, final long end, final boolean ALLOW) {
        this.delay = delay;
        this.nextdelay = nextdelay;
        this.ref= FirebaseDatabase.getInstance().getReference("ALLOW_TRADES");
        this.end = end;
        set = new TimerTask() {
            @Override
            public void run() {
                ref.setValue(ALLOW);
                new Thread( new AdminTime(nextdelay, delay, end, !ALLOW)).start();
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
