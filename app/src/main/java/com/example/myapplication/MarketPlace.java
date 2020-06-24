package com.example.myapplication;

import Objects.Investor;
import Objects.Man_Model;
import Objects.Pricing_Model;
import Objects.Share_Model;
import Objects.Trade_Model;
import Objects.Vest_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Business_Logic.Accountant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MarketPlace extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;
    private  String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        context=this;
        Investor i = (Investor)getIntent().getSerializableExtra("investor");
       id = i.getID();
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
        adapter.addFragment(new View_Companies(), "Company Reports");
        adapter.addFragment(new Investor_Instructions_Fragment(), "Your  Stocks");
        adapter.addFragment(new ActiveTradesFragment(), "Active Trades");
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
}