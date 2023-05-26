package com.revature.ecommerce_cli.util;

final public class PriceUtil {
    private PriceUtil() {}
    public static String toString(int price) {
        return(Integer.toString(price/10) + "." + Integer.toString(price%100));
    }
    public static int toCents(String input) throws NumberFormatException {
        return Integer.parseInt(input.replaceAll("\\.", ""));
    }
}
