package Project.Objects.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.HashMap;
import java.util.List;

import Project.Objects.Handlers.ManHash;
import Project.Objects.Personel.Manager;
import androidx.annotation.NonNull;

public class ManAdapter extends ArrayAdapter<Manager> {
    ManHash manHash;


    public ManAdapter(@NonNull Context context, @NonNull List<Manager> objects) {
        super(context, R.layout.res_man, objects);
        manHash = new ManHash();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Manager manager = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.res_man, parent, false);
        }
        // Lookup view for data population
        TextView compsym = convertView.findViewById(R.id.compsymbolres);
        TextView perform = convertView.findViewById(R.id.performanceres);
        TextView audit = convertView.findViewById(R.id.auditres);
        TextView div = convertView.findViewById(R.id.dividendres);
        if (manager.getID()!=null) {

            // Populate the data into the template view using the data object
            compsym.setText(manager.getCompany_symbol());
            perform.setText(manHash.highLowHash(manager.getReport_performance()));
            audit.setText(manHash.YesNOHash(manager.getAudit_choice()));
            div.setText(manHash.YesNOHash(manager.getReport_dividend()));

        }
    // Return the completed view to render on screen
            return convertView;
}

}

