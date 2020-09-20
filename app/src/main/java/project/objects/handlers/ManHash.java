package project.objects.handlers;

import java.util.HashMap;

public class ManHash {
    private HashMap<Integer, String> hash;
    private HashMap<Integer, String> hash2;

    public ManHash() {
        hash = new HashMap<Integer, String>();
        hash2 = new HashMap<Integer, String>();
        hash.put(0, "No");
        hash.put(1, "Yes");
        hash2.put(0, "Low");
        hash2.put(1, "High");

    }

    public String YesNOHash(Integer i){
        if (i==null) return "---";

        return hash.get(i);

    }
    public String highLowHash(Integer i){
        if (i==null) return "---";

        return hash2.get(i);

    }
}
