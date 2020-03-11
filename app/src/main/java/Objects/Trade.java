package Objects;

import java.io.Serializable;
import java.util.Date;

public class Trade implements Serializable {

    private Integer num_shares;
    private Date timeStamp;
    private boolean completed;
    private boolean for_sale;
    private Integer price_point;


    public Trade() {
    }

    public Trade(Integer num_shares,  boolean for_sale, Integer price_point) {
        this.num_shares = num_shares;
        this.completed = false;
        this.for_sale = for_sale;
        this.price_point = price_point;
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
