package edu.hw1;

import static java.lang.Math.abs;

public class Task5 {
    public static boolean isPalindrome(long x) {
        var s = Long.toString(x);
        // Палиндромом считается только число, у которого >= 2 цифр
        if (s.length() < 2) {
            return false;
        }

        int n = s.length();
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(n - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static long makeStep(long x) {
        var s = Long.toString(x);
        long ans = 0;
        for (int i = 0; i < s.length() - 1; i += 2) {
            ans += (s.charAt(i) - '0') + (s.charAt(i + 1) - '0');
        }
        return ans;
    }

    public static boolean isPalindromeDescendant(long num) {
        // Будем считать, что, например, -121 -- палиндром
        // Т.е. если число отрицательное, то работать с его модулем
        var x = abs(num);
        // Если задано число нечетной длины, то применять операцию к нему некорректно,
        // поэтому проверим только само число на палиндромность
        if (Long.toString(x).length() % 2 == 1) {
            return isPalindrome(x);
        }

        while (Long.toString(x).length() >= 2) {
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
