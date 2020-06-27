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

import Project.Objects.Economics.Schedule;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__parameters_schedule);
        context = this;
        mauth = FirebaseAuth.getInstance();
        if (mauth.getCurrentUser() == null) {
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);

        }
        final View dialogView = View.inflate(context, R.layout.datetimeres, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        dialogView.findViewById(R.id.settime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                time = calendar.getTimeInMillis();
                alertDialog.dismiss();
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();



        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item);
        arrayAdapter.add("10");
        arrayAdapter.add("15");
        arrayAdapter.add("20");
        arrayAdapter.add("25");
        spinnerreport.setAdapter(arrayAdapter);
        spinnerinvest.setAdapter(arrayAdapter);
        spinnerinvest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer i = Integer.valueOf(parent.getItemAtPosition(position).toString());
                report_length = i*60000;

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
                FirebaseDatabase.getInstance().getReference("Time").setValue(
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
