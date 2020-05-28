package com.example.myapplication;

import android.os.Bundle;

import Objects.Database_callback_current_bid;
import Objects.Database_callback_order_stock;
import Objects.Investor;
import Objects.Share;
import Objects.Share_Model;
import Objects.Trade;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;


public class OrderStockFragment extends Fragment{
    private final static  String TAG = "Order Stock Fragment";
    private Spinner spinner ;
    private Spinner spinner2 ;
    private Button order_stock;
    private EditText quantity;
    private EditText price;
    private TextView current_bid;
    private TextView quantity_owned;
    private String user_id;
    final private String high_bid = "high_bid";
    private Share my_share;
    private Share_Model sm;


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        sm.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                ArrayList<String> a = new ArrayList<>();
                for (Share s : shares){
                    a.add(s.getCompany());
                }
                ArrayList<String>b =new ArrayList<String>();
                b.add("Buy");
                b.add("Sell");
               update_Company_Symbols(a, b);

            }
        });

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_stock,container, false);
        Investor investor = (Investor) getActivity().getIntent().getSerializableExtra("investor");
        user_id = investor.getID();
        current_bid = view.findViewById(R.id.current_bid);
        my_share=new Share( );
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        sm.setId(user_id);

        spinner = view.findViewById(R.id.spinner_buy_sell);
        spinner2 = view.findViewById(R.id.spinner_stocks);
        quantity_owned= view.findViewById(R.id.quantity);


        /*Query q = FirebaseDatabase.getInstance().getReference().child("Managers");


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d :dataSnapshot.getChildren()){
                   String index = d.child("company_symbol").getValue(String.class);

                   list1.add(index);

                }
                update_Company_Symbols(list1, list2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "trouble getting the managers from database");

            }
        });*/
        quantity = view.findViewById(R.id.enter_number_of_stocks);
        price = view.findViewById(R.id.money_sign);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();


                getSharesDataFromFirebase(new Database_callback_order_stock() {
                    @Override
                    public void execute_upon_retrieval(Share current_share) {
                        my_share=current_share;



                    }
                }, s);
                getcurrentbidDataFromFirebase(new Database_callback_current_bid() {
                    @Override
                    public void execute_upon_retrieval(Float number) {
                        setHigh_bid(number);

                    }
                }, s);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        order_stock = view.findViewById(R.id.place_order_button);
        order_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer num_shares;
                Float dollars;
                Trade trade;


                if (quantity.getText()!=null&& price.getText()!=null) {
                    try {
                        num_shares = Integer.getInteger(quantity.toString());
                        dollars = Float.parseFloat(price.toString());
                        String bs = spinner.getSelectedItem().toString();
                        String Company = spinner2.getSelectedItem().toString();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference ref_shares;
                        ref_shares= db.getReference("Trades").child("Sell").child(Company);
                        trade = new Trade(num_shares, dollars);

                        if(bs.compareTo("Sell")==0){
                            my_share.setStatus("S");
                            trade.setFor_sale(true);
                            trade.setSeller_id(user_id);


                            if (my_share.getNumber()>=num_shares){
                                ref_shares.setValue(trade);
                                db.getReference("Companies").child(my_share.getCompany()).child("for_sale").push().setValue(my_share);
                            }
                            else {
                                Toast.makeText(getActivity(), "You Cannot Sell More Shares Than You Have!", Toast.LENGTH_LONG).show();}


                        }
                        else if (bs.compareTo("Buy")==0){
                            my_share.setStatus("B");
                            trade.setFor_sale(false);
                            trade.setBuyer_id(user_id);
                            FirebaseDatabase.getInstance().getReference(high_bid).setValue(Integer.parseInt(price.getText().toString()));
                            ref_shares= db.getReference("Trades").child("Buy").child(Company);

                            ref_shares.setValue(trade);


                            Toast.makeText(getActivity(), "Buy stock clicked", Toast.LENGTH_LONG).show();
                        }

                    }
                    catch (Exception e){
                        Log.d(TAG, e.getMessage());
                    }
                }

            }
        });



        return view;




    }

    private void update_Company_Symbols(ArrayList<String> companies, ArrayList <String> buysell){
        ArrayAdapter<String> adapterbuy = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,buysell);
        ArrayAdapter<String> adapterstocks = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,companies);
        spinner.setAdapter(adapterbuy);
        spinner2.setAdapter(adapterstocks);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }




    private void getcurrentbidDataFromFirebase(final Database_callback_current_bid current_bid, String s) {
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        Query query = db.getReference("Companies").child(s).child(high_bid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float num = dataSnapshot.getValue(Float.class);
                current_bid.execute_upon_retrieval(num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "getting Data from firebase trouble:"+ databaseError.getMessage());

            }
        });


    }

    private void getSharesDataFromFirebase(final Database_callback_order_stock callback_order_stock, String s) {
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference("Shares").child(user_id).child(s);
       dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Share share = dataSnapshot.getValue(Share.class);
                if (share!=null){
                    my_share = share;

                callback_order_stock.execute_upon_retrieval(share);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "getting Data from firebase trouble:"+ databaseError.getMessage());

            }
        });
    }

    private void set_quantity_shares(Integer num_share_for_company_selected){
        if (num_share_for_company_selected!=null){
        quantity_owned.setText(num_share_for_company_selected.toString());}

    }

    private void setHigh_bid(Float high_bid){
        if (high_bid!=null) {
            current_bid.setText(high_bid.toString());
        }

    }
}
