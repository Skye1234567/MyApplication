package project.activities.admin;

import project.activities.player.GameMenu;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Menu extends AppCompatActivity {

    Button logout;
    Button download;
    Button toHUB;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_admin__menu);
        logout = findViewById(R.id.logoutadmin);
        download = findViewById(R.id.downloaddatatadmin);
        toHUB = findViewById(R.id.editparametersadmin);
        toHUB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AdminHub.class));
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(context, GameMenu.class));
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DownloadPage.class));

            }
        });

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
