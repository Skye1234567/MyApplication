package project.objects.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import project.objects.economics.Price;

public class PriceAdapter extends ArrayAdapter<Map.Entry<String, Price>>{


    public PriceAdapter(@NonNull Context context,  @NonNull List<Map.Entry<String, Price>> objects) {
        super(context, R.layout.res_price , objects);}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       Map.Entry<String, Price> entry = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.res_price, parent, false);
        }

        ( (TextView) convertView.findViewById(R.id.symbol)).setText(entry.getKey());


        ( (TextView) convertView.findViewById(R.id.dollars)).setText(entry.getValue().getPrice().toString());



        return convertView;
    }
}