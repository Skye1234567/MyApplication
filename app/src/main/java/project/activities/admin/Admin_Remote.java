package project.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import project.objects.database.SessionDatabaseReference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;

public class Admin_Remote extends AppCompatActivity {
    private Button open_market;
    private Button close_market;
    private Button back;
    private Context context;

    private SessionDatabaseReference SDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_remote);
        open_market = findViewById(R.id.open_markets_admin);
        close_market = findViewById(R.id.close_markets_admin);
        back = findViewById(R.id.back_from_admin_remote);
        context = this;
        open_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDR = (SessionDatabaseReference) getApplication();
                DatabaseReference sess_id = SDR.getGlobalVarValue();
                sess_id.child("ALLOW_TRADES").setValue(true);


            }
        });
        close_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDR = (SessionDatabaseReference) getApplication();
                DatabaseReference sess_id = SDR.getGlobalVarValue();
                sess_id.child("ALLOW_TRADES").setValue(false);


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminHub.class);
                startActivity(intent);
            }
        });

    }
}
