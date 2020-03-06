package com.example.myapplication;

import android.os.Bundle;

import Objects.Share;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class OrderStockFragment extends Fragment{
    private final static  String TAG = "Order Stock Fragment";
    private Spinner spinner ;
    private Spinner spinner2 ;
    private Button order_stock;
    private EditText quantity;
    private EditText price;
    private TextView current_bid;
    private String user_id;
    private String high_bid;
    private String low_price;
    private HashMap<String, Share> symbol_to_share;
    private ArrayList Shares_For_Sale;
    private Share my_share;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_stock,container, false);
        user_id = getActivity().getIntent().getStringExtra("user_id");

        spinner = view.findViewById(R.id.spinner_buy_sell);
        spinner2 = view.findViewById(R.id.spinner_stocks);
        final ArrayList<String> list1 = new ArrayList();
        final ArrayList<String> list2 = new ArrayList();
        list2.add("Buy");
        list2.add("Sell");
        quantity = view.findViewById(R.id.enter_number_of_stocks);
        price = view.findViewById(R.id.money_sign);
        Query shares = FirebaseDatabase.getInstance().getReference("Shares_For_Sale").child(user_id);
        Query q = FirebaseDatabase.getInstance().getReference().child("Managers");


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d :dataSnapshot.getChildren()){
                    Integer index = d.getKey().length();
                   char[] characters = d.getKey().toCharArray();


                   String symbol = Arrays.copyOfRange(characters, index-5, index-1).toString();
                   list1.add(symbol);

                }
                update_Company_Symbols(list1, list2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "trouble getting the managers from database");

            }
        });
        shares.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                 symbol_to_share= dataSnapshot.getValue(HashMap.class);
                 //do callback

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                symbol_to_share= dataSnapshot.getValue(HashMap.class);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Shares_For_Sale = new ArrayList();
                FirebaseDatabase dbspinner=FirebaseDatabase.getInstance();
                String s = parent.getItemAtPosition(position).toString();
                Query q = dbspinner.getReference("Companies").child(s).child("high_bid");
                Query q2 = dbspinner.getReference("Companies").child(s).child("for_sale").orderByChild("Asking_price").limitToFirst(5);
                Query q3 =dbspinner.getReference("Shares").child(user_id).child(s);
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        my_share = dataSnapshot.getValue(Share.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());

                    }
                });
                q.addValueEventListener(new ValueEventListener() {
                     @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        high_bid = dataSnapshot.getValue(String.class);
                        if (high_bid!=null)
                        {current_bid.setText(high_bid);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());

                    }
                });

                q2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for (DataSnapshot d :dataSnapshot.getChildren()){
                            Shares_For_Sale.add(d.getValue(Share.class));


                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        order_stock = view.findViewById(R.id.place_order_button);
        order_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity.getText().toString().compareTo(null)==1 && price.getText().toString().compareTo(null)==1) {
                    try {
                        Integer num_stock = Integer.getInteger(quantity.toString());
                        Float dollars = Float.parseFloat(price.toString());
                        String bs = spinner.getSelectedItem().toString();
                        String Company = spinner2.getSelectedItem().toString();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference ref_shares;
                        ref_shares = db.getReference("Shares").child(user_id);
                        if(bs.compareTo("Sell")==0){

                            my_share.setAsking_price(dollars);
                            if (my_share.getNumber()>=num_stock){

                                my_share.setNumber_for_sale(num_stock);
                                ref_shares.setValue(my_share);
                                db.getReference("Companies").child(my_share.getCompany()).child("for_sale").push().setValue(my_share);
                            }
                            else {
                                Toast.makeText(getActivity(), "You Cannot Sell More Shares Than You Have!", Toast.LENGTH_LONG).show();}


                        }
                        else if (bs.compareTo("Buy")==0){
                            Toast.makeText(getActivity(), "Buy stock clicked", Toast.LENGTH_LONG).show();
                        }

                    }
                    catch (Exception e){
                        Log.d(TAG, e.getMessage());
                    }
                }

            }
        });


    }
}
