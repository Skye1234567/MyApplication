package com.example.myapplication;

import Objects.Investor;
import Objects.Manager;
import Objects.Share;
import Objects.ShareAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Investor_Instructions_Fragment extends Fragment {
    String in_id;

    Context context;
    ListView tableLayout;

    ArrayList<Share> investor_shares;

    Investor_Logic IL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
       View view = inflater.inflate(R.layout.activity_investor__instructions, container, false);
       context = getContext();
        final SwipeRefreshLayout SRL;
        SRL = view.findViewById(R.id.swiper_investor_instructions);
        tableLayout=view.findViewById(R.id.company_shares_table_investor_instructions);
        Investor investor = (Investor) Objects.requireNonNull(getActivity().getIntent().getExtras()).getSerializable("investor");
        in_id = investor.getID();
        investor_shares = new ArrayList<>();
        final ShareAdapter shareAdapter =new ShareAdapter(context,investor_shares);
        tableLayout.setAdapter(shareAdapter);
        if (in_id ==null){
            Toast.makeText(context, "HELP NO ID", Toast.LENGTH_LONG).show();
        }
        IL= new Investor_Logic(in_id,shareAdapter);
        IL.get_symbols();

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shareAdapter.clear();
                SRL.setRefreshing(false);

            }


        });



        return view;

    }



    public static class Investor_Logic {

        private final static String TAG="INVESTOR_LOGIC";

        private String investor_id;
        private HashMap<String, String> symbol_id;
        private ShareAdapter shareAdapter;

        public Investor_Logic(String investor_id,  ShareAdapter shareAdapter) {
           this.investor_id =investor_id;

           this.shareAdapter = shareAdapter;



        }



        public HashMap<String, String> getSymbol_id() {
            return symbol_id;
        }


        public void  get_symbols(){
           symbol_id= new HashMap<>();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            Query q = db.getReference("Managers");

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()){
                        for (DataSnapshot manager :dataSnapshot.getChildren()){
                            String manager_id = manager.getKey();
                            String company_symbol = manager.getValue(Manager.class).getCompany_symbol();
                            symbol_id.put(company_symbol,manager_id);

                        }

                    }
                    if (symbol_id!=null&& !symbol_id.isEmpty()) {
                        retrieve_investor_data(symbol_id);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "******************************can't get the investors?***********************");

                }
            });


        }




        public void retrieve_investor_data(HashMap<String, String> symbol_id) {
            Log.d(TAG, "IN RETRIEVE INVESTOR DATA");

            for (final String sym: symbol_id.keySet()){
                final String manager_val=symbol_id.get(sym);
                if (investor_id!=null) {
                    final Query shares = FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(sym);
                    shares.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Share share = dataSnapshot.getValue(Share.class);
                            if (share == null) {
                                DatabaseReference r = FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(sym);
                                r.setValue(new Share(investor_id, sym, manager_val));
                                shareAdapter.add(new Share(investor_id, sym, manager_val));

                            } else shareAdapter.add(share);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                }

             }






        }
    }
}
