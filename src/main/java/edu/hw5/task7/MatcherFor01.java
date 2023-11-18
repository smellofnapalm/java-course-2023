package edu.hw5.task7;

import java.util.regex.Pattern;

public final class MatcherFor01 {
    final static Pattern PATTERN_01 = Pattern.compile("^[01]*$");
    final static Pattern PATTERN_THIRD_IS_ZERO = Pattern.compile("^[01]{2}0[01]*$");
    final static Pattern PATTERN_STARTS_ENDS_SAME = Pattern.compile("^(1[01]*1|0[01]*0)$");
    final static Pattern PATTERN_LENGTH_1_TO_3 = Pattern.compile("^[01]{1,3}$");
    private final static String ERROR = "Это не строка из нулей и единиц!";

    private static void verifyText(String text) {
        if (text == null || !PATTERN_01.matcher(text).matches()) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    static boolean thirdSymbolIsZero(String text) {
        verifyText(text);
        return PATTERN_THIRD_IS_ZERO.matcher(text).matches();
    }

    static boolean startsAndEndsWithSame(String text) {
        verifyText(text);
        return PATTERN_STARTS_ENDS_SAME.matcher(text).matches();
    }

    static boolean lengthFrom1To3(String text) {
        verifyText(text);
        return PATTERN_LENGTH_1_TO_3.matcher(text).matches();
    }

    private MatcherFor01() {
    }
}
