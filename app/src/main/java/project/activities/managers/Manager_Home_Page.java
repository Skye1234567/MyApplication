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

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import project.activities.player.GameMenu;
import project.objects.adapters.SectionsPageAdapter;
import project.objects.database.ALLOWDatabase;
import project.objects.database.SessionDatabaseReference;

public class Manager_Home_Page extends AppCompatActivity {
    private static final String TAG="Manager_home_page";
    private Context context;
    private ViewPager viewPager;
    private SessionDatabaseReference SDR;
    private ALLOWDatabase allowDatabase;


    private SectionsPageAdapter mSectionsPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        context=this;
        SDR = (SessionDatabaseReference) getApplication();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);



        allowDatabase = new ALLOWDatabase(SDR.getGlobalVarValue());
        allowDatabase.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if ((boolean)arg) {
                    startActivity(new Intent(context, MarketPlaceForMan.class));
                }
            }
        });
        allowDatabase.addListener();


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
                allowDatabase.deleteObservers();
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