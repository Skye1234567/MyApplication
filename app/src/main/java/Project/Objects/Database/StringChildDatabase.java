package Project.Objects.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;

public class StringChildDatabase extends Observable {
    private boolean s;
    private DatabaseReference reference;
    private String sess;

    public StringChildDatabase(DatabaseReference reference, String sess) {
       this.reference = reference;
       this.sess = sess;
       this.s=false;


    }

    public void updating() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(sess)){
                    s=true;
                    setChanged();
                    notifyObservers(s);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    @Override
    protected synchronized void clearChanged() {
        super.clearChanged();
    }

    @Override
    public synchronized boolean hasChanged() {
        return super.hasChanged();
    }

    @Override
    public synchronized int countObservers() {
        return super.countObservers();
    }
}