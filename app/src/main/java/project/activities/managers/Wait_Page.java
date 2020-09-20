package project.activities.managers;

import project.objects.database.ALLOWDatabase;
import project.objects.database.SessionDatabaseReference;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;

import java.util.Observable;
import java.util.Observer;

public class Wait_Page extends AppCompatActivity {
    ALLOWDatabase allowDatabase;
    Context context;
    SessionDatabaseReference SDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        SDR = (SessionDatabaseReference) getApplication();
        setContentView(R.layout.activity_wait__page);
        allowDatabase = new ALLOWDatabase(SDR.getGlobalVarValue());
        allowDatabase.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if ((boolean)arg) {
                    startActivity(new Intent(context, MarketPlaceForMan.class));
                }
            }
        });
        allowDatabase.addListener();

    }
}
