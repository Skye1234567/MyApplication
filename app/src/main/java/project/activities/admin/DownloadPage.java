package project.activities.admin;

import project.activities.player.MainActivity;
import project.objects.database.SessionDatabaseReference;
import project.objects.economics.Price;
import project.objects.economics.Schedule;
import project.objects.economics.Session;
import project.objects.economics.Share;
import project.objects.economics.Trade;
import project.objects.handlers.Downloaded_Data;
import project.objects.personel.Investor;
import project.objects.personel.Manager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DownloadPage extends AppCompatActivity {
    ProgressBar pb;
    Context context;
    TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_page);
         pb = findViewById(R.id.pb);
         context=this;
         data = findViewById(R.id.all_data);
        SessionDatabaseReference SDR = (SessionDatabaseReference) getApplication();


        SDR.getGlobalVarValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Downloaded_Data dd = new Downloaded_Data();
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    if (d.getKey().compareTo("Managers")==0){dd.setManagers((HashMap<String, Manager>) d.getValue());}
                    if (d.getKey().compareTo("Investors")==0){dd.setInvestors((HashMap<String, Investor>) d.getValue());}
                    if (d.getKey().compareTo("Shares")==0){dd.setInvestor_shares((HashMap<String, Share>) d.getValue());}
                    if (d.getKey().compareTo("Session")==0){dd.setSession(d.getValue(Session.class));}
                    if (d.getKey().compareTo("Trades")==0){
                        dd.setCompleted((HashMap<String, Trade>) d.child("Completed").getValue());
                        dd.setBuy((HashMap<String, Trade>) d.child("Buy").getValue());
                        dd.setSell((HashMap<String, Trade>) d.child("Sell").getValue());
                        dd.setArchive((HashMap<String, Trade>)d.child("Archive").getValue());

                    }
                    if (d.getKey().compareTo("Prices")==0){dd.setPrices((HashMap<String, Price>) d.getValue());}
                    if (d.getKey().compareTo("Time")==0){dd.setSchedule(d.getValue(Schedule.class));}
                     }
                String sdd = dd.toString();
                data.setText(sdd);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
