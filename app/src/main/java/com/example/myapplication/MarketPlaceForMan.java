package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.Business_Logic.Accountant;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import Objects.Investor;
import Objects.Man_Model;
import Objects.Pricing_Model;
import Objects.RoundHandler;
import Objects.Share_Model;
import Objects.Trade_Model;
import Objects.Vest_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


public class MarketPlaceForMan extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;
    private  String id;
    private RoundHandler RH;
    private Intent future_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_for_man);
        context=this;
        Investor i = (Investor)getIntent().getSerializableExtra("investor");
       id = i.getID();
       future_intent = new Intent(this, Investor_Round_Intro.class);
       RH = new RoundHandler(context, future_intent);
       new Thread(RH).start();
        Share_Model SM= new ViewModelProvider(this).get(Share_Model.class);
        SM.setId(id);
        Vest_Model VM =  new ViewModelProvider(this).get(Vest_Model.class);
        VM.setId(id);
        Man_Model MM = new ViewModelProvider(this).get(Man_Model.class);
        Pricing_Model PM= new ViewModelProvider(this).get(Pricing_Model.class);
        Trade_Model TM = new ViewModelProvider(this).get(Trade_Model.class);
        TM.setId(id);

        PM.setCurrent_user_id(id);


        viewPager = findViewById(R.id.marketplace_container);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabmarketplacelayout);
        tabLayout.setupWithViewPager(viewPager);


    }



    private void setUpViewPager(ViewPager viewPager){
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new View_Companies_Manager(), "Company Reports");
        adapter.addFragment(new Market_Prices(), "Stocks");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);
                finish();
                return true;
            case R.id.reset:
                new Accountant().reset_investor(id);
                Vest_Model VM  = new ViewModelProvider(this).get(Vest_Model.class);
                VM.update_investor();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        RH.destroy_round();
        super.onDestroy();
    }
}
