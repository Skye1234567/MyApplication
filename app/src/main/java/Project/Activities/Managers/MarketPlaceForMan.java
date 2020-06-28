package Project.Activities.Managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import Project.Activities.Investors.Investor_Round_Intro;
import Project.Activities.Player.MainActivity;
import Project.Objects.Adapters.SectionsPageAdapter;
import Project.Business_Logic.Accountant;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import Project.Objects.Models.One_Man_Model;
import Project.Objects.Personel.Investor;
import Project.Objects.Models.Man_Model;
import Project.Objects.Models.Pricing_Model;
import Project.Objects.Handlers.RoundHandler;
import Project.Objects.Models.Share_Model;
import Project.Objects.Models.Trade_Model;
import Project.Objects.Models.Vest_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


public class MarketPlaceForMan extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;

    private RoundHandler RH;
    private Intent future_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_for_man);
        context=this;
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

       future_intent = new Intent(this, Manager_Home_Page.class);
       RH = new RoundHandler(context, future_intent);
       new Thread(RH).start();

        Man_Model MM = new ViewModelProvider(this).get(Man_Model.class);
        Pricing_Model PM= new ViewModelProvider(this).get(Pricing_Model.class);
        One_Man_Model one_man_model = new ViewModelProvider(this).get(One_Man_Model.class);
        one_man_model.set_id(id);



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
                RH.destroy_round();
                finish();
                return true;
            case R.id.reset:

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
