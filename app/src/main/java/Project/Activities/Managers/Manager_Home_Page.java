package Project.Activities.Managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

import Project.Activities.Player.MainActivity;
import Project.Objects.Adapters.SectionsPageAdapter;
import Project.Objects.Personel.Manager;
import Project.Objects.Models.One_Man_Model;
import Project.Objects.Handlers.RoundHandler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import static java.lang.Math.abs;

public class Manager_Home_Page extends AppCompatActivity {
    private static final String TAG="Manager_home_page";
    private Context context;
    private ViewPager viewPager;
    private Timer timer;



    private SectionsPageAdapter mSectionsPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        context=this;
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
           @Override
           public void run() {
               Intent intent = new Intent(context, MarketPlaceForMan.class);
               startActivity(intent);



           }
       };
        timer.schedule(timerTask, 120000);

        One_Man_Model MM = new ViewModelProvider(this).get(One_Man_Model.class);


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
                timer.cancel();
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
        super.onDestroy();
        timer.cancel();
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}