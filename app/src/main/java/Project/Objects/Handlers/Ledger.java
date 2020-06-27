package Project.Objects.Handlers;

import com.google.firebase.database.DatabaseReference;

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