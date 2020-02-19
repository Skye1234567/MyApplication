package Objects;

import java.util.ArrayList;
import java.util.Random;

public class Market {
    private float pi_h;
    private float pi_l;
    private float p;
    private ArrayList<Manager> managers;
    private ArrayList<Investor> investors;
    private Integer big_money;
    private Integer lil_money;

    public float getPi_h() {
        return pi_h;
    }

    public float getPi_l() {
        return pi_l;
    }

    public float getP() {
        return p;
    }

    private  ArrayList<Share> shares;
    private Integer num_players;

    public Integer getNum_players() {
        return num_players;
    }

    public void setNum_players(Integer num_players) {
        this.num_players = num_players;
    }

    public Market(float pi_h, float pi_l, float p) {
        this.pi_h = pi_h;
        this.pi_l = pi_l;
        this.p = p;
        this.big_money =100;
        this.lil_money =10;
    }



    public void setPi_h(float pi_h) {
        this.pi_h = pi_h;
    }

    public void setPi_l(float pi_l) {
        this.pi_l = pi_l;
    }

    public void setP(float p) {
        this.p = p;
    }


    public void generate_player_data(){
        Random r = new Random();

        for (Manager m :this.managers){
            float p_random = r.nextFloat();
            float pi_random = r.nextFloat();
            if (this.p<p_random){
                m.setPerformance(0);
                if (pi_l<pi_random){
                    m.setSalary(lil_money);
                }
                else {m.setSalary(big_money);}

            }
            else {
                if(pi_h<pi_random){
                    m.setSalary(lil_money);
                }
                else{
                    m.setSalary(big_money);
                }

            }
            for (Investor i: this.investors){
                i.add_Shares(new Share(i.getID(),m.getID()));
            }


        }


    }

}
