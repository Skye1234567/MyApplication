package Objects;

import java.io.Serializable;

public class Share implements Serializable {

    private Float market_price;
    private String owner;
    private String company;
    private Integer number;
    private Float offer_amount;
    private Integer number_offered;
    private String status;

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

    public Float getOffer_amount() {
        return offer_amount;
    }

    public void setOffer_amount(Float offer_amount) {
        this.offer_amount = offer_amount;
    }

    public Share() {
    }

    public Share(String owner, String company) {
        this.owner = owner;
        this.company = company;
        this.number=10;
    }

    public Float getMarket_price() {
        return market_price;
    }

    public void setMarket_price(Float market_price) {
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
