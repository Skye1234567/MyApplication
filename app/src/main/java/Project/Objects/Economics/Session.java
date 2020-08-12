package Project.Objects.Economics;

import java.io.Serializable;

import Project.Objects.Economics.Market;

public class Session implements Serializable {

    private Market practice;
    private Market round_1;
    private Market round_2;

    public Session(Market practice, Market boom, Market bust) {

        this.practice = practice;
        this.round_1 = boom;
        this.round_2 = bust;
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
        return this.round_1 != null && this.round_2 != null && this.practice != null;
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
