package Project.Objects.Personel;


import Project.Objects.Personel.Player;

public class Manager extends Player {
    private Integer salary;
    private String company_symbol;
    private Integer performance;
    private Integer profit;
    private Integer report_performance;
    private Integer report_dividend;

    public Manager() {
    }


    public String getCompany_symbol() {
        return company_symbol;
    }
    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    void setAccept_audit(Integer accept_audit) {
        this.accept_audit = accept_audit;
    }


    public void setCompany_symbol(String company_symbol) {
        this.company_symbol = company_symbol;
    }

    public Integer getReport_performance() {
        return report_performance;
    }

    public void setReport_performance(Integer report_performance) {
        this.report_performance = report_performance;
    }

    public Integer getAudit_report() {
        return audit_report;
    }

    public void setAudit_report(Integer audit_report) {
        this.audit_report = audit_report;
    }

    public Integer getAccept_audit() {
        return accept_audit;
    }


    private Integer audit_choice;
    private Integer audit_report;
    private Integer accept_audit;
    private Integer num_shares;

    public Integer getNum_shares() {
        return num_shares;
    }

    public void setNum_shares(Integer num_shares) {
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

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Integer getReport_dividend() {
        return report_dividend;
    }

    public void setReport_dividend(Integer report_dividend) {
        this.report_dividend = report_dividend;
    }

    public Integer getAudit_choice() {
        return audit_choice;
    }

    public void setAudit_choice(Integer audit_choice) {
        this.audit_choice = audit_choice;
    }

    public boolean isValid(){
        return (profit!=null &&performance!=null);
    }

}

