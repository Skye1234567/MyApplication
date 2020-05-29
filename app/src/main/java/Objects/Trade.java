package Objects;

import java.io.Serializable;
import java.util.Date;

public class Trade implements Serializable {

    private Integer num_shares;
    private Date timeStamp;
    private boolean completed;
    private boolean for_sale;
    private Integer price_point;
    private String buyer_id;
    private String seller_id;



    public Trade() {
    }

    public Trade(Integer num_shares, Integer price_point) {
        this.num_shares = num_shares;
        this.completed = false;
        this.price_point = price_point;

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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
