package edu.hw1;

import static java.lang.Math.abs;

<<<<<<< HEAD
<<<<<<< HEAD
public class Task5 {
    public static boolean isPalindrome(long x) {
        var s = Long.toString(x);
        // Палиндромом считается только число, у которого >= 2 цифр
        if (s.length() < 2)
=======
public final class Task5 {
=======
public class Task5 {
>>>>>>> parent of f24260f (Fixed check for too long line and made classes final)
    public static boolean isPalindrome(long x) {
        var s = Long.toString(x);
        // Палиндромом считается только число, у которого >= 2 цифр
        if (s.length() < 2) {
>>>>>>> parent of d071d67 (Added more test for 5 and 6 tasks, fixed issue with incorrect work of makeStep in task 5)
            return false;

        int n = s.length();
<<<<<<< HEAD
        for (int i = 0; i < s.length()/2; i++)
            if (s.charAt(i) != s.charAt(n-1-i))
=======
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(n - 1 - i)) {
>>>>>>> parent of d071d67 (Added more test for 5 and 6 tasks, fixed issue with incorrect work of makeStep in task 5)
                return false;
        return true;
    }

    public static long makeStep(long x) {
        var s = Long.toString(x);
        long ans = 0;
<<<<<<< HEAD
        for(int i = 0; i < s.length()-1; i += 2) {
            ans += (s.charAt(i) - '0') + (s.charAt(i+1) - '0');
=======
        for (int i = 0; i < s.length() - 1; i += 2) {
            ans += (s.charAt(i) - '0') + (s.charAt(i + 1) - '0');
>>>>>>> parent of d071d67 (Added more test for 5 and 6 tasks, fixed issue with incorrect work of makeStep in task 5)
        }
        return ans;
    }

    public static boolean isPalindromeDescendant(long x) {
        // Будем считать, что, например, -121 -- палиндром
        // Т.е. если число отрицательное, то работать с его модулем
<<<<<<< HEAD
        x = abs(x);
        // Если задано число нечетной длины, то применять операцию к нему некорректно,
        // поэтому проверим только само число на палиндромность
        if (Long.toString(x).length() % 2 == 1)
=======
        var x = abs(num);
        // Если задано число нечетной длины, то применять операцию к нему некорректно,
        // поэтому проверим только само число на палиндромность
        if (Long.toString(x).length() % 2 == 1) {
>>>>>>> parent of d071d67 (Added more test for 5 and 6 tasks, fixed issue with incorrect work of makeStep in task 5)
            return isPalindrome(x);

<<<<<<< HEAD
        while(Long.toString(x).length() >= 2) {
            if (isPalindrome(x))
=======
        while (Long.toString(x).length() >= 2) {
            if (isPalindrome(x)) {
>>>>>>> parent of d071d67 (Added more test for 5 and 6 tasks, fixed issue with incorrect work of makeStep in task 5)
                return true;
            x = makeStep(x);
        }
        return false;
    }
}
