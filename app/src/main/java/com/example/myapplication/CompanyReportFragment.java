package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import Objects.Man_Model;
import Objects.Manager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CompanyReportFragment extends Fragment {
    Context context;
    Man_Model man_model;
    Manager managerman;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_report,container, false);

        Intent intent = getActivity().getIntent();
        context = getContext();


        Button b = view.findViewById(R.id.no_audit);
        Button b2 = view.findViewById(R.id.yes_audit);
        final Button marketplace = view.findViewById(R.id.to_market);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketplace.setVisibility(View.VISIBLE);


            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketplace.setVisibility(View.VISIBLE);


            }
        });

        marketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context,Wait_Page.class);
                intent.putExtra("user_id", managerman.getID());
                context.startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        man_model = new ViewModelProvider(getActivity()).get(Man_Model.class);
        man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                managerman = manager;
                updateReportManager();

            }
        });

    }

    private void updateReportManager() {

    }
}
