package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Manager_Home_Page extends AppCompatActivity {
    private static final String TAG="Manager_home_page";
    private Context context;
    private ViewPager viewPager;

    private SectionsPageAdapter mSectionsPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        context=this;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        FloatingActionButton sign_out=findViewById(R.id.FABmanhome);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);
            }
        });

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.manhome_container);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabmanhome);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setUpViewPager(ViewPager viewPager){

        mSectionsPageAdapter.addFragment(new Company_status_fragment(), "Company Status");
        mSectionsPageAdapter.addFragment(new Sumbit_Manager_Report_Fragment(), "Your Report");

        viewPager.setAdapter(mSectionsPageAdapter);
    }
}