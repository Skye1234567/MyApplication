package Project.Activities.Managers;

import Project.Objects.Database.ALLOWDatabase;
import Project.Objects.Database.SessionDatabaseReference;
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
        SDR = (SessionDatabaseReference) getApplicationContext();
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
