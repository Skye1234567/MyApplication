package project.objects.economics;

import java.io.Serializable;
import java.util.Objects;

public class Session implements Serializable {
    private String id;
    private Integer starting_sum;
    private Integer audit_cost;
    private Integer big_payoff;
    private Integer small_payoff;
    private Integer dividend;
    private Market practice;
    private Market round_1;
    private Market round_2;

    public Session(Market practice, Market boom, Market bust, String id) {

        this.practice = practice;
        this.round_1 = boom;
        this.round_2 = bust;
        this.id = id;
    }

    public Integer getStarting_sum() {
        return starting_sum;
    }

    public void setStarting_sum(Integer starting_sum) {
        this.starting_sum = starting_sum;
    }

    public Integer getAudit_cost() {
        return audit_cost;
    }

    public void setAudit_cost(Integer audit_cost) {
        this.audit_cost = audit_cost;
    }

    public Integer getBig_payoff() {
        return big_payoff;
    }

    public void setBig_payoff(Integer big_payoff) {
        this.big_payoff = big_payoff;
    }

    public Integer getSmall_payoff() {
        return small_payoff;
    }

    public void setSmall_payoff(Integer small_payoff) {
        this.small_payoff = small_payoff;
    }

    public Integer getDividend() {
        return dividend;
    }

    public void setDividend(Integer dividend) {
        this.dividend = dividend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return getId().equals(session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Session{" +
                "practice=" + practice +
                ", round_1=" + round_1 +
                ", round_2=" + round_2 +
                '}';
    }

    public Session() {

    }
    public boolean constants_set(){
        if (starting_sum!=null&&audit_cost!=null&&big_payoff!=null&&small_payoff!=null&&dividend!=null)return true;
        else
        return false;
    }

    public Session(String id, Integer starting_sum, Integer audit_cost, Integer big_payoff, Integer small_payoff, Integer dividend, Market practice, Market round_1, Market round_2) {
        this.id = id;
        this.starting_sum = starting_sum;
        this.audit_cost = audit_cost;
        this.big_payoff = big_payoff;
        this.small_payoff = small_payoff;
        this.dividend = dividend;
        this.practice = practice;
        this.round_1 = round_1;
        this.round_2 = round_2;
    }

    public Market getPractice() {
        return practice;
    }

    public void setPractice(Market practice) {
        this.practice = practice;
    }

    public Market getRound_1() {
        return round_1;
    }

    public void setRound_1(Market round_1) {
        this.round_1 = round_1;
    }

    public Market getRound_2() {
        return round_2;
    }

    public void setRound_2(Market round_2) {
        this.round_2 = round_2;
    }

    public boolean isValid(){
        return this.round_1 != null && this.round_2 != null && this.practice != null &&constants_set();
    }

    public Market round_to_market(Integer round_num){
        int bound = getPractice().getNum_rounds();
        int bound2 = bound +getRound_1().getNum_rounds();
        int bound3 = bound2+ getRound_2().getNum_rounds();

        if (round_num<bound) return getPractice();

        else if (round_num<bound2)return getRound_1();

        else if (round_num<bound3)return getRound_2();
        else{
            return null;
        }


    }
}
