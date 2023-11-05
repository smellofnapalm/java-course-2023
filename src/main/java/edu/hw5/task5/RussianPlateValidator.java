package edu.hw5.task5;

public final class RussianPlateValidator {
    static boolean validatePlate(String carNumber) {
        if (carNumber == null) {
            throw new NullPointerException("Номера машины не могут быть null!");
        }
        String letters = "[ABCEHKMOPTXYАВСЕНКМОРТХУ]";
        String pattern = "^%s{1}[0-9]{3}%s{2}[0-9]{3}$".formatted(letters, letters);
        return carNumber.matches(pattern);
    }

    private RussianPlateValidator() {
    }
}
