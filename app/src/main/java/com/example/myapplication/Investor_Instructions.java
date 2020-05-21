package com.example.myapplication;

import Objects.Investor;
import Objects.Manager;
import Objects.Share;
import Objects.ShareAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Investor_Instructions extends AppCompatActivity {
    String investor_id;
    SwipeRefreshLayout.OnRefreshListener ORL;
    Context context;
    ListView tableLayout;

    ArrayList<Share> investor_shares;

    Investor_Logic IL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_investor__instructions);

        final SwipeRefreshLayout SRL;
        SRL = findViewById(R.id.swiper_investor_instructions);
        tableLayout=findViewById(R.id.company_shares_table_investor_instructions);
        Investor investor = (Investor) getIntent().getSerializableExtra("investor");
        investor_id = investor.getID();
        investor_shares = new ArrayList<>();
        final ShareAdapter shareAdapter =new ShareAdapter(context,investor_shares);
        tableLayout.setAdapter(shareAdapter);
        IL= new Investor_Logic(investor_id,shareAdapter);
        IL.get_symbols();

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shareAdapter.clear();
                SRL.setRefreshing(false);

            }


        });

        Button b = findViewById(R.id.proceed_to_market);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Wait_Page.class);
                intent.putExtra("user_id", investor_id );
                context.startActivity(intent);
                finish();
            }
        });
        FloatingActionButton sign_out=findViewById(R.id.FAB);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent);
                finish();
            }
        });




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


        public void get_symbols(){
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
                   retrieve_investor_data(symbol_id);


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
                final Query shares =FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(sym);
                shares.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Share share = dataSnapshot.getValue(Share.class);
                        if (share==null){
                            DatabaseReference r = FirebaseDatabase.getInstance().getReference("Shares").child(investor_id).child(sym);
                            r.setValue(new Share(investor_id,sym, manager_val ));
                            shareAdapter.add(new Share(investor_id,sym, manager_val ));

                        }else shareAdapter.add(share);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });



             }






        }
    }
}
