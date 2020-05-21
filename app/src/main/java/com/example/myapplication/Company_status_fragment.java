package com.example.myapplication;

import Objects.Manager;
import Objects.Market;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Context context;
    TextView profit;
    TextView performance;
    TextView assets;
    TextView audit;
    TextView dividend;
    SwipeRefreshLayout SR;
    final String TAG ="company status";
    Accountant accountant;
    Manager manager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__company_status,container, false);
        Intent intent = getActivity().getIntent();
        context = getContext();
        accountant = new Accountant();
        profit = view.findViewById(R.id.profit_info);
        assets = view.findViewById(R.id.assets_info);
        performance = view.findViewById(R.id.performance_info);
        audit = view.findViewById(R.id.audit_info);
        dividend = view.findViewById(R.id.dividend_info);


        manager =(Manager) intent.getSerializableExtra("manager");
        Manager_Logic mLogic = new Manager_Logic( manager.getCompany_symbol(), manager.getID());
        mLogic.allocate_shares();
         assets.setText(manager.getCash().toString());

        FirebaseDatabase.getInstance().getReference().child("markets").child("practice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Market market = dataSnapshot.getValue(Market.class);
                try{
                accountant.generate_company_data(market.getP());
                accountant.generate_round_data(market.getPi_h(), market.getPi_l());
                }catch (NullPointerException e){Log.e("market null", e.getMessage());}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());

            }
        });



        FloatingActionButton sign_out= view.findViewById(R.id.FAB);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);

            }
        });
        SR = view.findViewById(R.id.refresh_comp_stat);
        SR.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update_Manager();
            }
        });

        return view;
    }

    private void update_Manager() {
        switch (accountant.getPerformance()){
            case 0:
                manager.setPerformance(0);
                performance.setText("Low");
                break;
            case 1:
                manager.setPerformance(1);
                performance.setText("High");
                break;
        }
        Integer rev = accountant.getRevenue();
        manager.setProfit(rev);
        profit.setText(rev.toString());
        SR.setRefreshing(false);

        FirebaseDatabase.getInstance().getReference().child("Managers").child(manager.getID()).setValue(manager);




    }


}
