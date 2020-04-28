package com.example.myapplication;

import Objects.Database_callback_investor_instructions;
import Objects.Share;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Business_Logic.Investor_Logic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Investor_Instructions extends AppCompatActivity {
    String investor_id;
    TextView company1;
    TextView company2;
    TextView company3;
    TextView company4;
    SwipeRefreshLayout SRL;
    SwipeRefreshLayout.OnRefreshListener ORL;

    TextView numshare1;
    TextView numshare2;
    TextView numshare3;
    TextView numshare4;
    Context context;

    ArrayList<Share> investor_shares;

    Investor_Logic IL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;


        SRL = findViewById(R.id.swiper_investor_instructions);
        setContentView(R.layout.activity_investor__instructions);
        company1 = findViewById(R.id.company1);
        company2= findViewById(R.id.company2);
        company3= findViewById(R.id.company3);
        company4= findViewById(R.id.company4);

        numshare1= findViewById(R.id.numshares1);
        numshare2=findViewById(R.id.numshares2);
        numshare3=findViewById(R.id.numshares3);
        numshare4=findViewById(R.id.numshares4);
        investor_id = getIntent().getStringExtra("user_id");
        investor_shares = new ArrayList<>();
        IL= new Investor_Logic(investor_id);
        ORL = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                IL.get_symbols();
                while (!IL.isComplete_id()){

                }
                IL.retrieve_investor_data(IL.getSymbol_id());
                while(!IL.isComplete_shares()){

                }
                investor_shares = IL.getInvestor_shares();
                int i=0;

                for (Share s: investor_shares){
                    switch (i){
                        case 0:
                            company1.setText(s.getCompany());
                            numshare1.setText(s.getNumber());
                            break;

                        case 1:
                            company2.setText(s.getCompany());
                            numshare2.setText(s.getNumber());
                            break;
                        case 2:
                            company3.setText(s.getCompany());
                            numshare3.setText(s.getNumber());
                            break;

                        case 3:
                            company4.setText(s.getCompany());
                            numshare4.setText(s.getNumber());
                            break;}
                            i+=1;
                }

                }


        };

        SRL.setOnRefreshListener(ORL);




        Button b = findViewById(R.id.proceed_to_market);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Wait_Page.class);
                intent.putExtra("user_id", investor_id );
                context.startActivity(intent);
                finish();
            }
        });
        FloatingActionButton sign_out=findViewById(R.id.FAB);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);
                finish();
            }
        });




    }


}
