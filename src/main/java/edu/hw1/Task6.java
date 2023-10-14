package edu.hw1;

import java.util.Arrays;
import static java.lang.Math.abs;

public final class Task6 {
    public static int fromArray(int[] a) {
        int ten = 1;
        int n = a.length;
        int res = 0;
        final int base = 10;
        for (int i = n - 1; i >= 0; i--) {
            res += ten * a[i];
            ten *= base;
        }
        return res;
    }

    public static int getKaprekar(int n) {
        final int base = 10;
        final int sz = 4;
        // Создадим массив из 4 цифр
        var a = new int[] {n / (base * base * base),
            (n % (base * base * base)) / (base * base),
            (n % (base * base)) / base, n % base};
        Arrays.sort(a);
        int asc = fromArray(a);
        // Развернем массив (сортировка в обратном порядке)
        var b = new int[sz];
        for (int i = 0; i < sz; i++) {
            b[i] = a[sz - 1 - i];
        }
        int desc = fromArray(b);
        return abs(desc - asc);
    }

    public static int countK(int x) {
        // Если число не четырехзначное, то операция некорректна
        // Или если все его цифры одинаковы -- т.е. оно делится на 1111
        final int stablePoint = 6174;
        final int lowerBound = 1000;
        final int upperBound = 10000;
        final int digitsEqual = 1111;
        if (x == stablePoint) {
            return 0;
        }
        if (x <= lowerBound || x >= upperBound || x % digitsEqual == 0) {
            return -1;
        }
        // Здесь нужна отдельная рекурсивная функция,
        // Так как условие x <= lowerBound может нарушиться,
        // Например, 9998 - 8999 = 0999
        return countKRecursion(getKaprekar(x)) + 1;
    }

    private static int countKRecursion(int x) {
        final int stablePoint = 6174;
        if (x == stablePoint) {
            return 0;
        }
        return countKRecursion(getKaprekar(x)) + 1;
    }

    private Task6() {
    }
}
