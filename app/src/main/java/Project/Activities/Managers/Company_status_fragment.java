package Project.Activities.Managers;

import Project.Objects.Database.SessionDatabase;
import Project.Objects.Database.SessionTimeDatabase;
import Project.Objects.Economics.Schedule;
import Project.Objects.Economics.Session;
import Project.Objects.Handlers.ManHash;
import Project.Objects.Personel.Manager;
import Project.Objects.Economics.Market;
import Project.Objects.Models.One_Man_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Project.Business_Logic.Accountant;
import Project.Business_Logic.Manager_Logic;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Company_status_fragment extends Fragment {
    One_Man_Model man_model;
    Context context;
    TextView profit;
    TextView performance;
    TextView assets;
    TextView audit;
    TextView dividend;
    Session session;
    SwipeRefreshLayout SR;
    final String TAG ="company status";
    Accountant accountant;
    Manager managerstat;
    Integer current_round;
    SessionTimeDatabase STD;
    DatabaseReference REF;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__company_status,container, false);
        context = getContext();

        profit = view.findViewById(R.id.profit_info);
        assets = view.findViewById(R.id.assets_info);
        performance = view.findViewById(R.id.performance_info);
        audit = view.findViewById(R.id.audit_info);
        dividend = view.findViewById(R.id.dividend_info);
        String id  = FirebaseAuth.getInstance().getCurrentUser().getUid();
        REF = FirebaseDatabase.getInstance().getReference("Managers").child(id);
        accountant = new Accountant(REF);
        STD = new SessionTimeDatabase();
        STD.addObserver(new java.util.Observer() {
            @Override
            public void update(Observable o, Object arg) {
               current_round= STD.getCurrentRound();
               update_Manager_status();

            }
        });
        STD.setParam();
        //TODO shift to onemanobvserer
      //  Manager_Logic mLogic = new Manager_Logic( managerstat.getCompany_symbol(), managerstat.getID());
        //mLogic.allocate_shares();


        man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        man_model.set_id(id);



        SessionDatabase SD =  new SessionDatabase();
        SD.addObserver(new java.util.Observer() {
            @Override
            public void update(Observable o, Object arg) {
                session = (Session) arg;
                update_Manager_status();

            }
        });
        SD.setParam();


        return view;
    }

    private void update_Manager_status() {
        if (current_round != null && session != null) {
            Market m = session.round_to_market(current_round);
            accountant.generate_company_data(m.getP());
            accountant.generate_round_data(m.getPi_h(), m.getPi_l());



        }
    }
    public void updateUI(){
        if(managerstat!=null){
        ManHash MH= new ManHash();
        profit.setText(managerstat.getProfit().toString());
        performance.setText(MH.highLowHash(managerstat.getPerformance()));
        assets.setText(managerstat.getCash().toString());
        dividend.setText(MH.YesNOHash(managerstat.getReport_dividend()));
        audit.setText(MH.YesNOHash(managerstat.getAudit_choice()));}
    }
        @Override
        public void onActivityCreated (@Nullable Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
            man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
                @Override
                public void onChanged(Manager manager) {
                    managerstat = manager;
                    updateUI();


                }
            });

        }
    }
