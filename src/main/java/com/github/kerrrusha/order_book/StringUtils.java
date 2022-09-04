package com.github.kerrrusha.order_book;

public class StringUtils {
    public static int countMatches(String str, String substr) {
        return str.split(substr, -1).length - 1;
    }
}
