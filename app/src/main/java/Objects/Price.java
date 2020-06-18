package Objects;

import java.io.Serializable;
import java.util.HashMap;

public class Price implements Serializable {

    Integer high_bid=0;
    Integer low_ask=0;
    Integer price;
    HashMap<String, Integer> asks;
    HashMap<String, Integer> bids;


    public Integer getPrice() {
        return price;
    }


    public Price() {

    }

    public Integer getLow_ask() {
        return low_ask;
    }

    public void setLow_ask(Integer low_ask) {
        this.low_ask = low_ask;
    }

    public Integer getHigh_bid() {
        return high_bid;
    }

    public void setHigh_bid(Integer high_bid) {
        this.high_bid = high_bid;
    }

    public void calculate_price(){
        if (low_ask!=0&&high_bid!=0){
        price = low_ask+high_bid;
        price = price/2;}
        else{
            price = Math.max(low_ask, high_bid);
        }
    }



    public Price(Integer high_bid, Integer low_ask) {
        this.high_bid = high_bid;
        this.low_ask = low_ask;
        this.asks = new HashMap<>();
        this.bids = new HashMap<>();
        calculate_price();
    }
    public void add_ask(String key, Integer value){
        asks.put(key,value);
        Integer min = null;
        for (Integer i: asks.values()){
            if (min==null) min = i;
            else if (i<=min) min=i;

        }
        low_ask=min;
        calculate_price();
    }
    public void add_bid(String key, Integer value){
        bids.put(key,value);
        Integer max = null;
        for (Integer i: bids.values()){
            if (max==null) max = i;
            else if (i<=max) max=i;

        }
        high_bid=max;
        calculate_price();
    }
}
