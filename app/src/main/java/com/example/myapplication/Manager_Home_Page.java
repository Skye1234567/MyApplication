package com.example.myapplication;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Objects.Man_Model;
import Objects.Manager;
import Objects.One_Man_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

public class Manager_Home_Page extends AppCompatActivity {
    private static final String TAG="Manager_home_page";
    private Context context;
    private ViewPager viewPager;
    private Manager manager;

    private SectionsPageAdapter mSectionsPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        context=this;
        manager =(Manager) getIntent().getSerializableExtra("manager");
        One_Man_Model MM = new ViewModelProvider(this).get(One_Man_Model.class);
        MM.setMan(manager);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.manhome_container);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabmanhome);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setUpViewPager(ViewPager viewPager){

        mSectionsPageAdapter.addFragment(new Company_status_fragment(), "Company Status");
        mSectionsPageAdapter.addFragment(new CompanyReportFragment(), "Your Report");

        viewPager.setAdapter(mSectionsPageAdapter);
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


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}