package com.example.myapplication;

import android.os.Bundle;

import Objects.Investor;
import Objects.Share;
import Objects.Share_Model;
import Objects.Trade;
import Objects.Trade_Manager;
import Objects.Vest_Model;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class OrderStockFragment extends Fragment{
    private final static  String TAG = "Order Stock Fragment";
    private ArrayList<Share> my_shares;
    private Spinner spinner ;
    private Spinner spinner2 ;
    private Share current_selection;
    private Button order_stock;
    private EditText quantity;
    private EditText price;
    private TextView current_bid;
    private TextView quantity_owned;
    private String user_id;
    final private String high_bid = "high_bid";
    private Vest_Model vm;

    private String bs;
    private String Cpany;
    private Share_Model sm;
    private String buy = "Buy";
    private String sell = "Sell";


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        sm.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                my_shares = shares;
                ArrayList<String> a = new ArrayList<>();
                for (Share s : shares){
                    a.add(s.getCompany());
                }
                ArrayList<String>b =new ArrayList<String>();
                b.add(buy);
                b.add(sell);
               update_Company_Symbols(a, b);

            }
        });

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_stock,container, false);
        final Investor investor = (Investor) getActivity().getIntent().getSerializableExtra("investor");
        user_id = investor.getID();
        current_bid = view.findViewById(R.id.current_bid);
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        sm.setId(user_id);
        vm = new ViewModelProvider(getActivity()).get(Vest_Model.class);
        vm.setId(user_id);
        spinner = view.findViewById(R.id.spinner_buy_sell);
        spinner2 = view.findViewById(R.id.spinner_stocks);
        quantity_owned= view.findViewById(R.id.quantity);
        quantity = view.findViewById(R.id.enter_number_of_stocks);
        price = view.findViewById(R.id.money_sign);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bs =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cpany = parent.getItemAtPosition(position).toString();

                for (Share sha : my_shares) {
                    if (sha.getCompany().equals(Cpany)) current_selection = sha;
                }
                try {
                    quantity_owned.setText( current_selection.getNumber().toString());
                    current_bid.setText(current_selection.getOffer_amount().toString());

                }catch(NullPointerException e){


                }
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
                Integer dollars;
                Trade trade;



                if (quantity.getText()!=null&& price.getText()!=null) {
                    try {
                        num_shares = Integer.parseInt(quantity.getText().toString());
                        dollars =Integer.parseInt(price.getText().toString());
                        if (dollars>investor.getCash()||num_shares>current_selection.getNumber())
                            Toast.makeText(getContext(), "Invalid entry: make sure you have enough cash", Toast.LENGTH_LONG).show();
                    else{
                        String looking_for="";
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference ref_shares;
                        ref_shares= db.getReference("Trades").child(sell).push();
                        trade = new Trade(num_shares, dollars, Cpany);
                        trade.setId(ref_shares.getKey());

                        if(bs.compareTo(sell)==0){
                            current_selection.setStatus(sell);
                            trade.setFor_sale(true);
                            trade.setSeller_id(user_id);
                            trade.setTimeStamp(System.currentTimeMillis());
                            ref_shares.setValue(trade);
                            current_selection.setNumber(current_selection.getNumber()-num_shares);
                            looking_for = buy;
                        }
                        else if (bs.compareTo(buy)==0){
                            trade.setFor_sale(false);
                            trade.setBuyer_id(user_id);
                             FirebaseDatabase.getInstance().getReference(high_bid).child(Cpany).setValue(Integer.parseInt(price.getText().toString()));
                            ref_shares= db.getReference("Trades").child(buy).push();
                            trade.setId(ref_shares.getKey());
                            ref_shares.setValue(trade);
                            Toast.makeText(getActivity(), "Buy stock clicked", Toast.LENGTH_LONG).show();
                            investor.setCash(investor.getCash()-dollars);
                            current_selection.setStatus(buy);
                            looking_for = sell;

                        }
                        current_selection.setNumber_offered(num_shares);
                        current_selection.setOffer_amount(dollars);
                        db.getReference("Shares").child(user_id).child(Cpany).setValue(current_selection);
                        db.getReference().child("Investors").child(user_id).setValue(investor);
                        Trade_Manager trade_manager = new Trade_Manager(trade,looking_for);
                        trade_manager.search_for_trade();
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







}
