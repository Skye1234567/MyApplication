package project.activities.player;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class GameMenu extends AppCompatActivity {
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);
        context = this;

        Button button1 = findViewById(R.id.select_player);
        Button button2 = findViewById(R.id.select_sign_up);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put stuff for player auth
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Intent intent = new Intent(context, Sign_up_player.class);
                context.startActivity(intent);
                finish();


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put stuff for player auth
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Intent intent = new Intent(context, Create_User.class);
                context.startActivity(intent);
                finish();


            }
        });
    }


}



