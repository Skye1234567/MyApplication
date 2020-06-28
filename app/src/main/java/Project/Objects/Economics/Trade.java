package Project.Objects.Economics;

import java.io.Serializable;
import java.util.Objects;

public class Trade implements Serializable {
    private String id;

    private Integer num_shares;
    private long timeStamp;
    private boolean for_sale;
    private Integer price_point;
    private String buyer_id;
    private String seller_id;
    private String company;
    public Trade() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return Objects.equals(getId(), trade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public boolean greater_than(Trade other_trade){
        return this.price_point > other_trade.price_point;
    }
    public Trade(Integer num_shares, Integer price_point, String company) {
        this.num_shares = num_shares;
        this.price_point = price_point;
        this.company=company;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isFor_sale() {
        return for_sale;
    }

    public void setFor_sale(boolean for_sale) {
        this.for_sale = for_sale;
    }

    public Integer getPrice_point() {
        return price_point;
    }

    public void setPrice_point(Integer price_point) {
        this.price_point = price_point;
    }

    public Integer getNum_shares() {
        return num_shares;
    }

    public void setNum_shares(Integer num_shares) {
        this.num_shares = num_shares;
    }




}
