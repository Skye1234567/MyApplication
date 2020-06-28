package Project.Objects.Economics;

import java.io.Serializable;

public class Share implements Serializable {

    private Integer market_price;
    private String owner;
    private String company;
    private Integer number;
   String manager_id;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Share() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Share)) return false;
        Share share = (Share) o;
        return getCompany().compareTo(share.getCompany())==0;
    }


    public Share(String owner, String company, String manager_id) {
        this.owner = owner;
        this.company = company;
        this.number=10;
        this.manager_id=manager_id;

       this.market_price=0;

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



    public Integer calculate_shareholder_value(){
        if (this.number==null || this.market_price==null) return 0;

        return this.number*this.market_price;
    }
}
