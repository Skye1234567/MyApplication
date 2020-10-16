package project.objects.personel;

import java.io.Serializable;
import java.util.Objects;


public class Player implements Serializable {
    //Superclass for the player: contains the cash and value with with the rewards are calculated
    private String ID;
    private String type;
    private Integer cash;
    private Integer value;

    @Override
    public String toString() {
        return "Player{" +
                "ID='" + ID + '\'' +
                ", type='" + type + '\'' +
                ", cash=" + cash +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getID().equals(player.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Player() {

    }

    public Player(String ID) {
        this.ID = ID;
        this.cash=0;
        this.value=0;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
