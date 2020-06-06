package com.example.myapplication;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import Objects.Man_Model;
import Objects.Manager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class Investor_View_Report extends Fragment {
TextView audit;
TextView dividend;
TextView performance;
Manager managr;
HashMap<Integer, String> hash;

    HashMap<Integer, String> hash2;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_report,container, false);
        audit = view.findViewById(R.id.viewaudit);
        performance = view.findViewById(R.id.viewperformance);
        dividend = view.findViewById(R.id.viewdividend);
        hash = new HashMap<Integer, String>();
        hash2 = new HashMap<Integer, String>();
        hash.put(0, "No");
        hash.put(1, "Yes");
        hash2.put(0, "Low");
        hash2.put(1, "High");

        if (managr!=null) update_report_fields();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Man_Model mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                managr =manager;


            }
        });

    }
    public void update_report_fields(){
        audit.setText(hash.get(managr.getAudit_choice()));
        performance.setText(hash2.get(managr.getReport_performance()));
        dividend.setText(hash.get(managr.getReport_dividend()));

    }
}
