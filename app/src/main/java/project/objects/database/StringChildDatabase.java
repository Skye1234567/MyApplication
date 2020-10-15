package project.objects.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;

public class StringChildDatabase extends Observable {

    private DatabaseReference reference;
    private String sess;

    public StringChildDatabase(DatabaseReference reference, String sess) {
        if(reference!=null)
       this.reference = reference;
       this.sess = sess;



    }

    public void updating() {
        if(reference!=null){
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()){
                    if (d.getKey().compareTo(sess)==0)
                    setChanged();
                    notifyObservers(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}


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
