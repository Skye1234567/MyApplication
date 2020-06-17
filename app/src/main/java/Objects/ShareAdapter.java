package Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import androidx.annotation.NonNull;

public class ShareAdapter extends ArrayAdapter<Share> {
    public ShareAdapter(@NonNull Context context,  @NonNull List<Share> objects) {
        super(context, R.layout.res_share , objects);
    }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
           Share share = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.res_share, parent, false);
            }
            // Lookup view for data population
            TextView compsym = convertView.findViewById(R.id.compsymb);
            TextView sharenum = convertView.findViewById(R.id.sharenumb);
            TextView mp = convertView.findViewById(R.id.mp);
            // Populate the data into the template view using the data object
            compsym.setText(share.getCompany());
            sharenum.setText(share.getNumber().toString());
            mp.setText(share.getMarket_price().toString());
            // Return the completed view to render on screen
            return convertView;
        }
    }

