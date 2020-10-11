package project.activities.managers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Observable;

import project.business_logic.Accountant;
import project.objects.database.SessionDatabase;
import project.objects.database.SessionDatabaseReference;
import project.objects.database.SessionTimeDatabase;
import project.objects.economics.Market;
import project.objects.economics.Session;
import project.objects.handlers.ManHash;
import project.objects.models.One_Man_Model;
import project.objects.personel.Manager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class Company_status_fragment extends Fragment {
    One_Man_Model man_model;
    Context context;
    TextView profit;
    TextView performance;
    TextView assets;
    TextView audit;
    TextView dividend;
    Session session;
    TextView round_num;
    final String TAG ="company status";
    Accountant accountant;
    Manager managerstat;
    Integer current_round;
    SessionTimeDatabase STD;
    DatabaseReference REF;
    SessionDatabaseReference SDR;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__company_status,container, false);
        context = getContext();
        round_num = view.findViewById(R.id.manager_welcome);
        SDR = (SessionDatabaseReference) getActivity().getApplication();
        profit = view.findViewById(R.id.profit_info);
        assets = view.findViewById(R.id.assets_info);
        performance = view.findViewById(R.id.performance_info);
        audit = view.findViewById(R.id.audit_info);
        dividend = view.findViewById(R.id.dividend_info);
        String id  = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SessionDatabaseReference SDR  = (SessionDatabaseReference) getActivity().getApplication();
        DatabaseReference base_ref = SDR.getGlobalVarValue();
        REF=SDR.getGlobalVarValue().child("Managers").child(id);
        accountant = new Accountant(REF);
        STD = new SessionTimeDatabase(base_ref);
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



        SessionDatabase SD =  new SessionDatabase(base_ref);
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
            round_num.setText("Welcome to round"+current_round.toString());
            Market m = session.round_to_market(current_round);
            if( m==null)  m=session.getPractice();
            if (m!=null) {
                accountant.generate_company_data(m.getP());
                accountant.generate_round_data(m.getPi_h(), m.getPi_l());
            }


        }
    }
    public void updateUI(){
        if(managerstat!=null){
        ManHash MH= new ManHash();
        if (managerstat.isValid()){
        profit.setText(managerstat.getProfit().toString());
        assets.setText(managerstat.getCash().toString());}
        performance.setText(MH.highLowHash(managerstat.getPerformance()));
        dividend.setText(MH.YesNOHash(managerstat.getReport_dividend()));
        audit.setText(MH.YesNOHash(managerstat.getAudit_choice()));}

    }
        @Override
        public void onActivityCreated (@Nullable Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
            man_model.setSession_db_ref(SDR.getGlobalVarValue());
            man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
                @Override
                public void onChanged(Manager manager) {
                    managerstat = manager;
                    updateUI();


                }
            });

        }
    }
