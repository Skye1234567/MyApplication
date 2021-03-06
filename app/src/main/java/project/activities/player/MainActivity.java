package project.activities.player;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Observable;
import java.util.Observer;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import project.activities.admin.Admin_Menu;
import project.objects.database.SessionDatabaseReference;
import project.objects.database.StringChildDatabase;

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
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
        SDR= ((SessionDatabaseReference) getApplication());
        verify = findViewById(R.id.verify_sess_button);



        context = this;
        session_num.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    verify.performClick();


                    return true;
                }


                return false;
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_id = session_num.getText().toString();
                StringChildDatabase SCD = new StringChildDatabase(FirebaseDatabase.getInstance().getReference(),session_id);
                SCD.addObserver(new Observer() {
                    @Override
                    public void update(Observable o, Object arg) {
                        if ((boolean) arg) {

                            Intent intent ;
                            //TODO: NO HARDCODE admin_session id
                            if (session_id.compareTo("admin_session")==0){
                                intent = new Intent(context, Admin_Menu.class);
                            }
                            else{

                                intent = new Intent(context, GameMenu.class);

                            }
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
