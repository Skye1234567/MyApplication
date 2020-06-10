package com.example.myapplication;

import Objects.ManAdapter;
import Objects.Man_Model;
import Objects.Manager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class View_Companies extends Fragment {
    private Man_Model mm;

    private ArrayList<Manager> managersArray;
    private ManAdapter manAdapter;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_view__companies,container, false);
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        managersArray = new ArrayList<Manager>();


        mm.update_manager();


        manAdapter = new ManAdapter(getContext(), managersArray);
        listView = view.findViewById(R.id.list_of_companies);
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
                manAdapter.clear();
                manAdapter.addAll(stringManagerHashMap.values());


            }
        });



    }


}