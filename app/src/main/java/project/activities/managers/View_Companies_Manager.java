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

import project.objects.adapters.ManAdapter;
import project.objects.database.SessionDatabaseReference;
import project.objects.models.Man_Model;
import project.objects.personel.Manager;
import project.objects.economics.Price;
import project.objects.models.Pricing_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class View_Companies_Manager extends Fragment {
    private Man_Model mm;

    private Pricing_Model pricing_model;

    private ManAdapter manAdapter;
    private ListView listView;
    private TextView yourSymbol;
    private String yourstring;
    private String ID;
    private SessionDatabaseReference SDR;


    private HashMap<String, Price> hm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_view__companies_man,container, false);
        SDR = (SessionDatabaseReference) getActivity().getApplication();
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.setSession_db_ref(SDR.getGlobalVarValue());
        ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mm.update_manager();


        manAdapter = new ManAdapter(getContext(), new ArrayList<Manager>());
        listView = view.findViewById(R.id.list_of_companies);
        listView.setAdapter(manAdapter);
        yourSymbol = view.findViewById(R.id.youare);
        yourstring = yourSymbol.getText().toString();





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



            }
        });




        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.setSession_db_ref(SDR.getGlobalVarValue());
        mm.getMan().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Manager>>() {
            @Override
            public void onChanged(HashMap<String, Manager> stringManagerHashMap) {
                manAdapter.clear();
                manAdapter.addAll(stringManagerHashMap.values());
                Manager manager = new Manager(ID);
                for (Manager m: stringManagerHashMap.values()){
                    if (m.equals(manager)) yourSymbol.setText(yourstring+" "+ m.getCompany_symbol());

                    }
                }
        });


    }


}