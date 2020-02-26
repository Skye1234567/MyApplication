package Objects;

import java.io.Serializable;

public class Share implements Serializable {

    private Integer price;
    private String owner;
    private String company;
    private int number;

    public Share() {
    }

    public Share(String owner, String company) {
        this.owner = owner;
        this.company = company;
        this.number=10;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
