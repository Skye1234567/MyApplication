package Objects;

import java.io.Serializable;

public class Share implements Serializable {

    private Float price;
    private String owner;
    private String company;
    private Integer number;
    private Float asking_price;
    private Integer number_for_sale;

    public Integer getNumber_for_sale() {
        return number_for_sale;
    }

    public void setNumber_for_sale(Integer number_for_sale) {
        this.number_for_sale = number_for_sale;
    }

    public Float getAsking_price() {
        return asking_price;
    }

    public void setAsking_price(Float asking_price) {
        this.asking_price = asking_price;
    }

    public Share() {
    }

    public Share(String owner, String company) {
        this.owner = owner;
        this.company = company;
        this.number=10;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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
