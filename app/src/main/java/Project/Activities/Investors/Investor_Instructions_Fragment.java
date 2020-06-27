package Project.Activities.Investors;

import Project.Objects.Personel.Investor;
import Project.Objects.Models.Share_Model;
import Project.Objects.Economics.Share;
import Project.Objects.Adapters.ShareAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import Project.Objects.Models.Pricing_Model;
import Project.Objects.Economics.Price;
import Project.Objects.Handlers.Value_Assessor;
import Project.Objects.Models.Vest_Model;

public class Investor_Instructions_Fragment extends Fragment {
    String in_id;
    ShareAdapter shareAdapter;
    Pricing_Model pricing_model;
    Context context;
    ListView tableLayout;
    Vest_Model VM;
    ArrayList<Share> investor_shares;
    TextView cash;


    Share_Model share_model;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
       View view = inflater.inflate(R.layout.activity_investor__instructions, container, false);
       context = getContext();
        share_model = new ViewModelProvider(getActivity()).get(Share_Model.class);

       VM = new ViewModelProvider(getActivity()).get(Vest_Model.class);
       cash = view.findViewById(R.id.cashmoneydata);


        tableLayout=view.findViewById(R.id.company_shares_table_investor_instructions);
        Investor investor = (Investor) Objects.requireNonNull(getActivity().getIntent().getExtras()).getSerializable("investor");
        in_id = investor.getID();
        new Thread(new Value_Assessor(in_id)).start();


        investor_shares = new ArrayList<>();
        share_model .setId(in_id);
        pricing_model =new ViewModelProvider(getActivity()).get(Pricing_Model.class);
        pricing_model.setCurrent_user_id(in_id);
        shareAdapter =new ShareAdapter(context,investor_shares);
        tableLayout.setAdapter(shareAdapter);
        if (in_id ==null){
            Toast.makeText(context, "HELP NO ID", Toast.LENGTH_LONG).show();
        }





        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model.class);
         pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
            @Override
            public void onChanged(HashMap<String, Price> stringPriceHashMap) {
                DatabaseReference ref;
                if (in_id!=null&&stringPriceHashMap!=null ){
                ref = FirebaseDatabase.getInstance().getReference("Shares").child(in_id);
                for (String comp:stringPriceHashMap.keySet()){
                    ref.child(comp).child("market_price").setValue(stringPriceHashMap.get(comp).getPrice());


                }
                }


            }
        });
        share_model = new ViewModelProvider(getActivity()).get(Share_Model.class);
        share_model.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                investor_shares = shares;
                update_ShareAdapter();
            }
        });
        VM = new ViewModelProvider(getActivity()).get(Vest_Model.class);
        VM.getMan().observe(getViewLifecycleOwner(), new Observer<Investor>() {
            @Override
            public void onChanged(Investor investor) {
                if (investor!=null)
                update_cash(investor.getCash().toString());

            }
        });

    }
    public void update_ShareAdapter(){
        shareAdapter.clear();

        for (Share s : investor_shares){
            shareAdapter.add(s);

        }


    }
public void update_cash(String s){
        cash.setText(s);
}



}
