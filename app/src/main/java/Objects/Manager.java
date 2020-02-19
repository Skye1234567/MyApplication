package Objects;


public class Manager extends Player {
    private Integer salary;

    private int performance;
    private int profit;

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    void setAccept_audit(int accept_audit) {
        this.accept_audit = accept_audit;
    }

    private int report_performance;
    private int report_dividend;

    public int getReport_performance() {
        return report_performance;
    }

    public void setReport_performance(int report_performance) {
        this.report_performance = report_performance;
    }

    public int getAudit_report() {
        return audit_report;
    }

    public void setAudit_report(int audit_report) {
        this.audit_report = audit_report;
    }

    public int getAccept_audit() {
        return accept_audit;
    }


    private int audit_choice;
    private int audit_report;
    private int accept_audit;
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
        setPerformance(1);




    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getReport_dividend() {
        return report_dividend;
    }

    public void setReport_dividend(int report_dividend) {
        this.report_dividend = report_dividend;
    }

    public int getAudit_choice() {
        return audit_choice;
    }

    public void setAudit_choice(int audit_choice) {
        this.audit_choice = audit_choice;
    }
}