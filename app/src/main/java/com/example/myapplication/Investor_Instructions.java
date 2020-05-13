package com.example.myapplication;

import Objects.Share;
import Objects.ShareAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.myapplication.Business_Logic.Investor_Logic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Investor_Instructions extends AppCompatActivity {
    String investor_id;
    SwipeRefreshLayout.OnRefreshListener ORL;
    Context context;
    ListView tableLayout;

    ArrayList<Share> investor_shares;

    Investor_Logic IL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_investor__instructions);

        SwipeRefreshLayout SRL;
        SRL = findViewById(R.id.swiper_investor_instructions);
        tableLayout=findViewById(R.id.company_shares_table_investor_instructions);
        investor_id = getIntent().getStringExtra("user_id");
        investor_shares = new ArrayList<>();
        ShareAdapter shareAdapter =new ShareAdapter(context,investor_shares);
        tableLayout.setAdapter(shareAdapter);
        IL= new Investor_Logic(investor_id, this ,shareAdapter);

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                IL.get_symbols();




            }


        });

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
