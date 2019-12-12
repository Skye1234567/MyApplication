package Objects;

import java.util.ArrayList;
import java.util.Random;

public class Market {
    private float pi_h;
    private float pi_l;
    private float p;
    private ArrayList<Manager> managers;
    private ArrayList<Investor> investors;
    private int big_money;
    private int lil_money;
    private  ArrayList<Share> shares;

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
                m.setGood_performance(false);
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
