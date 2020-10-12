package project.activities.investors;

import project.objects.database.ALLOWDatabase;
import project.objects.database.ROUNDDatabase;
import project.objects.database.SessionDatabaseReference;
import project.objects.personel.Investor;
import project.objects.adapters.ManAdapter;
import project.objects.database.ManDatabase;
import project.objects.personel.Manager;
import project.objects.economics.Schedule;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static java.lang.Integer.valueOf;

public class Investor_Round_Intro extends AppCompatActivity {

    TextView round_title;
    Context context;
    Investor i;
    long start_time;
    ManAdapter manAdapter;
    Schedule schedule;
    Integer round;
    ArrayList<Manager> managersArray;
    ListView listView;
    private ALLOWDatabase allowDatabase;
    private SessionDatabaseReference SDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start_time = System.currentTimeMillis();
        SDR =(SessionDatabaseReference) getApplication();
        DatabaseReference base_ref = SDR.getGlobalVarValue();
        allowDatabase = new ALLOWDatabase(base_ref);
        allowDatabase.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if ((boolean)arg) {
                    Intent intent =new Intent(context, MarketPlace.class);
                    intent.putExtra("investor", i);
                    startActivity(intent);
                }
            }
        });
        allowDatabase.addListener();


        setContentView(R.layout.activity_investor__round__intro);



        managersArray = new ArrayList<>();

        round_title = findViewById(R.id.round_title_i);

        manAdapter = new ManAdapter(this, managersArray);
        listView = findViewById(R.id.list_of_companies);
        listView.setAdapter(manAdapter);

        ROUNDDatabase SD = new ROUNDDatabase(base_ref);
        SD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                round = (Integer) arg;
                round_title.setText("Round "+round.toString());

            }
        });
        SD.addListener();


        context=this;


        getInvestor();

        ManDatabase MD = new ManDatabase(base_ref);
        MD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {


                managersArray = (ArrayList<Manager>) arg;
                update_adapter();
            }
        });
        MD.listenForChanges();

    }

    public void getInvestor(){
        String id;
        if (  FirebaseAuth.getInstance().getCurrentUser()!=null)
            id =    FirebaseAuth.getInstance().getCurrentUser().getUid();
        else return;
        SessionDatabaseReference SDR  = (SessionDatabaseReference) getApplication();


        SDR.getGlobalVarValue().child("Investors").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 i =dataSnapshot.getValue(Investor.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void update_adapter(){
        manAdapter.clear();
        manAdapter.addAll(managersArray);
    }


}
