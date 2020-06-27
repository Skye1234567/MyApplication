package Objects;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Round {
    private long stop_time;
    private Context context;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    public Round(long stop_time, Context context, Intent intent) {

        this.stop_time = stop_time;
        this.context = context;
        pendingIntent= PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        set_pending_intent_alarm(); }

    public void set_pending_intent_alarm() {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, stop_time , pendingIntent);
    }

    public void cancel_alarm(){
        if (alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }


    }

}