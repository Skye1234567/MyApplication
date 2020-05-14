package com.example.myapplication.Business_Logic;

public class Auditor {
    /*Suppose that each round, a firm fills an urn with 10 balls.  If the current period earnings are high (regardless of the firm's underlying type), then each ball is Green with a probability of 5/6 and Blue with probability of 1/6.  (I didn't want red and black because red is positive with jumper cables but negative with bookkeeping.)  If current period earnings are low, then each ball is Green with probability 1/6 and Blue with probability 5/6.

    The auditor draws 3 balls from the urn.  It rules in favor of the majority of draws.  Then Prob(draw at least 2 green | high earnings) = 25/27, Prob(draw at least 2 green | low earnings) = 2/27.

    In other words, the audit makes the right call 25/27 of the time, or with around 93% probability.

    Now suppose the participants have 4 practice rounds, and that there are 4 firms.  The auditor will make the right call with probability (25/27)^16 because each audit is independent, across firms and across periods.  So during the practice rounds, there is around a 71% chance that at least one firm has an incorrect audit.

    Now imagine 6 additional rounds before the crash comes.  Then for each firm, the probability of having every audit being correct (if the firm audits every period) is (25/27)^10, or roughly 46%.  The probability of all four firms having all correct audits would be around 4.6% = (25/27)^40.

    So over time, because auditors make mistakes -- even when they are over 90% accurate -- some firm will get unlucky.  It might be nice to have some auditor mistakes likely to be observed before a market crash occurs.
*/
}
