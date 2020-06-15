package com.example.myapplication;

import android.os.Bundle;

import Objects.Investor;
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
import android.widget.ListView;

import java.util.ArrayList;


public class ActiveTradesFragment extends Fragment {
    private final static String TAG="active trades page";

    Trade_Model TM;
    ArrayList<Trade> buytradeArray;
    ArrayList<Trade> selltradeArray;
    TradeAdapter buytradeAdapter;
    TradeAdapter selltradeAdapter;

    ListView listViewbuy;
    ListView listViewsell;
    Investor investor;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
    }
    public void update_Adapter(){
        buytradeAdapter.clear();
        buytradeAdapter.addAll(buytradeArray);
        selltradeAdapter.clear();
        selltradeAdapter.addAll(selltradeArray);
    }
}
