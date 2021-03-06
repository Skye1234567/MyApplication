 package project.activities.investors;

 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.example.myapplication.R;
 import com.google.firebase.database.DatabaseReference;

 import java.util.Observable;
 import java.util.Observer;

 import androidx.appcompat.app.AppCompatActivity;
 import project.activities.player.MainActivity;
 import project.objects.database.InvestorDatabase;
 import project.objects.database.PriceDatabase;
 import project.objects.database.SessionDatabaseReference;
 import project.objects.database.ShareDatabase;
 import project.objects.economics.Price;
 import project.objects.economics.Share;
 import project.objects.economics.Trade;
 import project.objects.handlers.Ledger;
 import project.objects.handlers.Trade_Manager;
 import project.objects.personel.Investor;

 public class orderstockActivity extends AppCompatActivity {
     private final static  String TAG = "Order Stock Fragment";
     private Spinner spinner ;
     private Share current_selection;
     private Button order_stock;
     private EditText quantity;
     private EditText price;
     private TextView market_price;

     private TextView quantity_owned;
     private String user_id;
     private String bs="Buy";
     private String Cpany;
     private String buy = "Buy";
     private String sell = "Sell";
     private Context context;
     private Investor investor;
     private Price p;
     private SessionDatabaseReference SDR;
     private PriceDatabase PD;
     private InvestorDatabase investorDatabase;
     private ShareDatabase ShD;
     private TextView low_ask;
     private TextView high_bid;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
         //all you need to pass in the intent is the company symbol and the investor id
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activityorderstock);
        SDR =(SessionDatabaseReference) getApplication();

         if (SDR.getGlobalVarValue()==null){
             Intent intent = new Intent(context, MainActivity.class);
             startActivity(intent);
         }
        Intent intent1 = getIntent();
         Cpany= intent1.getStringExtra("symbol");
         investor = (Investor) intent1.getSerializableExtra("user");
         current_selection = (Share) intent1.getSerializableExtra("share");
         user_id = investor.getID();
         market_price =findViewById(R.id.current_bid);
         spinner = findViewById(R.id.spinner_buy_sell);
         low_ask = findViewById(R.id.low_bid);
         high_bid = findViewById(R.id.high_bid);


         ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item);
         arrayAdapter.add("Buy");
         arrayAdapter.add("Sell");
         spinner.setAdapter(arrayAdapter);
         quantity_owned= findViewById(R.id.quantity_owned);
         quantity = findViewById(R.id.enter_number_of_stocks);
         price =findViewById(R.id.money_sign);
         p = (Price)intent1.getSerializableExtra("price");
         PD = new PriceDatabase(SDR.getGlobalVarValue().child("Prices").child(Cpany));
         ShD = new ShareDatabase(SDR.getGlobalVarValue().child("Shares").child(investor.getID()).child(Cpany));
         investorDatabase = new InvestorDatabase(SDR.getGlobalVarValue().child("Investors").child(investor.getID()));
         PD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                p=(Price) arg;
                market_price.setText(p.getPrice().toString());
                if (p.getHigh_bid()!=null)
                high_bid.setText(p.getHigh_bid().toString());
                if (p.getLow_ask()!=null);
                low_ask.setText(p.getLow_ask().toString());
            }
        });
        PD.updating();
        ShD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                current_selection = (Share) arg;
                if (current_selection.getNumber()!=null)
                quantity_owned.setText(current_selection.getNumber().toString());



            }
        });
        ShD.updating();
        investorDatabase.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                investor = (Investor) arg;
            }
        });


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
                     //try {
                         num_shares = Integer.parseInt(quantity.getText().toString());
                         dollars =Integer.parseInt(price.getText().toString());
                         String looking_for="";


                        DatabaseReference db = SDR.getGlobalVarValue();

                         DatabaseReference ref_shares = db.child("Trades").child(sell).push();
                         trade = new Trade(num_shares, dollars, Cpany);
                         trade.setId(ref_shares.getKey());
                         trade.setTimeStamp(System.currentTimeMillis());

                         if(bs.compareTo(sell)==0){

                             if (num_shares>current_selection.getNumber())
                                 Toast.makeText(context, "Invalid entry: make sure you have enough shares", Toast.LENGTH_LONG).show();
                             else{
                                 p.add_ask(user_id,dollars);
                                 current_selection.setStatus(sell);
                                 trade.setFor_sale(true);
                                 trade.setSeller_id(user_id);
                                 trade.setTimeStamp(System.currentTimeMillis());
                                 ref_shares.setValue(trade);
                                 current_selection.setNumber(current_selection.getNumber()-num_shares);
                                 looking_for = buy;}
                         }
                         else if (bs.compareTo(buy)==0){
                             if (investor.getCash()>=(dollars*num_shares)){
                                 Integer set_cash =investor.getCash() - dollars*num_shares;

                                 p.add_bid(user_id,dollars);
                                 trade.setFor_sale(false);
                                 trade.setBuyer_id(user_id);
                                 ref_shares = db.child("Trades").child(buy).push();
                                 trade.setId(ref_shares.getKey());
                                 ref_shares.setValue(trade);
                                 Toast.makeText(context, "Buy stock clicked", Toast.LENGTH_LONG).show();
                                 investor.setCash(set_cash);
                                 current_selection.setStatus(buy);
                                 looking_for = sell;
                             }   else
                                 Toast.makeText(context, "Invalid entry: make sure you have enough cash", Toast.LENGTH_LONG).show();


                         }

                         db.child("Shares").child(user_id).child(Cpany).setValue(current_selection);
                         db.child("Investors").child(user_id).setValue(investor);
                         db.child("Prices").child(Cpany).setValue(p);
                         Trade_Manager trade_manager = new Trade_Manager(trade,looking_for, p.getPrice(), SDR.getGlobalVarValue());
                         trade_manager.search_for_trade();



/*
                     }
                     catch (Exception e){
                         Log.d(TAG, e.getMessage());
                         Toast.makeText(context, "try statement failed", Toast.LENGTH_LONG).show();
                     }*/
                     //Toast.makeText(context, "Order Placed", Toast.LENGTH_LONG).show();
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









