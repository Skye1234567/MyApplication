package project.activities.managers;

import project.activities.player.GameMenu;
import project.objects.database.ALLOWDatabase;
import project.objects.database.SessionDatabaseReference;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(context, GameMenu.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);

                finish();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
