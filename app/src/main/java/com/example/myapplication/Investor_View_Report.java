package com.example.myapplication;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import Objects.Man_Model;
import Objects.Manager;
import Objects.Share;
import Objects.Share_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class Investor_View_Report extends Fragment {
    private Man_Model mm;
private TextView audit;
private TextView dividend;
private TextView performance;
private Manager managr;
private Spinner spiner;
private HashMap<Integer, String> hash;
private Share_Model sm;
private String Cmpany;
 private HashMap<Integer, String> hash2;



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
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);

        spiner = view.findViewById(R.id.spinner_stocks_commpany_eport);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cmpany = parent.getItemAtPosition(position).toString();
                mm.setSymbol(Cmpany);
                if (managr!=null) update_report_fields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                managr =manager;


            }
        });
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        sm.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                //my_shares = shares;
                ArrayList<String> a = new ArrayList<String>();
                for (Share s : shares){
                    a.add(s.getCompany());
                }
                update_spinner(a);

            }
        });

    }
    public void  update_spinner(ArrayList<String> a){
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, a);
        spiner.setAdapter(adapter);

    }
    public void update_report_fields(){
        audit.setText(hash.get(managr.getAudit_choice()));
        performance.setText(hash2.get(managr.getReport_performance()));
        dividend.setText(hash.get(managr.getReport_dividend()));

    }
}
