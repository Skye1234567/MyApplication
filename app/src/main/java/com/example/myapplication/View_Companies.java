package com.example.myapplication;

import Objects.Investor;
import Objects.ManAdapter;
import Objects.Man_Model;
import Objects.Manager;
import Objects.Price;
import Objects.Pricing_Model;
import Objects.Share;
import Objects.Share_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class View_Companies extends Fragment {
    private Man_Model mm;
    private Share_Model sm;
    private Pricing_Model pricing_model;
    private ArrayList<Manager> managersArray;
    private ManAdapter manAdapter;
    private ListView listView;
    private Investor investor;
    private ArrayList<Share> my_shares;
    private HashMap<String, Price> hm;
    private Integer number_shares;
   private String company;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_view__companies,container, false);
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        managersArray = new ArrayList<Manager>();
        investor = (Investor) getActivity().getIntent().getSerializableExtra("investor");



        mm.update_manager();
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);


        manAdapter = new ManAdapter(getContext(), managersArray);
        listView = view.findViewById(R.id.list_of_companies);
        listView.setAdapter(manAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(getActivity(), orderstockActivity.class);
                 Manager manager = (Manager) parent.getItemAtPosition(position);
                 String symbol = manager.getCompany_symbol();
                 Share s = new Share();
                 s.setCompany(symbol);
                 Price p;
                 if (hm ==null) p=new Price();
                 else p= hm.get(symbol);
                 intent.putExtra("user", investor);
                 intent.putExtra("symbol", symbol);
                 intent.putExtra("share", my_shares.get(my_shares.indexOf(s)));
                 intent.putExtra("price", p);
                 startActivity(intent);


            }
        });



        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model.class);
        pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
            @Override
            public void onChanged(HashMap<String, Price> stringPriceHashMap) {
                hm = stringPriceHashMap;



            }
        });

        sm.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                my_shares = shares;

            }
        });


        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.getMan().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Manager>>() {
            @Override
            public void onChanged(HashMap<String, Manager> stringManagerHashMap) {
                manAdapter.clear();
                manAdapter.addAll(stringManagerHashMap.values());


            }
        });



    }


}