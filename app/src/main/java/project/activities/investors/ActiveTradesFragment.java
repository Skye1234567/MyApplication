package project.activities.investors;

import android.content.Intent;
import android.os.Bundle;

import project.objects.database.SessionDatabaseReference;
import project.objects.personel.Investor;
import project.objects.economics.Price;
import project.objects.models.Pricing_Model;
import project.objects.economics.Share;
import project.objects.models.Share_Model;
import project.objects.economics.Trade;
import project.objects.adapters.TradeAdapter;
import project.objects.models.Trade_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ActiveTradesFragment extends Fragment {
    private final static String TAG="active trades page";
    ArrayList<Share> my_shares;
    Share_Model SM;
    Trade_Model TM;
    ArrayList<Trade> buytradeArray;
    ArrayList<Trade> selltradeArray;
    TradeAdapter buytradeAdapter;
    TradeAdapter selltradeAdapter;
    Pricing_Model pricing_model;
    HashMap<String, Price> hm= new HashMap<>();
    ListView listViewbuy;
    ListView listViewsell;
    Investor investor;
    private SessionDatabaseReference SDR;
    @Nullable
    @Override

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_active_trades,container, false);
        SDR = (SessionDatabaseReference) getActivity().getApplication();
        investor = (Investor) getActivity().getIntent().getSerializableExtra("investor");
        TM = new ViewModelProvider(getActivity()).get(Trade_Model.class);
        TM.setSession_db_ref(SDR.getGlobalVarValue());
        SM = new ViewModelProvider(getActivity()).get(Share_Model.class);
        SM.setSession_db_ref(SDR.getGlobalVarValue());
        buytradeArray = new ArrayList<Trade>();
        selltradeArray = new ArrayList<Trade>();
        TM.setId(investor.getID());
        TM.update_trade();
        buytradeAdapter = new TradeAdapter(getContext(), buytradeArray);
        selltradeAdapter = new TradeAdapter(getContext(), selltradeArray);
        listViewbuy = view.findViewById(R.id.tradeslistbuy);
        listViewsell = view.findViewById(R.id.tradeslistsell);
        listViewbuy.setAdapter(buytradeAdapter);
        listViewsell.setAdapter(selltradeAdapter);
        listViewsell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), editorderActivity.class);
                Trade t = (Trade)parent.getItemAtPosition(position);
                Share s = new Share();
                s.setCompany(t.getCompany());
                s = my_shares.get(my_shares.indexOf(s));
                intent.putExtra("ShareNum", s.getNumber());
                intent.putExtra("price", hm.get(t.getCompany()));
                intent.putExtra("trade",t);
                intent.putExtra("investor",investor);
                startActivity(intent);


            }
        });
        listViewbuy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trade t = (Trade)parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), editorderActivity.class);
                Share s = new Share();
                s.setCompany(t.getCompany());
                s = my_shares.get(my_shares.indexOf(s));
                intent.putExtra("ShareNum", s.getNumber());
                intent.putExtra("price", hm.get(t.getCompany()));
                intent.putExtra("trade",t);
                intent.putExtra("investor",investor);
                startActivity(intent);


            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SDR = (SessionDatabaseReference) getActivity().getApplication();
        TM = new ViewModelProvider(getActivity()).get(Trade_Model.class);
        TM.setSession_db_ref(SDR.getGlobalVarValue());
        TM.getTrades().observe(getViewLifecycleOwner(), new Observer<ArrayList<ArrayList<Trade>>>() {
            @Override
            public void onChanged(ArrayList<ArrayList<Trade>> arrayLists) {
                buytradeArray = arrayLists.get(0);
                selltradeArray = arrayLists.get(1);
                update_Adapter();}

        });

    pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model .class);
    pricing_model.setSession_db_ref(SDR.getGlobalVarValue());

        pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
        @Override
        public void onChanged(HashMap<String, Price> stringPriceHashMap) {
            hm = stringPriceHashMap;

        }
    });

    SM = new ViewModelProvider(getActivity()).get(Share_Model.class);
    SM.setSession_db_ref(SDR.getGlobalVarValue());
    SM.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
        @Override
        public void onChanged(ArrayList<Share> shares) {
            my_shares = shares;
        }
    });
    }


    public void update_Adapter(){
        buytradeAdapter.clear();
        buytradeAdapter.addAll(buytradeArray);
        selltradeAdapter.clear();
        selltradeAdapter.addAll(selltradeArray);
    }
}
