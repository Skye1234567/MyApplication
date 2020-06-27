package Project.Activities.Admin;

import Project.Objects.Economics.Market;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Set_Parameters extends AppCompatActivity {
    Context context;
    FirebaseAuth mauth;
    EditText p;
    EditText pi_h;
    EditText pi_l;
    EditText rounds;
    Button submit_round_1;
    FirebaseDatabase db;
    String child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__parameters);
        context= this;
        child =getIntent().getStringExtra("child");
        mauth = FirebaseAuth.getInstance();
        if ( mauth.getCurrentUser()==null){
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);
        }

        p = findViewById(R.id.prob_ofhigh);
    pi_h = findViewById(R.id.prob_outcome_high);
    pi_l = findViewById(R.id.prob_outcome_low);
    rounds = findViewById(R.id.num_rounds);
    submit_round_1 = findViewById(R.id.button_submit_round_1);

        submit_round_1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Float fp = Float.parseFloat(p.getText().toString());
            Float fpi_h = Float.parseFloat(pi_h.getText().toString());
            Float fpi_l = Float.parseFloat(pi_l.getText().toString());
            Integer num_round = Integer.parseInt(rounds.getText().toString());

            if (!(fp==null || fpi_h==null || fpi_l==null || num_round==null)) {
                Market round_1 = new Market(fpi_h,fpi_l,fp, num_round);
                round_1.setType("BOOM");
                db = FirebaseDatabase.getInstance();
                db.getReference("markets").child(child).setValue(round_1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(context, AdminHub.class);
                        context.startActivity(intent);
                        finish();
                    }
                });

            }
            else{
                Toast.makeText(context, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }




        }
    });
}
}
