 package Project.Activities.Investors;

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
 import com.google.firebase.database.FirebaseDatabase;

 import Project.Objects.Database.SessionDatabaseReference;
 import Project.Objects.Personel.Investor;
 import Project.Objects.Economics.Price;
 import Project.Objects.Economics.Trade;
 import Project.Objects.Handlers.Trade_Manager;
 import androidx.appcompat.app.AppCompatActivity;

 public class editorderActivity extends AppCompatActivity {
     private final static  String TAG = "Order Stock Fragment";
     private Trade current_trade;

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

         investor = (Investor) intent1.getSerializableExtra("investor");
         current_trade = (Trade) intent1.getSerializableExtra("trade");

          p = (Price)intent1.getSerializableExtra("price");
         user_id = investor.getID();
         current_bid =findViewById(R.id.current_bid);
         quantity_owned= findViewById(R.id.quantity_owned);
         quantity = findViewById(R.id.enter_number_of_stocks);
         price =findViewById(R.id.money_sign);
         quantity.setText(current_trade.getNum_shares().toString());
         price.setText(current_trade.getPrice_point().toString());
         Cpany= current_trade.getCompany();
         if (current_trade.getBuyer_id()!=null){
             if (current_trade.getBuyer_id().compareTo(user_id)==0) bs =  buy;}else bs=sell;



         try{
         quantity_owned.setText(current_trade.getNum_shares().toString());
         current_bid.setText(p.getPrice().toString());}
         catch (NullPointerException e){}
         deleter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SessionDatabaseReference SDR  = (SessionDatabaseReference) getApplicationContext();


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
                         SessionDatabaseReference SDR  = (SessionDatabaseReference) getApplicationContext();


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



                         current_bid.setText(p.getPrice().toString());
                         db.child("Investors").child(user_id).setValue(investor);
                         db.child("Trades").child(bs).child(current_trade.getId()).setValue(current_trade);

                         Trade_Manager trade_manager = new Trade_Manager(current_trade,looking_for, p.getPrice());
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









