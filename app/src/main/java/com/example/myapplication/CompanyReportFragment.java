package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import Objects.Man_Model;
import Objects.Manager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Business_Logic.Auditor;


public class CompanyReportFragment extends Fragment {
    Context context;
    Man_Model man_model;
    Manager managerman;
    Button aud_yes;
    Button aud_no;
    Button div_yes;
    Button div_no;
    Button Accept_Report;
    TextView Audit_result_textview;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__enter_report,container, false);


        context = getContext();
        man_model = new ViewModelProvider(getActivity()).get(Man_Model.class);
        managerman=man_model.getMan().getValue();
         aud_yes = view.findViewById(R.id.yes_audit);
         aud_no = view.findViewById(R.id.no_audit);
        final Button marketplace = view.findViewById(R.id.to_market);
        aud_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerman.setAudit_choice(0); }
        });
        aud_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerman.setAudit_choice(1);
                int auditor_report = new Auditor(managerman.getPerformance()).generateReport(managerman.getProfit());

            }
        });

        marketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context,Wait_Page.class);
                intent.putExtra("user_id", managerman.getID());
                context.startActivity(intent);
            }
        });
        div_no = view.findViewById(R.id.no_dividend);
        div_yes =view.findViewById(R.id.yes_dividend);
        div_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerman.setReport_dividend(0);
            }
        });
        div_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerman.setReport_dividend(1);
            }

        });
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        man_model = new ViewModelProvider(getActivity()).get(Man_Model.class);
        man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                managerman = manager;
                updateReportManager();

            }
        });

    }

    private void updateReportManager() {

    }
}
