package Project.Objects.Handlers;

import android.content.Context;
import android.content.Intent;

import java.util.Observable;
import java.util.Observer;

import Project.Objects.Economics.Round;
import Project.Objects.Economics.Schedule;
import Project.Objects.Database.SessionTimeDatabase;

import static java.lang.Math.abs;


public class RoundHandler implements Runnable {
    Schedule schedule;
    Integer current_round;
    long round_stop;
    Context context;
    Intent intent;
    Round round;

    public RoundHandler(Context context, Intent intent) {
      this.context = context;
      this.intent = intent;


    }

    public void create_round(){

       round =  new Round(round_stop,  context, intent);

    }

    public void destroy_round(){
        if (round!=null) round.cancel_alarm();
    }

    public void observeSchedule(){

        SessionTimeDatabase SD = new SessionTimeDatabase();

        SD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                schedule = (Schedule) arg;
                current_round = 0;
                long rn = System.currentTimeMillis();
                long now = (rn - schedule.getStart()) / (schedule.getReport() + schedule.getInvest());
                while (current_round < now) {
                    current_round += 1;

                }
                round_stop = schedule.getStart() + current_round * (schedule.getInvest() + schedule.getReport()) + schedule.getReport();
                if (abs(System.currentTimeMillis() - round_stop) < 10000){
                    current_round += 1;
                round_stop = schedule.getStart() + (current_round) * (schedule.getInvest() + schedule.getReport());
            }
                intent.putExtra("Title", "Round "+current_round.toString());

                create_round();



            }
        });
        SD.setParam();


    }

    @Override
    public void run() {
        observeSchedule();


    }
}
