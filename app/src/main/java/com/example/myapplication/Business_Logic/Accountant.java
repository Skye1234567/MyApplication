package com.example.myapplication.Business_Logic;

import java.security.SecureRandom;
import java.util.Random;

import Objects.Market;
// leon walras



public class Accountant {
    private Integer performance;
    private Integer revenue;



    public Accountant() {
        revenue = 10;


    }


    public Integer getPerformance() {
        return performance;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void generate_company_data(Float percent_chance_high){
        Double value = Math.random();
        if(value<percent_chance_high) {
            performance = 1;

        }else performance =0;

    }
public void generate_round_data(Float percent_chance_profit_h, Float percent_chance_profit_l) {
    Double val = Math.random();
    if ((performance==1 && val < percent_chance_profit_h) || (performance==0 && val < percent_chance_profit_l)) {
        revenue = 50;
    }
}

}
