package project.activities.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import project.objects.adapters.SectionsPageAdapter;
import project.objects.database.SessionDatabaseReference;
import project.objects.models.Man_Model;
import project.objects.models.Pricing_Model;


public class MarketPlaceForAdmin extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;
    private SessionDatabaseReference SDR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_for_admin);
        context=this;
        SDR = (SessionDatabaseReference) getApplication();

        Man_Model MM = new ViewModelProvider(this).get(Man_Model.class);
        MM.setSession_db_ref(SDR.getGlobalVarValue());
        Pricing_Model PM= new ViewModelProvider(this).get(Pricing_Model.class);
        PM.setSession_db_ref(SDR.getGlobalVarValue());

        viewPager = findViewById(R.id.marketplace_container);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabmarketplacelayout);
        tabLayout.setupWithViewPager(viewPager);


    }



    private void setUpViewPager(ViewPager viewPager){
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new View_Companies_Admin(), "Company Reports");
        adapter.addFragment(new Market_Prices_Admin(), "Stocks");
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
                Intent intent = new Intent(context, AdminHub.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
