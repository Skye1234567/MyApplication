package project.Objects.Handlers;

import java.util.Random;

public class StringBuilderRandom {

    private Integer length;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public StringBuilderRandom(Integer length) {
        this.length = length;

    }

    public String buildString(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String string;


        for (int i=0; i<length;i++){
            char c =ALPHA_NUMERIC_STRING.charAt(random.nextInt(ALPHA_NUMERIC_STRING.length()-1));
            sb.append(c);



        }
        string = sb.toString().toUpperCase();
        return string;
    }



}


