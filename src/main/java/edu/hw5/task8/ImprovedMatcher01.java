package edu.hw5.task8;

public final class ImprovedMatcher01 {
    final static String PATTERN_01 = "^[01]*$";
    final static String EVEN_PATTERN = "(00|01|10|11)*";
    final static String ERROR = "Это не строка из нулей и единиц!";

    private static void verifyText(String text) {
        if (text == null || !text.matches(PATTERN_01)) {
            throw new IllegalArgumentException(ERROR);
        }
    }

    static boolean hasOddLength(String text) {
        verifyText(text);
        String pattern = "^(00|01|10|11)*[01]$";
        return text.matches(pattern);
    }

    static boolean zeroOddOrOneEven(String text) {
        verifyText(text);
        String pattern = "^(0%s|1[01]%s)$".formatted(EVEN_PATTERN, EVEN_PATTERN);
        return text.matches(pattern);
    }

    static boolean has3kOfZeroes(String text) {
        verifyText(text);
        String pattern = "^(1*01*01*01*)*$";
        return text.matches(pattern);
    }

    static boolean except11Or111(String text) {
        verifyText(text);
        String pattern = "^([01]|[01]0|01|[01]{2}0|[01]0[01]|0[01]{2}|[01]{4,})$";
        return text.matches(pattern);
    }

    static boolean everyOddIsOne(String text) {
        verifyText(text);
        String pattern = "^(1[01])*1?$";
        return text.matches(pattern);
    }

    static boolean hasTwoOrMoreZeroesAndNotMoreThanOneOne(String text) {
        verifyText(text);
        String pattern = "^0*(010|001|100|00)0*$";
        return text.matches(pattern);
    }

    static boolean noConsecutiveOnes(String text) {
        verifyText(text);
        String pattern = "^(10|0)*[01]?$";
        return text.matches(pattern);
    }

    private ImprovedMatcher01() {
    }
}
