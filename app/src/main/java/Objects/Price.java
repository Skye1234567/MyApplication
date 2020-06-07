package Objects;

import java.io.Serializable;

public class Price implements Serializable {

    Integer high_bid=0;
    Integer low_ask=0;
    Integer price;

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

    public void challenge_bid(Integer challenger){
        if (challenger>high_bid) high_bid = challenger;
        calculate_price();
    }
    public void challenge_ask(Integer challenger){
        if (challenger<low_ask || low_ask==0) low_ask= challenger;
        calculate_price();
    }

    public Price(Integer high_bid, Integer low_ask) {
        this.high_bid = high_bid;
        this.low_ask = low_ask;
        calculate_price();
    }
}
