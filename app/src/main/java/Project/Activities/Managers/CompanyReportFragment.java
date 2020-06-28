package Project.Activities.Managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import Project.Objects.Adapters.ManAdapter;
import Project.Objects.Database.IntegerDatabase;
import Project.Objects.Handlers.ManHash;
import Project.Objects.Personel.Manager;
import Project.Objects.Models.One_Man_Model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import Project.Business_Logic.Auditor;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CompanyReportFragment extends Fragment {
    Context context;
    One_Man_Model man_model;
    DatabaseReference Ref;
    Manager managerman;
    Button aud_yes;
    ManHash manHash;
    Button aud_no;
    Button div_yes;
    Button div_no;
    Button strong;
    Button weak;
    Button submit;
    Button Accept_Report;
    Button Reject_Report;
    TextView Audit_result_textview;
    String audit_result;
    ArrayList<Button> invisible;
    IntegerDatabase cashDatabase;
    IntegerDatabase profitDatabase;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__enter_report,container, false);


        context = getContext();
        invisible=new ArrayList<>();
        manHash=new ManHash();
        Audit_result_textview = view.findViewById(R.id.auditresult);
        audit_result = Audit_result_textview.getText().toString();

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        Ref = FirebaseDatabase.getInstance().getReference("Managers").child(id);
        cashDatabase = new IntegerDatabase(Ref.child("cash"));
        profitDatabase = new IntegerDatabase(Ref.child("profit"));
        aud_yes = view.findViewById(R.id.yes_audit);
        aud_no = view.findViewById(R.id.no_audit);
        submit = view.findViewById(R.id.submitreport);
        updateReportManager();

        aud_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref.child("audit_choice").setValue(0);
                aud_yes.setVisibility(View.INVISIBLE);
                invisible.add(aud_yes);

            }
        });

        aud_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Ref.child("audit_choice").setValue(1);

               Integer auditor_report = new Auditor(managerman.getPerformance()).generateReport(managerman.getProfit());
               Ref.child("audit_report").setValue(auditor_report);
               Audit_result_textview.setText(audit_result+" "+manHash.highLowHash(auditor_report));
               aud_no.setVisibility(View.INVISIBLE);
               invisible.add(aud_no);



            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, MarketPlaceForMan.class);

                context.startActivity(intent);
            }
        });
        div_no = view.findViewById(R.id.no_dividend);
        div_yes =view.findViewById(R.id.yes_dividend);
        div_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Ref.child("report_dividend").setValue(0);
                div_yes.setVisibility(View.INVISIBLE);
                invisible.add(div_yes);

            }
        });
        div_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Ref.child("report_dividend").setValue(1);
                div_no.setVisibility(View.INVISIBLE);
                invisible.add(div_no);
            }

        });

        strong = view.findViewById(R.id.strong_type);
        weak = view.findViewById(R.id.weak_type);
        strong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref.child("performance").setValue(1);
                weak.setVisibility(View.INVISIBLE);
                invisible.add(weak);
            }
        });
        weak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref.child("performance").setValue(0);
                strong.setVisibility(View.INVISIBLE);
                invisible.add(strong);
            }
        });

        Accept_Report = view.findViewById(R.id.acceptreport);
        Reject_Report = view.findViewById(R.id.reject);
        Reject_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Button b :invisible){
                    b.setVisibility(View.VISIBLE);
                }
                invisible.clear();

            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                managerman = manager;
            }
        });

    }

    private void updateReportManager() {
        man_model.getMan().observe(getViewLifecycleOwner(), new Observer<Manager>() {
            @Override
            public void onChanged(Manager manager) {
                managerman = manager;
            }
        });

    }
}
