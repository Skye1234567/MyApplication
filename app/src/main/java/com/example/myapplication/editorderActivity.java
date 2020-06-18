 package com.example.myapplication;

 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;

 import Objects.Investor;
 import Objects.Price;
 import Objects.Trade;
 import Objects.Trade_Manager;
 import androidx.appcompat.app.AppCompatActivity;

 public class editorderActivity extends AppCompatActivity {
     private final static  String TAG = "Order Stock Fragment";
     private Trade current_selection;
     private Button edit_stock;
     private EditText quantity;
     private EditText price;
     private TextView current_bid;
     private TextView quantity_owned;
     private String user_id;

     private String buy = "Buy";
     private String sell = "Sell";
     private Context context;
     private Investor investor;
     private Price p;
     private Button deleter;
     private String bs;
     private Integer num_trade_shares;
     private String Cpany;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activityeditorder);
        Intent intent1 = getIntent();
        deleter = findViewById(R.id.delete_order_button);
        num_trade_shares = intent1.getIntExtra("ShareNum", 0);
        bs =  intent1.getStringExtra("bos");
         investor = (Investor) intent1.getSerializableExtra("investor");
         current_selection = (Trade) intent1.getSerializableExtra("trade");
          p = (Price)intent1.getSerializableExtra("price");
         user_id = investor.getID();
         current_bid =findViewById(R.id.current_bid);
         quantity_owned= findViewById(R.id.quantity_owned);
         quantity = findViewById(R.id.enter_number_of_stocks);
         price =findViewById(R.id.money_sign);
         quantity.setText(current_selection.getNum_shares());
         price.setText(current_selection.getPrice_point());
         Cpany= current_selection.getCompany();





         try{
         quantity_owned.setText(current_selection.getNum_shares().toString());
         current_bid.setText(p.getPrice().toString());}
         catch (NullPointerException e){}
         deleter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 FirebaseDatabase db = FirebaseDatabase.getInstance();
                if (bs==buy) {
                    p.add_bid(user_id, null);
                    investor.setCash(investor.getCash()+current_selection.getPrice_point());

                }else {
                    p.add_ask(user_id, null);
                    db.getReference("Shares").child(investor.getID()).child(current_selection.getCompany()).child("number").setValue(num_trade_shares+current_selection.getNum_shares());

                }

                 db.getReference("Trades").child(bs).child(current_selection.getId()).setValue(null);
                 db.getReference().child("Investors").child(user_id).setValue(investor);
                 db.getReference("Prices").child(Cpany).setValue(p);
                 Intent intent = new Intent(context, MarketPlace.class);
                 intent.putExtra("investor", investor);
                 startActivity(intent);
                 finish();

             }
         });
         edit_stock = findViewById(R.id.edit_order_button);
         edit_stock.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Integer num_shares;
                 Integer dollars;
                 if (p==null) p= new Price(0,0);




                 if (quantity.getText()!=null&& price.getText()!=null) {
                     try {
                         num_shares = Integer.parseInt(quantity.getText().toString());
                         dollars =Integer.parseInt(price.getText().toString());


                         String looking_for="";
                         FirebaseDatabase db = FirebaseDatabase.getInstance();
                         DatabaseReference ref_shares = db.getReference("Trades").child(sell).child(current_selection.getId());

                         current_selection.setTimeStamp(System.currentTimeMillis());

                       switch (bs.compareTo(sell)){
                           case 0:
                             if (num_shares>current_selection.getNum_shares())
                                 Toast.makeText(context, "Invalid entry: make sure you have enough shares", Toast.LENGTH_LONG).show();
                             else{
                                 p.add_ask(user_id,dollars);
                                 current_selection.setFor_sale(true);
                                 current_selection.setSeller_id(user_id);
                                 current_selection.setTimeStamp(System.currentTimeMillis());
                                 ref_shares.setValue(current_selection);
                                 current_selection.setNum_shares(current_selection.getNum_shares()-num_shares);
                                 looking_for = buy;}
                             break;

                         case 1:
                             if (dollars*num_shares >investor.getCash())
                                 Toast.makeText(context, "Invalid entry: make sure you have enough cash", Toast.LENGTH_LONG).show();
                             else {
                                 p.add_bid(user_id,dollars);
                                 current_selection.setFor_sale(false);
                                 current_selection.setBuyer_id(user_id);
                                 ref_shares = db.getReference("Trades").child(buy).child(current_selection.getId());
                                 ref_shares.setValue(current_selection);
                                 Toast.makeText(context, "Buy stock clicked", Toast.LENGTH_LONG).show();
                                 investor.setCash(investor.getCash() - dollars*num_shares);
                                 looking_for = sell; }
                             break; }
                         current_bid.setText(p.getPrice().toString());

                         db.getReference("Shares").child(user_id).child(Cpany).setValue(current_selection);
                         db.getReference().child("Investors").child(user_id).setValue(investor);
                         db.getReference("Prices").child(Cpany).setValue(p);
                         Trade_Manager trade_manager = new Trade_Manager(current_selection,looking_for, p.getPrice());
                         trade_manager.search_for_trade(); }
                     catch (Exception e){
                         Log.d(TAG, e.getMessage()); }
                     Toast.makeText(context, "Trade Updated", Toast.LENGTH_LONG).show();
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









