package Objects;

import java.io.Serializable;
import java.util.ArrayList;


public class Player implements Serializable {
    private String ID;
    private ArrayList<Integer> payout_history;
    private String type;

    public Player() {
    }

    public Player(String ID) {
        this.ID = ID;
        this.payout_history = new ArrayList<Integer>();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<Integer> getPayout_history() {
        return payout_history;
    }

    public void setPayout_history(ArrayList<Integer> payout_history) {
        this.payout_history = payout_history;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
