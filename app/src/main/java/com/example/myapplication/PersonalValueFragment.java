package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import Objects.Investor;
import Objects.Player;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class PersonalValueFragment extends Fragment {
    private final static String TAG="personal value fragment";
    TextView textView;
    TextView payout;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_value,container, false);
        String user_id = getActivity().getIntent().getStringExtra("user_id");
        textView = (TextView) view.findViewById(R.id.cash_value);
        payout = (TextView) view.findViewById(R.id.value_account);

        Investor investor = (Investor)getActivity().getIntent().getSerializableExtra("investor");
        String id = investor.getID();
        Query q = FirebaseDatabase.getInstance().getReference().child("player_list").child(id);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Player current  =dataSnapshot.getValue(Player.class);
                String cash = current.getCash().toString();
                String value = current.getValue().toString();
                textView.setText(cash);
                payout.setText(value);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "data listener for the cash value for a player cancelled");
            }
        });

        return view;
    }
}
