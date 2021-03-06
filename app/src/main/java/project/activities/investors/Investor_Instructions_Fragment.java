package project.activities.investors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import project.activities.player.MainActivity;
import project.objects.adapters.ShareAdapter;
import project.objects.database.SessionDatabaseReference;
import project.objects.economics.Price;
import project.objects.economics.Share;
import project.objects.handlers.Value_Assessor;
import project.objects.models.Pricing_Model;
import project.objects.models.Share_Model;
import project.objects.models.Vest_Model;
import project.objects.personel.Investor;

public class Investor_Instructions_Fragment extends Fragment {
    String in_id;
    ShareAdapter shareAdapter;
    Pricing_Model pricing_model;
    Context context;
    ListView tableLayout;
    Vest_Model VM;
    ArrayList<Share> investor_shares;
    TextView cash;
    SessionDatabaseReference SDR;
    Share_Model share_model;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
       View view = inflater.inflate(R.layout.activity_investor__instructions, container, false);

       SDR = (SessionDatabaseReference) getActivity().getApplication();
       share_model = new ViewModelProvider(getActivity()).get(Share_Model.class);
       share_model.setSession_db_ref(SDR.getGlobalVarValue());
       VM = new ViewModelProvider(getActivity()).get(Vest_Model.class);
       VM.setSession_db_ref(SDR.getGlobalVarValue());
       cash = view.findViewById(R.id.cashmoneydata);
       context=getContext();
        if (SDR.getGlobalVarValue()==null){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }



        tableLayout=view.findViewById(R.id.company_shares_table_investor_instructions);
        Investor investor = (Investor) requireActivity().getIntent().getExtras().getSerializable("investor");
        in_id = investor.getID();
        new Thread(new Value_Assessor(in_id, SDR.getGlobalVarValue())).start();


        investor_shares = new ArrayList<>();
        share_model .setId(in_id);
        pricing_model =new ViewModelProvider(getActivity()).get(Pricing_Model.class);
        pricing_model.setSession_db_ref(SDR.getGlobalVarValue());
        shareAdapter =new ShareAdapter(context,investor_shares);
        tableLayout.setAdapter(shareAdapter);
        if (in_id ==null){
            Toast.makeText(context, "HELP NO ID", Toast.LENGTH_LONG).show();
        }





        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SDR = (SessionDatabaseReference) getActivity().getApplication();


        pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model.class);
        pricing_model.setSession_db_ref(SDR.getGlobalVarValue());
         pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
            @Override
            public void onChanged(HashMap<String, Price> stringPriceHashMap) {
                DatabaseReference ref;
                if (in_id!=null&&stringPriceHashMap!=null ){
                    SessionDatabaseReference SDR  = (SessionDatabaseReference) getActivity().getApplication();
                    ref = SDR.getGlobalVarValue().child("Shares").child(in_id);
                for (String comp:stringPriceHashMap.keySet()){
                    ref.child(comp).child("market_price").setValue(stringPriceHashMap.get(comp).getPrice());


                }
                }


            }
        });
        share_model = new ViewModelProvider(getActivity()).get(Share_Model.class);
        share_model.setSession_db_ref(SDR.getGlobalVarValue());
        share_model.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                investor_shares = shares;
                update_ShareAdapter();
            }
        });
        VM = new ViewModelProvider(getActivity()).get(Vest_Model.class);
        VM.setSession_db_ref(SDR.getGlobalVarValue());
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
