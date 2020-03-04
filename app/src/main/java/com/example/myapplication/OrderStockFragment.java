package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class OrderStockFragment extends Fragment{
    private final static  String TAG = "Order Stock Fragment";
    Spinner spinner ;
    Spinner spinner2 ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_stock,container, false);
        String user_id = getActivity().getIntent().getStringExtra("user_id");

        spinner = view.findViewById(R.id.spinner_buy_sell);
        spinner2 = view.findViewById(R.id.spinner_stocks);
        final ArrayList list1 = new ArrayList();
        final ArrayList list2 = new ArrayList();
        list2.add("Buy");
        list2.add("Sell");
        Query q = FirebaseDatabase.getInstance().getReference().child("Managers");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d :dataSnapshot.getChildren()){
                    Integer index = d.getKey().length();
                   char[] characters = d.getKey().toCharArray();


                   String symbol = Arrays.copyOfRange(characters, index-5, index-1).toString();
                   list1.add(symbol);

                }
                update_Company_Symbols(list1, list2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "trouble getting the managers from database");

            }
        });


         return view;
    }

    public void update_Company_Symbols(ArrayList<String> companies, ArrayList <String> buysell){
        ArrayAdapter<String> adapterbuy = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,buysell);
        ArrayAdapter<String> adapterstocks = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,companies);
        spinner.setAdapter(adapterbuy);
        spinner2.setAdapter(adapterstocks);



    }



}
