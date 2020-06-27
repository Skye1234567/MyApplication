package Objects;

import java.io.Serializable;

public class Schedule implements Serializable {


    private long start;
    private long invest;
    private long report;

    public Schedule() {
    }


    public Schedule(long sessionstart_time, long investing_round_length, long enter_report_length) {
        this.start = sessionstart_time;
        this.invest = investing_round_length;
        this.report = enter_report_length;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getInvest() {
        return invest;
    }

    public void setInvest(long invest) {
        this.invest = invest;
    }

    public long getReport() {
        return report;
    }

    public void setReport(long report) {
        this.report = report;
    }
}
