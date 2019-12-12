package Objects;


public class Manager extends Player {
    private Integer salary;
    private boolean good_performance;
    private boolean dividend;
    private boolean audit;
    private int num_shares;

    public int getNum_shares() {
        return num_shares;
    }

    public void setNum_shares(int num_shares) {
        this.num_shares = num_shares;
    }

    public Manager(String ID) {
        super(ID);
        super.setType("M");
        this.good_performance = true;


    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public boolean isGood_performance() {
        return good_performance;
    }

    public void setGood_performance(boolean good_performance) {
        this.good_performance = good_performance;
    }

    public boolean isDividend() {
        return dividend;
    }

    public void setDividend(boolean dividend) {
        this.dividend = dividend;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }
}