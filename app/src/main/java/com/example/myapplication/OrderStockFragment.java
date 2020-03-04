package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class OrderStockFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_stock,container, false);
        Spinner spinner = view.findViewById(R.id.spinner_buy_sell);
        Spinner spinner2 = view.findViewById(R.id.spinner_stocks);
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        list1.add("Company A");
        list1.add("Company B");
        list1.add("Company C");
        list1.add("Company D");
        list2.add("Buy");
        list2.add("Sell");
        ArrayAdapter<String> adapterbuy = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,list2);
        ArrayAdapter<String> adapterstocks = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,list1);
        spinner.setAdapter(adapterbuy);
        spinner2.setAdapter(adapterstocks);
        return view;
    }

}
