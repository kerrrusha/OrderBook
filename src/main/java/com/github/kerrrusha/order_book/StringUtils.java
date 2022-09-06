package com.github.kerrrusha.order_book;

public class StringUtils {
    public static boolean stringIsInvalid (String str) { return str == null || str.length() == 0; }
    public static int countMatches(String str, String substr) {
        return str.split(substr, -1).length - 1;
    }
}
