package Project.Activities.Investors;

import Project.Activities.Managers.Manager_Home_Page;
import Project.Activities.Player.MainActivity;
import Project.Objects.Database.ALLOWDatabase;
import Project.Objects.Personel.Investor;
import Project.Objects.Models.Man_Model;
import Project.Objects.Models.Pricing_Model;

import Project.Objects.Adapters.SectionsPageAdapter;
import Project.Objects.Models.Share_Model;
import Project.Objects.Models.Trade_Model;
import Project.Objects.Models.Vest_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import Project.Business_Logic.Accountant;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Observable;
import java.util.Observer;


public class MarketPlace extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;
    private  String id;
    private ALLOWDatabase allowDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        context=this;
        allowDatabase = new ALLOWDatabase();
        allowDatabase.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (!(boolean)arg) {
                    startActivity(new Intent(context,Investor_Round_Intro.class));
                }
            }
        });
        allowDatabase.addListener();

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
                new Accountant(FirebaseDatabase.getInstance().getReference()).reset_investor(id);
                Vest_Model VM  = new ViewModelProvider(this).get(Vest_Model.class);
                VM.update_investor();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
