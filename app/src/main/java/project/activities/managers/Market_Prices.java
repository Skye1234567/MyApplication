package project.activities.managers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.objects.database.SessionDatabaseReference;
import project.objects.models.One_Man_Model;
import project.objects.personel.Manager;
import project.objects.economics.Price;
import project.objects.adapters.PriceAdapter;
import project.objects.models.Pricing_Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class Market_Prices extends Fragment {

    private Pricing_Model pricing_model;
    private One_Man_Model one_man_model;
    private PriceAdapter priceAdapter;
    private ListView listView;
    private TextView youare;
    private String yourstring;
    private HashMap<String, Price> hm;
    private SessionDatabaseReference SDR;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_prices,container, false);
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        hm = new HashMap<>();
        one_man_model=new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        one_man_model.set_id(id);
        priceAdapter = new PriceAdapter(getContext(), new ArrayList<Map.Entry<String, Price>>());
        listView = view.findViewById(R.id.list_of_prices);
        listView.setAdapter(priceAdapter);
        youare =view.findViewById(R.id.youare);
        yourstring = youare.getText().toString();




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
                hm = stringPriceHashMap;
                priceAdapter.clear();
                priceAdapter.addAll(hm.entrySet());


            }
        });
        one_man_model =new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        one_man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                youare.setText(yourstring+manager.getCompany_symbol());

            }
        });








    }


}