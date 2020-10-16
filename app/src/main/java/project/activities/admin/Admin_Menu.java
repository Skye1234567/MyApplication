package project.activities.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import project.activities.player.MainActivity;

public class Admin_Menu extends AppCompatActivity {

    private Button logout;
    private Button download;
    private Button toSessList;
    private Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_admin__menu);
        logout = findViewById(R.id.logoutadmin);
       //TODO: MAKE DOWNLOAD BUTTON MAYBE
        toSessList = findViewById(R.id.editparametersadmin);
        toSessList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AdminSessionEdit.class));
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(context, MainActivity.class));
            }
        });
        //disable the back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent =new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);





    }

}
