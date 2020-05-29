package Objects;

import java.io.Serializable;
import java.util.Objects;

public class Share implements Serializable {

    private Integer market_price;
    private String owner;
    private String company;
    private Integer number;
    private Integer offer_amount;
    private Integer number_offered;
    private String status;
    private String manager_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber_offered() {
        return number_offered;
    }

    public void setNumber_offered(Integer number_offered) {
        this.number_offered = number_offered;
    }

    public Integer getOffer_amount() {
        return offer_amount;
    }

    public void setOffer_amount(Integer offer_amount) {
        this.offer_amount = offer_amount;
    }

    public Share() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Share)) return false;
        Share share = (Share) o;
        return getCompany().equals(share.getCompany());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompany());
    }

    public Share(String owner, String company, String manager_id) {
        this.owner = owner;
        this.company = company;
        this.number=10;
        this.manager_id=manager_id;
        this.number_offered=0;
        this.market_price=0;
        this.offer_amount=0;
    }

    public Integer getMarket_price() {
        return market_price;
    }

    public void setMarket_price(Integer market_price) {
        this.market_price = market_price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
