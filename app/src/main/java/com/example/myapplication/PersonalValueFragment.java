package com.example.myapplication;

import android.os.Bundle;

import Objects.Investor;
import Objects.Share;
import Objects.Share_Model;
import Objects.Vest_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class PersonalValueFragment extends Fragment {
    private final static String TAG="personal value fragment";
    TextView cash;
    TextView payout;

    Vest_Model VM;
    Share_Model SM;
    Investor invester;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_value,container, false);
        invester = (Investor)getActivity().getIntent().getSerializableExtra("investor");


        cash = view.findViewById(R.id.cash_value);
        payout = view.findViewById(R.id.value_data);


        String id = invester.getID();
        VM = new ViewModelProvider(getActivity()).get(Vest_Model.class);
        SM = new ViewModelProvider(getActivity()).get(Share_Model.class);
        SM.setId(id);
        updateCash();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        VM = new ViewModelProvider(getActivity()).get(Vest_Model.class);
        VM.getMan().observe(getViewLifecycleOwner(), new Observer<Investor>() {
            @Override
            public void onChanged(Investor investor) {
                invester = investor;
                updateCash();

            }
        });

        SM = new ViewModelProvider(getActivity()).get(Share_Model.class);
        SM.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                //something
            }
        });




    }
    public void updateCash(){
        cash.setText(invester.getCash().toString());
        payout.setText(invester.getValue().toString());
    }
}
