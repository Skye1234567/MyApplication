package Project.Activities.Admin;

import Project.Objects.Economics.Price;
import Project.Objects.Economics.Schedule;
import Project.Objects.Economics.Session;
import Project.Objects.Economics.Share;
import Project.Objects.Economics.Trade;
import Project.Objects.Handlers.Downloaded_Data;
import Project.Objects.Personel.Investor;
import Project.Objects.Personel.Manager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DownloadPage extends AppCompatActivity {
    ProgressBar pb;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_page);
         pb = findViewById(R.id.pb);
         context=this;


        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
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

                write_to_file(sdd);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void write_to_file(String write){
        pb.setMax(100);


        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {

            //If it isn't mounted - we can't write into it.
            return;
        }

        //Create a new file that points to the root directory, with the given name:
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hhmmss");
        String dateString = dateFormat.format(date);
        String filenameExternal ="MarketData"+dateString;

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filenameExternal);

        //This point and below is responsible for the write operation
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            //second argument of FileOutputStream constructor indicates whether
            //to append or create new file if one exists
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

           bufferedWriter.write(write);

            bufferedWriter.close();
            startActivity(new Intent(context, Admin_Menu.class));
            pb.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
