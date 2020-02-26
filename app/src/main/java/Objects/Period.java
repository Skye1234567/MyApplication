package Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Period implements Serializable {

    private int session_id;
    private ArrayList player_ids;
    private int num_players;
    private ArrayList<Trade > Trades;

    public Period() {
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public ArrayList getPlayer_ids() {
        return player_ids;
    }

    public void setPlayer_ids(ArrayList player_ids) {
        this.player_ids = player_ids;
    }

    public int getNum_players() {
        return num_players;
    }

    public void setNum_players(int num_players) {
        this.num_players = num_players;
    }

    public ArrayList<Trade> getTrades() {
        return Trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        Trades = trades;
    }



}

