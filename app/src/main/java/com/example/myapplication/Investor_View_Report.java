package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import Objects.ManAdapter;
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

private Share_Model sm;
private String Cmpany;
private ArrayList<Manager> managersArray;
 private ManAdapter manAdapter;
 private ListView listView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_company_report,container, false);
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
      managersArray = new ArrayList();


      mm.update_manager();


      manAdapter = new ManAdapter(getContext(), managersArray);
       listView = view.findViewById(R.id.company_report_table);

       listView.setAdapter(manAdapter);



        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.getMan().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Manager>>() {
            @Override
            public void onChanged(HashMap<String, Manager> stringManagerHashMap) {
                managersArray.clear();
                managersArray.addAll(stringManagerHashMap.values());
                updateAdapter();


            }
        });



    }

private void updateAdapter(){
        manAdapter.clear();
        manAdapter.addAll(managersArray);

}
}
