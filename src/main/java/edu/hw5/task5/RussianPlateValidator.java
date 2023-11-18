package edu.hw5.task5;

import java.util.regex.Pattern;

public final class RussianPlateValidator {
    private static final String LETTERS = "[ABCEHKMOPTXY]";
    private static final Pattern PATTERN = Pattern.compile("^%s{1}[0-9]{3}%s{2}[0-9]{3}$".formatted(LETTERS, LETTERS));

    static boolean validatePlate(String carNumber) {
        if (carNumber == null) {
            throw new NullPointerException("Номера машины не могут быть null!");
        }
        return PATTERN.matcher(carNumber).matches();
    }

    private RussianPlateValidator() {
    }
}
