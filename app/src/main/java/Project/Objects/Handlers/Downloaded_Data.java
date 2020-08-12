package Project.Objects.Handlers;

import java.util.HashMap;

import Project.Objects.Economics.Price;
import Project.Objects.Economics.Schedule;
import Project.Objects.Economics.Session;
import Project.Objects.Economics.Share;
import Project.Objects.Economics.Trade;
import Project.Objects.Personel.Investor;
import Project.Objects.Personel.Manager;

public class Downloaded_Data {


    HashMap<String, Trade> buy;
    HashMap<String, Trade> archive;
    HashMap<String, Trade> sell;
    HashMap<String, Trade> completed;
    HashMap<String, Share> investor_shares;
    HashMap<String, Investor> investors;
    HashMap<String, Manager> managers;
    Session session;
    Schedule schedule;
    HashMap<String, Price> prices;

    public Downloaded_Data() {
    }

    @Override
    public String toString() {
        return "Downloaded_Data{" +
                "buy=" + buy.toString() +
                ", archive=" + archive.toString() +
                ", sell=" + sell.toString() +
                ", completed=" + completed.toString() +
                ", investor_shares=" + investor_shares.toString() +
                ", investors=" + investors.toString() +
                ", managers=" + managers.toString() +
                ", session=" + session.toString() +
                ", schedule=" + schedule.toString() +
                ", prices=" + prices.toString() +
                '}';
    }

    public HashMap<String, Trade> getBuy() {
        return buy;
    }

    public void setBuy(HashMap<String, Trade> buy) {
        this.buy = buy;
    }

    public HashMap<String, Trade> getArchive() {
        return archive;
    }

    public void setArchive(HashMap<String, Trade> archive) {
        this.archive = archive;
    }

    public HashMap<String, Trade> getSell() {
        return sell;
    }

    public void setSell(HashMap<String, Trade> sell) {
        this.sell = sell;
    }

    public HashMap<String, Trade> getCompleted() {
        return completed;
    }

    public void setCompleted(HashMap<String, Trade> completed) {
        this.completed = completed;
    }

    public HashMap<String, Share> getInvestor_shares() {
        return investor_shares;
    }

    public void setInvestor_shares(HashMap<String, Share> investor_shares) {
        this.investor_shares = investor_shares;
    }

    public HashMap<String, Investor> getInvestors() {
        return investors;
    }

    public void setInvestors(HashMap<String, Investor> investors) {
        this.investors = investors;
    }

    public HashMap<String, Manager> getManagers() {
        return managers;
    }

    public void setManagers(HashMap<String, Manager> managers) {
        this.managers = managers;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public HashMap<String, Price> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<String, Price> prices) {
        this.prices = prices;
    }
}
