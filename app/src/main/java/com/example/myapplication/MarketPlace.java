package com.example.myapplication;

import Objects.Investor;
import Objects.Man_Model;
import Objects.Pricing_Model;
import Objects.Share_Model;
import Objects.Trade;
import Objects.Vest_Model;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class MarketPlace extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        context=this;
        Investor i = (Investor)getIntent().getSerializableExtra("investor");
        String id = i.getID();
        Share_Model SM= new ViewModelProvider(this).get(Share_Model.class);
        SM.setId(id);
        Vest_Model VM =  new ViewModelProvider(this).get(Vest_Model.class);
        VM.setId(id);
        Man_Model MM = new ViewModelProvider(this).get(Man_Model.class);
        Pricing_Model PM= new ViewModelProvider(this).get(Pricing_Model.class);
        PM.setCurrent_user_id(id);





        FloatingActionButton sign_out=findViewById(R.id.FAB);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);
            }
        });


        viewPager = findViewById(R.id.marketplace_container);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabmarketplacelayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setUpViewPager(ViewPager viewPager){
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Investor_View_Report(), "Company Reports");
        adapter.addFragment(new Investor_Instructions_Fragment(), "Your  Stocks");
        viewPager.setAdapter(adapter);
    }
}