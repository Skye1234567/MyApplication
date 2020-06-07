package com.example.myapplication;

import android.os.Bundle;

import Objects.Investor;
import Objects.Vest_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PersonalValueFragment extends Fragment {
    private final static String TAG="personal value fragment";
    TextView cash;
    TextView payout;
    Vest_Model VM;
    Investor invester;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_value,container, false);
        invester = (Investor)getActivity().getIntent().getSerializableExtra("investor");


        cash = view.findViewById(R.id.cash_value);
        payout = view.findViewById(R.id.value_account);


        String id = invester.getID();
        VM = new ViewModelProvider(getActivity()).get(Vest_Model.class);
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



    }
    public void updateCash(){
        cash.setText(invester.getCash().toString());
        //payout.setText(invester.getValue());
    }
}
