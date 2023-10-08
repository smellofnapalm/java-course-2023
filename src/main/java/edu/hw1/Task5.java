package edu.hw1;

import static java.lang.Math.abs;

public final class Task5 {
    public static boolean isPalindrome(String x) {
        // Палиндромом считается только число, у которого >= 2 цифр
        if (x.length() < 2) {
            return false;
        }

        int n = x.length();
        for (int i = 0; i < x.length() / 2; i++) {
            if (x.charAt(i) != x.charAt(n - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static String makeStep(String x) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < x.length() - 1; i += 2) {
            ans.append((x.charAt(i) - '0') + (x.charAt(i + 1) - '0'));
        }
        return ans.toString();
    }

    public static boolean isPalindromeDescendant(long num) {
        // Будем считать, что, например, -121 -- палиндром
        // Т.е. если число отрицательное, то работать с его модулем
        String x = Long.toString(abs(num));
        // Если задано число нечетной длины, то применять операцию к нему некорректно,
        // поэтому проверим только само число на палиндромность
        if (x.length() % 2 == 1) {
            return isPalindrome(x);
        }

        while (x.length() >= 2) {
            if (x.length() % 2 == 1) {
                return isPalindrome(x);
            }
            if (isPalindrome(x)) {
                return true;
            }
            x = makeStep(x);
        }
        return false;
    }

    private Task5() {
    }
}
