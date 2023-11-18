package edu.hw5.task8;

import java.util.regex.Pattern;

public final class ImprovedMatcher01 {
    private final static Pattern PATTERN_01 = Pattern.compile("^[01]*$");
    private final static Pattern EVEN_PATTERN = Pattern.compile("(00|01|10|11)*");
    private final static Pattern ODD_LENGTH = Pattern.compile("^(00|01|10|11)*[01]$");
    private final static Pattern
        ZERO_ODD_OR_ONE_EVEN = Pattern.compile("^(0%s|1[01]%s)$".formatted(EVEN_PATTERN, EVEN_PATTERN));

    private final static Pattern HAS_3K_OF_ZEROES = Pattern.compile("^(1*01*01*01*)*$");
    private final static Pattern EXCEPT_11_OR_111 =
        Pattern.compile("^([01]|[01]0|01|[01]{2}0|[01]0[01]|0[01]{2}|[01]{4,})$");

    private final static Pattern EVERY_ODD_IS_ONE =
        Pattern.compile("^(1[01])*1?$");
    private final static Pattern HAS_TWO_OR_MORE_ZEROES_AND_NOT_MORE_THAN_ONE_ONE =
        Pattern.compile("^0*(010|001|100|00)0*$");
    private final static Pattern NO_CONSECUTIVE_ONES =
        Pattern.compile("^(10|0)*[01]?$");
    private final static String ERROR = "Это не строка из нулей и единиц!";

    private static void verifyText(String text) throws IllegalArgumentException {
        if (text == null || !PATTERN_01.matcher(text).matches()) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    private static boolean matchesPattern(String text, Pattern pattern) {
        verifyText(text);
        return pattern.matcher(text).matches();
    }

    static boolean hasOddLength(String text) {
        return matchesPattern(text, ODD_LENGTH);
    }

    static boolean zeroOddOrOneEven(String text) {
        return matchesPattern(text, ZERO_ODD_OR_ONE_EVEN);
    }

    static boolean has3kOfZeroes(String text) {
        return matchesPattern(text, HAS_3K_OF_ZEROES);
    }

    static boolean except11Or111(String text) {
        return matchesPattern(text, EXCEPT_11_OR_111);
    }

    static boolean everyOddIsOne(String text) {
        return matchesPattern(text, EVERY_ODD_IS_ONE);
    }

    static boolean hasTwoOrMoreZeroesAndNotMoreThanOneOne(String text) {
        return matchesPattern(text, HAS_TWO_OR_MORE_ZEROES_AND_NOT_MORE_THAN_ONE_ONE);
    }

    static boolean noConsecutiveOnes(String text) {
        return matchesPattern(text, NO_CONSECUTIVE_ONES);
    }

    private ImprovedMatcher01() {
    }
}
