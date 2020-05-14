package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Business_Logic.Manager_Logic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Company_status_fragment extends Fragment {
    Context context;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__company_status,container, false);
        String user_id = getActivity().getIntent().getStringExtra("user_id");
        Intent intent = getActivity().getIntent();
        context = getContext();

        final String manager_id = intent.getStringExtra("user_id");
        final String company_symbol = intent.getStringExtra("c");
        Manager_Logic mLogic = new Manager_Logic( company_symbol, manager_id);
        mLogic.allocate_shares();
        //TODO: move the buttons to the other fragment
        /*
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
                intent.putExtra("user_id", manager_id);
                context.startActivity(intent);
            }
        });*/
        FloatingActionButton sign_out= view.findViewById(R.id.FAB);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);

            }
        });

        return view;
    }


}
