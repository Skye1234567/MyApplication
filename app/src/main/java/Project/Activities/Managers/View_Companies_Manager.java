package Project.Activities.Managers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

import Project.Objects.Adapters.ManAdapter;
import Project.Objects.Models.Man_Model;
import Project.Objects.Personel.Manager;
import Project.Objects.Economics.Price;
import Project.Objects.Models.Pricing_Model;
import Project.Objects.Models.Share_Model;
import Project.Objects.Models.Trade_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class View_Companies_Manager extends Fragment {
    private Man_Model mm;
    private Share_Model sm;
    private Trade_Model tm;
    private Pricing_Model pricing_model;
    private ArrayList<Manager> managersArray;
    private ManAdapter manAdapter;
    private ListView listView;


    private HashMap<String, Price> hm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_view__companies,container, false);
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        managersArray = new ArrayList<Manager>();



        mm.update_manager();
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);

        tm = new ViewModelProvider(getActivity()).get(Trade_Model.class);

        manAdapter = new ManAdapter(getContext(), managersArray);
        listView = view.findViewById(R.id.list_of_companies);
        listView.setAdapter(manAdapter);




        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model.class);
        pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
            @Override
            public void onChanged(HashMap<String, Price> stringPriceHashMap) {
                hm = stringPriceHashMap;



            }
        });




        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.getMan().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Manager>>() {
            @Override
            public void onChanged(HashMap<String, Manager> stringManagerHashMap) {
                manAdapter.clear();
                manAdapter.addAll(stringManagerHashMap.values());


            }
        });


    }


}