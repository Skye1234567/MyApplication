package Objects;

import android.os.Build;

import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.nio.channels.AsynchronousChannel;
import java.util.ArrayList;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Ledger implements Runnable{
    private Integer callback;
   private Integer update;
   private DatabaseReference ref;

    public Ledger(Integer callback, Integer update, DatabaseReference reference){
        this.callback = callback;
        this.update=update;
        this.ref=reference;

    }

    @Override
    public void run() {
        Integer new_val = callback+update;
        ref.setValue(new_val);

    }
}