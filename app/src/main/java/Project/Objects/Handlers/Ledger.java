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

    public Integer getCallback() {
        return callback;
    }

    public void setCallback(Integer callback) {
        this.callback = callback;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }

    public DatabaseReference getRef() {
        return ref;
    }

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }
}