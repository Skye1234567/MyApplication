package com.example.myapplication;

import Objects.Man_Model;
import Objects.Manager;
import Objects.Market;
import Objects.One_Man_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Business_Logic.Accountant;
import com.example.myapplication.Business_Logic.Manager_Logic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Company_status_fragment extends Fragment {
    One_Man_Model man_model;
    Context context;
    TextView profit;
    TextView performance;
    TextView assets;
    TextView audit;
    TextView dividend;
    SwipeRefreshLayout SR;
    final String TAG ="company status";
    Accountant accountant;
    Manager managerstat;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__company_status,container, false);
        context = getContext();
        accountant = new Accountant();
        profit = view.findViewById(R.id.profit_info);
        assets = view.findViewById(R.id.assets_info);
        performance = view.findViewById(R.id.performance_info);
        audit = view.findViewById(R.id.audit_info);
        dividend = view.findViewById(R.id.dividend_info);
        managerstat = (Manager) getActivity().getIntent().getSerializableExtra("manager");

        Manager_Logic mLogic = new Manager_Logic( managerstat.getCompany_symbol(), managerstat.getID());
        mLogic.allocate_shares();

        man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);



        FirebaseDatabase.getInstance().getReference().child("markets").child("practice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Market market = dataSnapshot.getValue(Market.class);
                try{
                accountant.generate_company_data(market.getP());
                accountant.generate_round_data(market.getPi_h(), market.getPi_l());

                managerstat.setPerformance(accountant.getPerformance());

                managerstat.setProfit(accountant.getRevenue());
                    //FirebaseDatabase.getInstance().getReference().child("Managers").child(managerstat.getID()).setValue(managerstat);
                }catch (NullPointerException e){Log.e("market null", e.getMessage());}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());

            }
        });




        SR = view.findViewById(R.id.refresh_comp_stat);
        SR.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update_Manager_status();
            }
        });

        return view;
    }

    private void update_Manager_status() {
        switch (managerstat.getPerformance()){
            case 0:
                performance.setText("Low");
                break;
            case 1:
                performance.setText("High");
                break;
        }
        Integer rev = managerstat.getProfit();
        profit.setText(rev.toString());
        assets.setText(managerstat.getCash().toString());
        //TODO: accept audit stuff in comment
        if (managerstat.getAudit_choice()==1 /*&&managerstat.getAccept_audit()==1*/){
            audit.setText("Yes");
        }else audit.setText("No");
        if (managerstat.getReport_dividend()==1) dividend.setText("50");
        else dividend.setText("0");

        SR.setRefreshing(false);

        FirebaseDatabase.getInstance().getReference().child("Managers").child(managerstat.getID()).setValue(managerstat);

    }

   @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                managerstat=manager;
                update_Manager_status();


            }
        });

    }
}
