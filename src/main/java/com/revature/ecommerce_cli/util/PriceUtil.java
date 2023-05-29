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
        if (dotPlace != input.lastIndexOf('.')) throw new NumberFormatException();
        int precision = 0;
        int zeroesToAdd = 2;
        if(dotPlace != -1 ) {
            precision = len - 1 - dotPlace;
            zeroesToAdd = 2 - precision;
        }
        input = input + "0".repeat(Math.max(0, zeroesToAdd));
        input = input.substring(0, len + zeroesToAdd);
        return Integer.parseInt(input.replaceAll("\\.", ""));
    }

    public static int toCentsStrict(String input) throws NumberFormatException {
        int len = input.length();
        int dotPlace = input.indexOf('.');
        if(dotPlace != (len - 3)) throw new NumberFormatException();
        return Integer.parseInt(input.replaceAll("\\.", ""));
    }
}
