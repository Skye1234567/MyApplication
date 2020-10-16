package project.activities.admin;

import androidx.activity.OnBackPressedCallback;
import project.activities.player.MainActivity;
import project.objects.database.SessionDatabaseReference;
import project.objects.economics.Market;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.internal.$Gson$Preconditions;

public class Set_Parameters extends AppCompatActivity {
    private Context context;
    private FirebaseAuth mauth;
    private EditText p;
    private EditText pi_h;
    private EditText pi_l;
    private EditText rounds;
    private Button submit_round_1;
    private String child;
    private Market market;
    private SessionDatabaseReference SDR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__parameters);

        context= this;
        SDR = (SessionDatabaseReference) getApplication();
        if (SDR.getGlobalVarValue()==null){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent =new Intent(context, AdminHub.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        child =getIntent().getStringExtra("child");
        market = (Market) getIntent().getSerializableExtra("market");
        mauth = FirebaseAuth.getInstance();

        ((TextView) findViewById(R.id.Titleeditparam)).setText(child);
        //Labelling the edit texts to match each parameter
        p = findViewById(R.id.prob_ofhigh);
        pi_h = findViewById(R.id.prob_outcome_high);
        pi_l = findViewById(R.id.prob_outcome_low);
        rounds = findViewById(R.id.num_rounds);
        submit_round_1 = findViewById(R.id.button_submit_round_1);


        //Override the enter key for each edit text
        p.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER ){ return true;
                }


                    return false;
            }
        });
        pi_h.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {




                return true;
            }
                return false;
   }
        });
        pi_l.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER ){




                    return true;
                }

                return false;
            }
        });

        rounds.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER ){

                    submit_round_1.performClick();
                    return true;
                }

                return false;
            }
        });

        if(market!=null){
        p.setText(market.getP().toString());
        pi_h.setText(market.getPi_h().toString());
        pi_l.setText(market.getPi_l().toString());
        rounds.setText(market.getNum_rounds().toString());
    }

        submit_round_1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Float fp = Float.parseFloat(p.getText().toString());
            Float fpi_h = Float.parseFloat(pi_h.getText().toString());
            Float fpi_l = Float.parseFloat(pi_l.getText().toString());
            Integer num_round = Integer.parseInt(rounds.getText().toString());

            if (!(fp==null || fpi_h==null || fpi_l==null || num_round==null)) {
                Market round_1 = new Market(fpi_h,fpi_l,fp, num_round);

                SessionDatabaseReference SDR  = (SessionDatabaseReference) getApplication();



                SDR.getGlobalVarValue().child("markets").child(child).setValue(round_1).addOnCompleteListener(new OnCompleteListener<Void>() {
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
