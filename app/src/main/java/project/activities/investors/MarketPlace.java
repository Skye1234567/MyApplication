package project.activities.investors;

import project.activities.player.GameMenu;
import project.objects.database.ALLOWDatabase;
import project.objects.database.SessionDatabaseReference;
import project.objects.personel.Investor;
import project.objects.models.Man_Model;
import project.objects.models.Pricing_Model;

import project.objects.adapters.SectionsPageAdapter;
import project.objects.models.Share_Model;
import project.objects.models.Trade_Model;
import project.objects.models.Vest_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

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


public class MarketPlace extends AppCompatActivity {
    private static final String TAG="Marketplace";
    private Context context;
    private ViewPager viewPager;
    private SectionsPageAdapter adapter;
    private  String id;
    private ALLOWDatabase allowDatabase;
    private SessionDatabaseReference SDR;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        context=this;
        SDR= (SessionDatabaseReference) getApplication();
        allowDatabase = new ALLOWDatabase(SDR.getGlobalVarValue());
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
        SM.setSession_db_ref(SDR.getGlobalVarValue());
        Vest_Model VM =  new ViewModelProvider(this).get(Vest_Model.class);
        VM.setId(id);
        VM.setSession_db_ref(SDR.getGlobalVarValue());
        Man_Model MM = new ViewModelProvider(this).get(Man_Model.class);
        MM.setSession_db_ref(SDR.getGlobalVarValue());
        Pricing_Model PM= new ViewModelProvider(this).get(Pricing_Model.class);
        PM.setSession_db_ref(SDR.getGlobalVarValue());
        Trade_Model TM = new ViewModelProvider(this).get(Trade_Model.class);
        TM.setSession_db_ref(SDR.getGlobalVarValue());
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
        adapter.addFragment(new ActiveTradesFragment(), "Active Orders");
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

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
