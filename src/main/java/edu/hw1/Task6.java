package edu.hw1;

import java.util.Arrays;
import static java.lang.Math.abs;

public class Task6 {
    public static int fromArray(int[] a) {
        int ten = 1, n = a.length, res = 0;
        for (int i = n - 1; i >= 0; i--) {
            res += ten * a[i];
            ten *= 10;
        }
        return res;
    }

    public static int K(int n) {
        // Создадим массив из 4 цифр
<<<<<<< HEAD
        var a = new int[] {n / 1000, (n % 1000) / 100, (n % 100) / 10, n % 10};
=======
        var a = new int[] {n / (base * base * base), (n % (base * base * base)) / (base * base), (n % (base * base)) / base,
            n % base};
>>>>>>> parent of f24260f (Fixed check for too long line and made classes final)
        Arrays.sort(a);
        int asc = fromArray(a);
        // Развернем массив (сортировка в обратном порядке)
        var b = new int[4];
        for(int i = 0; i < 4; i++)
            b[i] = a[3-i];
        int desc = fromArray(b);
        return abs(desc - asc);
    }

    public static int countK(int n) {
        // Если число не четырехзначное, то операция некорректна
        // Или если все его цифры одинаковы -- т.е. оно делится на 1111
        if (n <= 1000 || n >= 10000 || n % 1111 == 0) {
            return -1;
        }
        int ans = 0;
        while (n != 6174) {
            n = K(n);
            ans++;
        }
        return ans;
    }
}
