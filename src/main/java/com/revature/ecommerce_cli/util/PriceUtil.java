package com.revature.ecommerce_cli.util;
import java.lang.NumberFormatException;

final public class PriceUtil {
    private PriceUtil() {}
    public static String centsToString(int price) {
        return("$" + Integer.toString(price/100) + "." + String.format("%02d", price%100));
    }

    public static String centsToString (long price) {
        return("$" + Long.toString(price/100) + "." + String.format("%02d", price%100));
    }

    public static int toCents(String input) throws NumberFormatException {
        int len = input.length();
        int dotPlace = input.indexOf('.');
        if(dotPlace != -1 || dotPlace != (len -3)) throw new NumberFormatException();
        return Integer.parseInt(input.replaceAll("\\.", ""));
    }
}
