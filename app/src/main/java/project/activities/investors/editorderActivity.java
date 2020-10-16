 package project.activities.investors;

 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
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
 import project.objects.economics.Price;
 import project.objects.economics.Trade;
 import project.objects.handlers.Trade_Manager;
 import project.objects.personel.Investor;

 public class editorderActivity extends AppCompatActivity {
     private final static  String TAG = "Order Stock Fragment";
     private Trade current_trade;

     private Button edit_stock;
     private EditText quantity;
     private EditText price;
     private TextView market_price;
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
     private SessionDatabaseReference SDR;
     private PriceDatabase PD;
     private InvestorDatabase investorDatabase;

     private TextView low_ask;
     private TextView high_bid;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activityeditorder);
        SDR= (SessionDatabaseReference) getApplication();

         if (SDR.getGlobalVarValue()==null){
             Intent intent = new Intent(context, MainActivity.class);
             startActivity(intent);
         }

         Intent intent1 = getIntent();
        deleter = findViewById(R.id.delete_order_button);
         num_trade_shares = intent1.getIntExtra("ShareNum", 0);

         investor = (Investor) intent1.getSerializableExtra("investor");
         current_trade = (Trade) intent1.getSerializableExtra("trade");

          p = (Price)intent1.getSerializableExtra("price");
         user_id = investor.getID();
         market_price =findViewById(R.id.current_bid);
         quantity_owned= findViewById(R.id.quantity_owned);
         quantity = findViewById(R.id.enter_number_of_stocks);
         high_bid = findViewById(R.id.high_bid);
         low_ask = findViewById(R.id.low_bid);
         price =findViewById(R.id.money_sign);
         quantity.setText(current_trade.getNum_shares().toString());
         price.setText(current_trade.getPrice_point().toString());
         Cpany= current_trade.getCompany();
         if (current_trade.getBuyer_id()!=null){
             if (current_trade.getBuyer_id().compareTo(user_id)==0) bs =  buy;}else bs=sell;



         PD = new PriceDatabase(SDR.getGlobalVarValue().child("Prices").child(Cpany));
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

         investorDatabase.addObserver(new Observer() {
             @Override
             public void update(Observable o, Object arg) {
                 investor = (Investor) arg;
             }
         });


         deleter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SessionDatabaseReference SDR  = (SessionDatabaseReference) getApplication();


                 DatabaseReference db = SDR.getGlobalVarValue();

                if (bs.compareTo(buy)==0) {

                    Integer new_cash = investor.getCash()+ current_trade.getPrice_point()* current_trade.getNum_shares();
                   investor.setCash(new_cash);

                    db.child("Prices").child(Cpany).child("bids").child(user_id).setValue(null);

                }else {
                    db.child("Prices").child(Cpany).child("asks").child(user_id).setValue(null);

                    db.child("Shares").child(investor.getID()).child(current_trade.getCompany()).child("number").setValue(num_trade_shares+ current_trade.getNum_shares());

                }

                 db.child("Shares").child(user_id).child(Cpany).child("status").setValue(null);
                 db.child("Trades").child(bs).child(current_trade.getId()).setValue(null);
                 db.child("Investors").child(user_id).setValue(investor);

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
                         Integer tot_dol = num_shares*dollars;


                         String looking_for="";
                         SessionDatabaseReference SDR  = (SessionDatabaseReference) getApplication();


                         DatabaseReference db = SDR.getGlobalVarValue();


                         Integer old_num_share =num_trade_shares+current_trade.getNum_shares();
                         Integer tots_cash = investor.getCash()+current_trade.getPrice_point()*current_trade.getNum_shares();


                         switch (bs.compareTo(sell)){


                           case 0:
                             if (num_shares> old_num_share)
                                 Toast.makeText(context, "Invalid entry: make sure you have enough shares", Toast.LENGTH_LONG).show();
                             else{
                                p.add_ask(user_id,dollars);
                                 db.child("Prices").child(Cpany).child("asks").child(user_id).setValue(dollars);
                                 current_trade.setNum_shares(num_shares);
                                 current_trade.setPrice_point(dollars);
                                 current_trade.setTimeStamp(System.currentTimeMillis());


                                 SDR.getGlobalVarValue().child("Shares")
                                        .child(current_trade.getSeller_id()).child(current_trade.getCompany())
                                        .child("number").setValue(old_num_share-num_shares);
                                 looking_for = buy;}
                             break;

                         case 1:

                             if (tot_dol >tots_cash) {
                                 Toast.makeText(context, "Invalid entry: make sure you have enough cash", Toast.LENGTH_LONG).show();
                             }
                             else {
                                 p.add_bid(user_id,dollars);
                                 db.child("Prices").child(Cpany).child("bids").child(user_id).setValue(dollars);
                                 current_trade.setPrice_point(dollars);
                                 current_trade.setNum_shares(num_shares);
                                 Toast.makeText(context, "Buy stock clicked", Toast.LENGTH_LONG).show();
                                 investor.setCash(tots_cash-tot_dol);
                                 looking_for = sell; }
                             }



                         market_price.setText(p.getPrice().toString());
                         db.child("Investors").child(user_id).setValue(investor);
                         db.child("Trades").child(bs).child(current_trade.getId()).setValue(current_trade);

                         Trade_Manager trade_manager = new Trade_Manager(current_trade,looking_for, p.getPrice(), SDR.getGlobalVarValue());
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









