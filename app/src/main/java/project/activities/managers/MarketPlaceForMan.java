package project.activities.managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Observable;
import java.util.Observer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import project.activities.player.GameMenu;
import project.activities.player.MainActivity;
import project.objects.adapters.SectionsPageAdapter;
import project.objects.database.ALLOWDatabase;
import project.objects.database.SessionDatabaseReference;
import project.objects.models.Man_Model;
import project.objects.models.One_Man_Model;
import project.objects.models.Pricing_Model;

public class MarketPlaceForMan extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;
    private ALLOWDatabase allowDatabase;
    private SessionDatabaseReference SDR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_for_man);
        context=this;
        String id="";
        try {id = FirebaseAuth.getInstance().getCurrentUser().getUid();}catch (NullPointerException e){
            startActivity(new Intent(context, MainActivity.class));

        }
        SDR = (SessionDatabaseReference) getApplication();
        allowDatabase = new ALLOWDatabase(SDR.getGlobalVarValue());
        allowDatabase.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (!(boolean)arg) {
                    startActivity(new Intent(context,Manager_Home_Page.class));
                }
            }
        });
        allowDatabase.addListener();


        Man_Model MM = new ViewModelProvider(this).get(Man_Model.class);
        MM.setSession_db_ref(SDR.getGlobalVarValue());
        Pricing_Model PM= new ViewModelProvider(this).get(Pricing_Model.class);
        PM.setSession_db_ref(SDR.getGlobalVarValue());
        One_Man_Model one_man_model = new ViewModelProvider(this).get(One_Man_Model.class);
        one_man_model.set_id(id);
        one_man_model.setSession_db_ref(SDR.getGlobalVarValue());



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
                Intent intent = new Intent(context, GameMenu.class);
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
