package Project.Activities.Managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import Project.Objects.Adapters.ManAdapter;
import Project.Objects.Database.IntegerDatabase;
import Project.Objects.Database.SessionDatabaseReference;
import Project.Objects.Handlers.DividendManager;
import Project.Objects.Handlers.Ledger;
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
import android.widget.Toast;

import Project.Business_Logic.Auditor;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Observable;


public class CompanyReportFragment extends Fragment {
    private Integer auditor_report;
    private Context context;
    private One_Man_Model man_model;
    private DatabaseReference Ref;
    private Manager managerman;
    private Button aud_yes;
    private ManHash manHash;
    private Button aud_no;
    private Button div_yes;
    private Button div_no;
    private Button strong;
    private Button weak;
    private Button submit;

    private Button Reject_Report;
    private TextView Audit_result_textview;
    private String audit_result;
    private ArrayList<Button> invisible;
    private IntegerDatabase cashDatabase;
    private IntegerDatabase profitDatabase;
    private boolean div;
    private boolean reject;
    private Ledger ledger;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__enter_report,container, false);
        div=false;
        reject=false;

        context = getContext();
        invisible=new ArrayList<>();
        manHash=new ManHash();
        Audit_result_textview = view.findViewById(R.id.auditresult);
        audit_result = Audit_result_textview.getText().toString();
        Reject_Report = view.findViewById(R.id.reject);
        Reject_Report.setVisibility(View.INVISIBLE);
        Reject_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref.child("audit_choice").setValue(0);
                reject=true;
                Toast.makeText(context, "Audit rejected", Toast.LENGTH_LONG);
                /*for (Button b :invisible){
                    b.setVisibility(View.VISIBLE);
                }
                invisible.clear();*/

            }
        });

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        SessionDatabaseReference SDR  = (SessionDatabaseReference) context.getApplicationContext();
        Ref = SDR.getGlobalVarValue().child("Managers").child(id);
        cashDatabase = new IntegerDatabase(Ref.child("cash"));
        profitDatabase = new IntegerDatabase(Ref.child("profit"));
        aud_yes = view.findViewById(R.id.yes_audit);
        aud_no = view.findViewById(R.id.no_audit);
        submit = view.findViewById(R.id.submitreport);
        ledger= new Ledger(0, 0, Ref.child("cash"));

        ;
        DatabaseReference databaseReference = SDR.getGlobalVarValue().child("Managers").
                child(id).child("cash");
        cashDatabase = new IntegerDatabase(databaseReference);
        cashDatabase.addObserver(new java.util.Observer() {
            @Override
            public void update(Observable o, Object arg) {
                ledger.setCallback((Integer) arg);
            }
        });
        cashDatabase.updating();
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
                Reject_Report.setVisibility(View.VISIBLE);

                cashDatabase.updating();
                Ref.child("audit_choice").setValue(1);
               auditor_report = new Auditor(managerman.getPerformance()).generateReport(managerman.getProfit());
               Ref.child("audit_report").setValue(auditor_report);

               Audit_result_textview.setText(audit_result+" "+manHash.highLowHash(auditor_report));
               aud_no.setVisibility(View.INVISIBLE);
               invisible.add(aud_no);
               ledger.setUpdate(-10);
               new Thread(ledger).start();



            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(div&&managerman!=null) new DividendManager(managerman.getCompany_symbol(), 5).payDividends();
                if (auditor_report!=null&&!reject)
                    Ref.child("report_performance").setValue(auditor_report);

                Intent intent = new Intent( context, Wait_Page.class);

                context.startActivity(intent);
            }
        });
        div_no = view.findViewById(R.id.no_dividend);
        div_yes =view.findViewById(R.id.yes_dividend);
        div_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                div=false;

                Ref.child("report_dividend").setValue(0);
                div_yes.setVisibility(View.INVISIBLE);
                invisible.add(div_yes);

            }
        });
        div_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                div =true;

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
                Ref.child("report_performance").setValue(1);
                weak.setVisibility(View.INVISIBLE);
                invisible.add(weak);
            }
        });
        weak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref.child("report_performance").setValue(0);
                strong.setVisibility(View.INVISIBLE);
                invisible.add(strong);
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
