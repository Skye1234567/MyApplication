package Objects;

import java.io.Serializable;

public class Session implements Serializable {
    private Integer num_players;
    private Market practice;
    private Market boom;
    private Market bust;

    public Session(Integer num_players, Market practice, Market boom, Market bust) {
        this.num_players = num_players;
        this.practice = practice;
        this.boom = boom;
        this.bust = bust;
    }

    public Session(Integer num_players) {
        this.num_players = num_players;
    }

    public Integer getNum_players() {
        return num_players;
    }

    public void setNum_players(Integer num_players) {
        this.num_players = num_players;
    }

    public Market getPractice() {
        return practice;
    }

    public void setPractice(Market practice) {
        this.practice = practice;
    }

    public Market getBoom() {
        return boom;
    }

    public void setBoom(Market boom) {
        this.boom = boom;
    }

    public Market getBust() {
        return bust;
    }

    public void setBust(Market bust) {
        this.bust = bust;
    }

    public boolean isValid(){
        return this.boom != null && this.bust != null && this.practice != null;
    }
}
