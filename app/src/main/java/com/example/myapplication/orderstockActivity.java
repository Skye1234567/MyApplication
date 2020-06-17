 package com.example.myapplication;

import Objects.Investor;
import Objects.Man_Model;
import Objects.Price;
import Objects.Pricing_Model;
import Objects.Share;
import Objects.Share_Model;
import Objects.Trade;
import Objects.Trade_Manager;
import Objects.Vest_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.HashMap;

 public class orderstockActivity extends AppCompatActivity {
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

     Integer s_val=0;
     private String bs;
     private String Cpany;
     private String buy = "Buy";
     private String sell = "Sell";
     private Context context;
     private Investor investor;
     private Price p;




     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activityorderstock);
        Intent intent1 = getIntent();
        Cpany= intent1.getStringExtra("symbol");
         investor = (Investor) intent1.getSerializableExtra("user");
         current_selection = (Share) intent1.getSerializableExtra("share");
          p = (Price)intent1.getSerializableExtra("price");
         user_id = investor.getID();
         current_bid =findViewById(R.id.current_bid);
         spinner = findViewById(R.id.spinner_buy_sell);
         ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item);
         arrayAdapter.add("Buy");
         arrayAdapter.add("Sell");
         spinner.setAdapter(arrayAdapter);
         quantity_owned= findViewById(R.id.quantity_owned);
         quantity = findViewById(R.id.enter_number_of_stocks);
         price =findViewById(R.id.money_sign);
         try{
         quantity_owned.setText(current_selection.getNumber().toString());
         current_bid.setText(current_selection.getMarket_price().toString());}
         catch (NullPointerException e){}
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 bs =parent.getItemAtPosition(position).toString();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {


             }
         });







         order_stock = findViewById(R.id.place_order_button);
         order_stock.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Integer num_shares;
                 Integer dollars;
                 Trade trade;
                 if (p==null) p= new Price(0,0);




                 if (quantity.getText()!=null&& price.getText()!=null) {
                     try {
                         num_shares = Integer.parseInt(quantity.getText().toString());
                         dollars =Integer.parseInt(price.getText().toString());


                         String looking_for="";
                         FirebaseDatabase db = FirebaseDatabase.getInstance();
                         DatabaseReference ref_shares = db.getReference("Trades").child(sell).push();
                         trade = new Trade(num_shares, dollars, Cpany);
                         trade.setId(ref_shares.getKey());
                         trade.setTimeStamp(System.currentTimeMillis());

                         if(bs.compareTo(sell)==0){
                             if (num_shares>current_selection.getNumber())
                                 Toast.makeText(context, "Invalid entry: make sure you have enough shares", Toast.LENGTH_LONG).show();
                             else{
                                 p.challenge_ask(dollars);
                                 current_selection.setStatus(sell);
                                 trade.setFor_sale(true);
                                 trade.setSeller_id(user_id);
                                 trade.setTimeStamp(System.currentTimeMillis());
                                 ref_shares.setValue(trade);
                                 current_selection.setNumber(current_selection.getNumber()-num_shares);
                                 looking_for = buy;}
                         }
                         else if (bs.compareTo(buy)==0){
                             if (dollars*num_shares >investor.getCash())
                                 Toast.makeText(context, "Invalid entry: make sure you have enough cash", Toast.LENGTH_LONG).show();
                             else {
                                 p.challenge_bid(dollars);
                                 trade.setFor_sale(false);
                                 trade.setBuyer_id(user_id);
                                 ref_shares = db.getReference("Trades").child(buy).push();
                                 trade.setId(ref_shares.getKey());
                                 ref_shares.setValue(trade);
                                 Toast.makeText(context, "Buy stock clicked", Toast.LENGTH_LONG).show();
                                 investor.setCash(investor.getCash() - dollars*num_shares);
                                 current_selection.setStatus(buy);
                                 looking_for = sell;
                             }

                         }
                         current_bid.setText(current_selection.getMarket_price().toString());
                         current_selection.setNumber_offered(num_shares);
                         current_selection.setOffer_amount(dollars);
                         db.getReference("Shares").child(user_id).child(Cpany).setValue(current_selection);
                         db.getReference().child("Investors").child(user_id).setValue(investor);
                         db.getReference("Prices").child(Cpany).setValue(p);
                         Trade_Manager trade_manager = new Trade_Manager(trade,looking_for, p.getPrice());
                         trade_manager.search_for_trade();




                     }
                     catch (Exception e){
                         Log.d(TAG, e.getMessage());
                     }
                     Toast.makeText(context, "Order Placed", Toast.LENGTH_LONG).show();
                     price.setText("");
                     quantity.setText("");
                 }
                 Intent intent = new Intent(context, MarketPlace.class);
                 intent.putExtra("investor", investor);
                 startActivity(intent);
                 finish();

             }
         });

     }
 }









