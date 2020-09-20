package project.activities.player;


import project.objects.database.SessionDatabaseReference;
import project.objects.database.StringChildDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
    private EditText session_num;
    private Context context;
    private String session_id;
    private Button verify;
    SessionDatabaseReference SDR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session__validation);
        session_num = findViewById(R.id.sess_num_enter);
        SDR= ((SessionDatabaseReference) getApplication());
        verify = findViewById(R.id.verify_sess_button);

        context = this;
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_id = session_num.getText().toString();
                StringChildDatabase SCD = new StringChildDatabase(FirebaseDatabase.getInstance().getReference(),session_id);
                SCD.addObserver(new Observer() {
                    @Override
                    public void update(Observable o, Object arg) {
                        if ((boolean) arg) {
                            Intent intent = new Intent(context, GameMenu.class);
                            SDR.setGlobalVarValue(session_id);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(context, "Session Not Recognized", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                SCD.updating();


            }


        });

    }
}
