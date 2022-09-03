package com.github.kerrrusha.order_book;

public class StringUtils {
    public static int countMatches(String str, String findStr) {
        return str.split(findStr, -1).length - 1;
    }
}
