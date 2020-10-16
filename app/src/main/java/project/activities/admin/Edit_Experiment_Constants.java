package project.activities.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import project.activities.player.MainActivity;
import project.objects.database.SessionDatabaseReference;
import project.objects.economics.Session;

public class Edit_Experiment_Constants extends AppCompatActivity {
    private Context context;
    private SessionDatabaseReference SDR;
    private EditText starting_sum;
    private EditText audit_cost;
    private EditText big_payoff;
    private EditText small_payoff;
    private EditText dividend;
    private Button submit;
    private Session sess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__experiment__constants);
        context= this;
        sess = (Session)getIntent().getSerializableExtra("session");

        SDR = (SessionDatabaseReference) getApplication();
        if (SDR.getGlobalVarValue()==null){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
        submit = findViewById(R.id.submit_constants_session);
        starting_sum =findViewById(R.id.enter_starting_sum);
        audit_cost = findViewById(R.id.enter_audit_cost);
        big_payoff =findViewById(R.id.enter_high_profit);
        small_payoff = findViewById(R.id.enter_low_profit);
        dividend =findViewById(R.id.enter_dividend_amount);
        if(sess!=null){
            if (sess.constants_set()){
                audit_cost.setText(sess.getAudit_cost().toString());
            starting_sum.setText(sess.getStarting_sum().toString());
            dividend.setText(sess.getDividend().toString());
            small_payoff.setText(sess.getSmall_payoff().toString());
            big_payoff.setText(sess.getBig_payoff().toString());}
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

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Integer audit_int = Integer.parseInt(audit_cost.getText().toString());

                final Integer div_int = Integer.parseInt(dividend.getText().toString());
                final Integer start_int = Integer.parseInt(starting_sum.getText().toString());
                final Integer big_int = Integer.parseInt(big_payoff.getText().toString());
                final Integer small_int = Integer.parseInt(small_payoff.getText().toString());

                if (!(audit_int==null || div_int==null || start_int==null || big_int==null||small_int==null)) {


                            Session s;
                            if (sess!=null) s= sess;
                            else s=new Session();
                            s.setAudit_cost(audit_int);
                            s.setBig_payoff(big_int);
                            s.setStarting_sum(start_int);
                            s.setDividend(div_int);
                            s.setSmall_payoff(small_int);
                            SDR.getGlobalVarValue().child("markets").setValue(s).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(context, AdminHub.class);
                                            startActivity(intent);
                                        }
                                    }
                            );




            }
        }});



}
}

