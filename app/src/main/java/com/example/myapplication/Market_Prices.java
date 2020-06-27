package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import Objects.Investor;
import Objects.ManAdapter;
import Objects.Man_Model;
import Objects.Manager;
import Objects.Price;
import Objects.PriceAdapter;
import Objects.Pricing_Model;
import Objects.Share;
import Objects.Share_Model;
import Objects.Trade;
import Objects.Trade_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class Market_Prices extends Fragment {

    private Pricing_Model pricing_model;
    private ArrayList<Manager> managersArray;
    private PriceAdapter priceAdapter;
    private ListView listView;

    private HashMap<String, Price> hm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_market_prices,container, false);



        priceAdapter = new PriceAdapter( hm);
        listView = view.findViewById(R.id.list_of_prices);




        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model.class);
        pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
            @Override
            public void onChanged(HashMap<String, Price> stringPriceHashMap) {
                hm = stringPriceHashMap;



            }
        });








    }


}