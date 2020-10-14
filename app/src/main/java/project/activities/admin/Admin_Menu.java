package project.activities.admin;

import project.activities.player.GameMenu;
import androidx.appcompat.app.AppCompatActivity;
import project.activities.player.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

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


    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
