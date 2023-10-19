package edu.hw3.task4;

import java.util.TreeMap;

public final class ConvertToRoman {

    private final static TreeMap<Integer, String> DICT = new TreeMap<Integer, String>();

    @SuppressWarnings("MagicNumber")
    private static void fillDict() {
        if (!DICT.isEmpty()) {
            return;
        }
        DICT.put(1, "I");
        DICT.put(4, "IV");
        DICT.put(5, "V");
        DICT.put(9, "IX");
        DICT.put(10, "X");
        DICT.put(40, "XL");
        DICT.put(50, "L");
        DICT.put(90, "XC");
        DICT.put(100, "C");
        DICT.put(400, "CD");
        DICT.put(500, "D");
        DICT.put(900, "CM");
        DICT.put(1000, "M");
    }

    public static String convertToRoman(int x) throws IllegalArgumentException {
        final int UPPER_BOUND = 4000;
        if (x <= 0 || x >= UPPER_BOUND) {
            throw new IllegalArgumentException("Римские числа могут быть только от 1 до 3999");
        }
        fillDict();
        return convertToRomanRecursive(x);
    }

    private static String convertToRomanRecursive(int x) {
        // Находим в списке наибольшее значение, которое не превосходит x,
        // Добавляем его римскую версию в ответ и погружаемся в рекурсию
        int lowerBoundKey = DICT.floorKey(x);
        if (x == lowerBoundKey) {
            return DICT.get(x);
        }
        return DICT.get(lowerBoundKey) + convertToRoman(x - lowerBoundKey);
    }

    private ConvertToRoman() {
    }
}
