package Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class PriceAdapter extends BaseAdapter {
    ArrayList arrayList;


    public PriceAdapter(HashMap<String, Price> data){
        arrayList = new ArrayList();
        arrayList.addAll(data.entrySet());

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Map.Entry getItem(int position) {
        return (Map.Entry) arrayList.get(position);
    }

    @Override
    public long getItemId(int pos) {
        String str = (String)((Map.Entry)arrayList.get(pos)).getKey();
        return Long.valueOf(str);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_price, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry price = getItem(pos);
        Price p = (Price) price.getValue();
        String money = p.getPrice().toString();



        ((TextView) result.findViewById(R.id.symbol)).setText((String)price.getKey());
        ((TextView) result.findViewById(R.id.dollars)).setText(money);

        return result;
    }
}
