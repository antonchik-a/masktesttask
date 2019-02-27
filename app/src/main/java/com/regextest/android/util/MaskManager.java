package com.regextest.android.util;

import java.util.regex.Pattern;

public class MaskManager {

    static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+^$\\\\|]");
    static String START_REGEX_CHAR = "^";
    static String END_REGEX_CHAR = "$";

    static String escapeSpecialRegexChars(String str) {
        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }

    public static String parseMask(String mask) {
        StringBuilder result = new StringBuilder();
        result.append(START_REGEX_CHAR);

        String escapedMask = escapeSpecialRegexChars(mask);
        escapedMask = escapedMask.replace("*", ".*");
        escapedMask = escapedMask.replace("?", ".?");
        result.append(escapedMask);
        result.append(END_REGEX_CHAR);

        return result.toString();
    }

}
