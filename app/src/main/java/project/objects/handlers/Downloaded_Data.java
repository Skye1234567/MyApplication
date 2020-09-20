package project.objects.handlers;

import java.util.HashMap;

import project.objects.economics.Price;
import project.objects.economics.Schedule;
import project.objects.economics.Session;
import project.objects.economics.Share;
import project.objects.economics.Trade;
import project.objects.personel.Investor;
import project.objects.personel.Manager;

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
                "buy=" + buy +
                ", archive=" + archive +
                ", sell=" + sell +
                ", completed=" + completed +
                ", investor_shares=" + investor_shares+
                ", investors=" + investors +
                ", managers=" + managers +
                ", session=" + session+
                ", schedule=" + schedule+
            ", prices=" + prices +
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
