package Objects;

public class Admin {
    private int player_num;

    public void begin_session(){
        Session  s = new Session();
        s.setNum_players(player_num);

    }

}
