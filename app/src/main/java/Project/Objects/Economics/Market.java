package Project.Objects.Economics;

import java.io.Serializable;
import java.util.Objects;

public class Market implements Serializable {


    private Float pi_h;
    private Float pi_l;
    private Float p;
    private Integer num_rounds;
    private String type;

    @Override
    public String toString() {
        return "Market{" +
                "pi_h=" + pi_h.toString() +
                ", pi_l=" + pi_l.toString() +
                ", p=" + p.toString() +
                ", num_rounds=" + num_rounds.toString() +
                ", type='" + type + '\'' +
                '}';
    }

    public Market() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Market market = (Market) o;
        return Objects.equals(pi_h, market.pi_h) &&
                Objects.equals(pi_l, market.pi_l) &&
                Objects.equals(p, market.p) &&
                Objects.equals(num_rounds, market.num_rounds)&&
                Objects.equals(type, market.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pi_h, pi_l, p, num_rounds);
    }

    public Integer getNum_rounds() {
        return num_rounds;
    }

    public void setNum_rounds(Integer num_rounds) {
        this.num_rounds = num_rounds;
    }

    public Float getPi_h() {
        return pi_h;
    }

    public Float getPi_l() {
        return pi_l;
    }

    public Float getP() {
        return p;
    }




    public Market(Float pi_h, Float pi_l, Float p, Integer num_rounds) {
        this.pi_h = pi_h;
        this.pi_l = pi_l;
        this.p = p;

        this.num_rounds = num_rounds;
    }



    public void setPi_h(Float pi_h) {
        this.pi_h = pi_h;
    }

    public void setPi_l(Float pi_l) {
        this.pi_l = pi_l;
    }

    public void setP(Float p) {
        this.p = p;
    }





        }


