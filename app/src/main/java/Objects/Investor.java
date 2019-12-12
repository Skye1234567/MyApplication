package Objects;

import java.util.ArrayList;

public class Investor extends Player {

    ArrayList<Share> Shares;


    public Investor(String ID) {
        super(ID);
        super.setType("I");
        this.Shares = new ArrayList<>();
    }
    public void add_Shares(Share share){
        Shares.add(share);

    }

    public Share find_share_by_company(String company_id){

        for (Share s: Shares) {
            if (s.getCompany().compareTo(company_id)==0){
                return s;}

        }
        return null;

    }

}
