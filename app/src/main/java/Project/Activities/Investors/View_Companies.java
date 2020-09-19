package Project.Activities.Investors;

import Project.Objects.Database.SessionDatabaseReference;
import Project.Objects.Personel.Investor;
import Project.Objects.Adapters.ManAdapter;
import Project.Objects.Models.Man_Model;
import Project.Objects.Personel.Manager;
import Project.Objects.Economics.Price;
import Project.Objects.Models.Pricing_Model;
import Project.Objects.Economics.Share;
import Project.Objects.Models.Share_Model;
import Project.Objects.Economics.Trade;
import Project.Objects.Models.Trade_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class View_Companies extends Fragment {
    private Man_Model mm;
    private Share_Model sm;
    private Trade_Model tm;
    private Pricing_Model pricing_model;
    private ArrayList<Manager> managersArray;
    private ManAdapter manAdapter;
    private ListView listView;
    private Investor investor;
    private ArrayList<Share> my_shares;
    private HashMap<String, Price> hm;
    private Integer number_shares;
   private String company;
   private SessionDatabaseReference SDR;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_view__companies,container, false);
        SDR = (SessionDatabaseReference) getContext().getApplicationContext();
        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.setSession_db_ref(SDR.getGlobalVarValue());
        managersArray = new ArrayList<Manager>();
        investor = (Investor) getActivity().getIntent().getSerializableExtra("investor");



        mm.update_manager();
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        sm.setSession_db_ref(SDR.getGlobalVarValue());

        tm = new ViewModelProvider(getActivity()).get(Trade_Model.class);
        tm.setSession_db_ref(SDR.getGlobalVarValue());

        manAdapter = new ManAdapter(getContext(), managersArray);
        listView = view.findViewById(R.id.list_of_companies);
        listView.setAdapter(manAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                 Manager manager = (Manager) parent.getItemAtPosition(position);
                 String symbol = manager.getCompany_symbol();
                 Trade t = tm.filter_for_company(symbol);
                Share s = new Share();

                s.setCompany(symbol);
                s=my_shares.get(my_shares.indexOf(s));
                Price p;
                if (hm ==null) p=new Price();


                else p= hm.get(symbol);

                if (t!=null){
                    Intent intent = new Intent(getActivity(), editorderActivity.class);
                    intent.putExtra("ShareNum", s.getNumber());
                intent.putExtra("price", p);
                intent.putExtra("trade",t);
                intent.putExtra("investor",investor);

                startActivity(intent);}




                 else{
                 Intent intent  = new Intent(getActivity(), orderstockActivity.class);
                 intent.putExtra("user", investor);
                 intent.putExtra("symbol", symbol);
                 intent.putExtra("share", s);
                 intent.putExtra("price", p);
                 startActivity(intent);
                 }


            }
        });



        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SDR = (SessionDatabaseReference) getContext().getApplicationContext();
        sm = new ViewModelProvider(getActivity()).get(Share_Model.class);
        sm.setSession_db_ref(SDR.getGlobalVarValue());
        pricing_model = new ViewModelProvider(getActivity()).get(Pricing_Model.class);
        pricing_model.setSession_db_ref(SDR.getGlobalVarValue());
        pricing_model.getPrices().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Price>>() {
            @Override
            public void onChanged(HashMap<String, Price> stringPriceHashMap) {
                hm = stringPriceHashMap;



            }
        });

        sm.getShares().observe(getViewLifecycleOwner(), new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                my_shares = shares;

            }
        });


        mm = new ViewModelProvider(getActivity()).get(Man_Model.class);
        mm.setSession_db_ref(SDR.getGlobalVarValue());
        mm.getMan().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Manager>>() {
            @Override
            public void onChanged(HashMap<String, Manager> stringManagerHashMap) {
                manAdapter.clear();
                manAdapter.addAll(stringManagerHashMap.values());


            }
        });
        tm = new ViewModelProvider(getActivity()).get(Trade_Model.class);
        tm.setSession_db_ref(SDR.getGlobalVarValue());



    }


}