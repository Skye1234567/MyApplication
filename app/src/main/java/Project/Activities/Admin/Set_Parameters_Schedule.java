package Project.Activities.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Project.Objects.Database.SessionDatabaseReference;
import Project.Objects.Economics.Schedule;
import Project.Objects.Handlers.AdminTime;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Set_Parameters_Schedule extends AppCompatActivity {
    Context context;
    FirebaseAuth mauth;
    Spinner spinnerreport;
    Spinner spinnerinvest;
    long time;
    long report_length;
    long invest_length;
    SessionDatabaseReference SDR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__parameters_schedule);
        context = this;
        SDR= (SessionDatabaseReference) getApplicationContext();
        mauth = FirebaseAuth.getInstance();
        if (mauth.getCurrentUser() == null) {
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);

        }
        Button start = findViewById(R.id.start_now);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=System.currentTimeMillis()+60000;
            }
        });

        spinnerreport = findViewById(R.id.reporttime);
        spinnerinvest=findViewById(R.id.investtime);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item);
        arrayAdapter.add("1");
        arrayAdapter.add("2");
        arrayAdapter.add("3");
        arrayAdapter.add("5");
        arrayAdapter.add("7");
        arrayAdapter.add("11");

        spinnerreport.setAdapter(arrayAdapter);
        spinnerinvest.setAdapter(arrayAdapter);
        spinnerinvest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer i = Integer.valueOf(parent.getItemAtPosition(position).toString());
                invest_length = i*60000;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerreport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer i = Integer.valueOf(parent.getItemAtPosition(position).toString());
                report_length = i*60000;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button submit   = findViewById(R.id.scheduleeditsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminTime AT = new AdminTime(report_length, invest_length,
                        System.currentTimeMillis()+(report_length+invest_length)*12, true, SDR.getGlobalVarValue());
                new Thread(AT).start();
                SessionDatabaseReference SDR  = (SessionDatabaseReference) getApplicationContext();


                SDR.getGlobalVarValue().child("Time").setValue(
                        new Schedule(time, invest_length,report_length)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(context, AdminHub.class));
                    }
                });



            }
        });
    }

}
