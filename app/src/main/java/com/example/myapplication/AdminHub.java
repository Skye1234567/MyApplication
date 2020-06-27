package com.example.myapplication;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import com.google.firebase.auth.FirebaseAuth;

public class AdminHub extends AppCompatActivity {
    Button EditPractice;
    Button EditRound1;
    Button EditRound2;
    Button EditSchedule;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_admin_hub);
        if ( mauth.getCurrentUser()==null){
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);
        }

        context =this;
        EditSchedule = findViewById(R.id.setschedhub);
        EditRound2 = findViewById(R.id.setround_2hub);
        EditRound1 = findViewById(R.id.setround_1hub);
        EditPractice = findViewById(R.id.setpracticehub);


        EditPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Set_Parameters.class);
                intent.putExtra("child", "practice");
                startActivity(intent);

            }
        });
        EditRound1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Set_Parameters.class);
                intent.putExtra("child", "round_1");
                startActivity(intent);

            }
        });
        EditRound2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Set_Parameters.class);
                intent.putExtra("child", "round_2");
                startActivity(intent);

            }
        });

        EditSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Set_Parameters_Schedule.class);

                startActivity(intent);

            }
        });


    }
}
