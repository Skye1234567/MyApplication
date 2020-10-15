package project.activities.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import project.objects.adapters.PriceAdapter;
import project.objects.database.SessionDatabaseReference;
import project.objects.economics.Price;
import project.objects.models.Pricing_Model;


public class Market_Prices_Admin extends Fragment {


    private PriceAdapter priceAdapter;

    private HashMap<String, Price> hm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_prices_admin,container, false);
        hm = new HashMap<>();
        ListView listView;


        if (getContext()!=null){
        priceAdapter = new PriceAdapter(getContext(), new ArrayList<Map.Entry<String, Price>>());}
        listView = view.findViewById(R.id.list_of_prices);
        listView.setAdapter(priceAdapter);




        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Pricing_Model pricing_model;
        SessionDatabaseReference SDR = (SessionDatabaseReference) getActivity().getApplication();

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



    }


}