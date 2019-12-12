package Objects;

public class Trade {

    private Integer high_bid;
    private Integer low_price;
    private Integer num_shares;

    public Trade(Integer high_bid, Integer low_price, Integer num_shares) {
        this.high_bid = high_bid;
        this.low_price = low_price;
        this.num_shares = num_shares;
    }

    public Integer getNum_shares() {
        return num_shares;
    }

    public void setNum_shares(Integer num_shares) {
        this.num_shares = num_shares;
    }

    public Integer getHigh_bid() {
        return high_bid;
    }

    public void setHigh_bid(Integer high_bid) {
        this.high_bid = high_bid;
    }

    public Integer getLow_price() {
        return low_price;
    }

    public void setLow_price(Integer low_price) {
        this.low_price = low_price;
    }


    public Integer get_stock_price(){

        return this.high_bid+this.low_price/2;
    }

}
