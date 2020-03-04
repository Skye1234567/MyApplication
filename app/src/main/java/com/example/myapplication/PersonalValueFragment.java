package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import Objects.Player;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    TextView textView;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_value,container, false);
        String user_id = getActivity().getIntent().getStringExtra("user_id");
        textView = (TextView) view.findViewById(R.id.cash_value);

        String id = getActivity().getIntent().getStringExtra("user_id");
        Query q = FirebaseDatabase.getInstance().getReference().child("player_list").child(id);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Player current  =dataSnapshot.getValue(Player.class);
                textView.setText( current.getCash().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
