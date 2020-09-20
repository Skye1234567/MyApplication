package project.objects.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import project.objects.economics.Trade;
import androidx.annotation.NonNull;

public class TradeAdapter extends ArrayAdapter<Trade> {
    public TradeAdapter(@NonNull Context context, @NonNull List<Trade> objects) {
        super(context, R.layout.res_trade , objects);
    }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
           Trade trade = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.res_trade, parent, false);
            }
            // Lookup view for data population
            TextView compsym = convertView.findViewById(R.id.compsymbtrade);
            TextView tradenum = convertView.findViewById(R.id.tradenumb);
            TextView dollars = convertView.findViewById(R.id.tradedollars);
            // Populate the data into the template view using the data object
            compsym.setText(trade.getCompany());
            tradenum.setText(trade.getNum_shares().toString());
            dollars.setText(trade.getPrice_point().toString());
            // Return the completed view to render on screen
            return convertView;
        }
    }

