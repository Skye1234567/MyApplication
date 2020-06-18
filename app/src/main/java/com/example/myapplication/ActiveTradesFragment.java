package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import Objects.Investor;
import Objects.Price;
import Objects.Pricing_Model;
import Objects.Trade;
import Objects.TradeAdapter;
import Objects.Trade_Model;
import Objects.Value_Assessor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class ActiveTradesFragment extends Fragment {
    private final static String TAG="active trades page";

    Trade_Model TM;
    ArrayList<Trade> buytradeArray;
    ArrayList<Trade> selltradeArray;
    TradeAdapter buytradeAdapter;
    TradeAdapter selltradeAdapter;
    Pricing_Model pricing_model;
    HashMap<String, Price> hm= new HashMap<>();
    ListView listViewbuy;
    ListView listViewsell;
    Investor investor;
    @Nullable
    @Override

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_active_trades,container, false);
        investor = (Investor) getActivity().getIntent().getSerializableExtra("investor");
        TM = new ViewModelProvider(getActivity()).get(Trade_Model.class);
        buytradeArray = new ArrayList<Trade>();
        selltradeArray = new ArrayList<Trade>();
        TM.setId(investor.getID());
        TM.update_trade();
        buytradeAdapter = new TradeAdapter(getContext(), buytradeArray);
        selltradeAdapter = new TradeAdapter(getContext(), selltradeArray);
        listViewbuy = view.findViewById(R.id.tradeslistbuy);
        listViewsell = view.findViewById(R.id.tradeslistsell);
        listViewbuy.setAdapter(buytradeAdapter);
        listViewsell.setAdapter(selltradeAdapter);
        listViewsell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), editorderActivity.class);
                Trade t = (Trade)parent.getItemAtPosition(position);
                intent.putExtra("price", hm.get(t.getCompany()));
                intent.putExtra("trade",t);
                intent.putExtra("bos", "Sell");
                intent.putExtra("investor",investor);
                startActivity(intent);


            }
        });
        listViewbuy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), editorderActivity.class);
                intent.putExtra("trade",(Trade)parent.getItemAtPosition(position));
                intent.putExtra("bos", "Buy");
                intent.putExtra("investor",investor);
                startActivity(intent);


            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TM = new ViewModelProvider(getActivity()).get(Trade_Model.class);
        TM.getTrades().observe(getViewLifecycleOwner(), new Observer<ArrayList<ArrayList<Trade>>>() {
            @Override
            public void onChanged(ArrayList<ArrayList<Trade>> arrayLists) {
                buytradeArray = arrayLists.get(0);
                selltradeArray = arrayLists.get(1);
                update_Adapter();}

        });

    pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model .class);
        pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
        @Override
        public void onChanged(HashMap<String, Price> stringPriceHashMap) {
            hm = stringPriceHashMap;



        }
    });}
    public void update_Adapter(){
        buytradeAdapter.clear();
        buytradeAdapter.addAll(buytradeArray);
        selltradeAdapter.clear();
        selltradeAdapter.addAll(selltradeArray);
    }
}
