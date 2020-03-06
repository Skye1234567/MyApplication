package Objects;

public class StringBuilderALpha {

    private Integer length;
    private boolean allCaps;

    public StringBuilderALpha(Integer length, boolean allCaps) {
        this.length = length;
        this.allCaps = allCaps;
    }

    public String buildString(){
        String string="";

        return string;
    }
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {

        StringBuilderALpha builder = new StringBuilderALpha();

        while (count-- != 0) {

            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString();

    }
}


