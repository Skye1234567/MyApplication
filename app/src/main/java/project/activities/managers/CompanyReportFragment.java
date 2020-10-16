package project.activities.managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import project.objects.database.IntegerDatabase;
import project.objects.database.SessionDatabase;
import project.objects.database.SessionDatabaseReference;
import project.objects.economics.Session;
import project.objects.handlers.DividendManager;
import project.objects.handlers.Ledger;
import project.objects.handlers.ManHash;
import project.objects.personel.Manager;
import project.objects.models.One_Man_Model;
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

import project.business_logic.Auditor;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
    private SessionDatabaseReference SDR;
    private Session session;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager__enter_report,container, false);
        div=false;
        reject=false;

        context = getContext();
        invisible=new ArrayList<>();
        manHash=new ManHash();
        SDR= (SessionDatabaseReference)context.getApplicationContext();
        final DatabaseReference base_ref = SDR.getGlobalVarValue();
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
        SessionDatabase SD = new SessionDatabase(SDR.getGlobalVarValue());
        SD.addObserver(new java.util.Observer() {
            @Override
            public void update(Observable o, Object arg) {
                session=(Session)arg;
                if (session!=null){
                    if (session.isValid()){
                aud_yes.setClickable(true);
                aud_no.setClickable(true);
                div_no.setClickable(true);
                div_yes.setClickable(true);
                submit.setClickable(true);
                }}
                else session=new Session();

            }
        });
        SD.setParam();

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        man_model = new ViewModelProvider(getActivity()).get(One_Man_Model.class);
        SessionDatabaseReference SDR  = (SessionDatabaseReference) getActivity().getApplication();
        Ref = SDR.getGlobalVarValue().child("Managers").child(id);
        cashDatabase = new IntegerDatabase(Ref.child("cash"));
        profitDatabase = new IntegerDatabase(Ref.child("profit"));
        aud_yes = view.findViewById(R.id.yes_audit);
        aud_yes.setClickable(false);
        aud_no = view.findViewById(R.id.no_audit);
        aud_no.setClickable(false);
        submit = view.findViewById(R.id.submitreport);
        submit.setClickable(false);
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
               ledger.setUpdate(session.getAudit_cost());
               new Thread(ledger).start();



            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(div&&managerman!=null) new DividendManager(managerman.getCompany_symbol(), session.getDividend(), base_ref ).payDividends();
                if (auditor_report!=null&&!reject)
                    Ref.child("report_performance").setValue(auditor_report);

                Intent intent = new Intent( context, Wait_Page.class);

                context.startActivity(intent);
            }
        });
        div_no = view.findViewById(R.id.no_dividend);
        div_no.setClickable(false);
        div_yes =view.findViewById(R.id.yes_dividend);
        div_yes.setClickable(false);
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
