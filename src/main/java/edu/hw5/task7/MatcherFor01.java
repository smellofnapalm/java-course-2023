package edu.hw5.task7;

public final class MatcherFor01 {
    final static String PATTERN_01 = "^[01]*$";
    final static String ERROR = "Это не строка из нулей и единиц!";

    private static void verifyText(String text) {
        if (text == null || !text.matches(PATTERN_01)) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    static boolean thirdSymbolIsZero(String text) {
        verifyText(text);
        String pattern = "^[01]{2}0[01]*$";
        return text.matches(pattern);
    }

    static boolean startsAndEndsWithSame(String text) {
        verifyText(text);
        String pattern = "^(1[01]*1|0[01]*0)$";
        return text.matches(pattern);
    }

    static boolean lengthFrom1To3(String text) {
        verifyText(text);
        String pattern = "^[01]{1,3}$";
        return text.matches(pattern);
    }

    private MatcherFor01() {
    }
}
