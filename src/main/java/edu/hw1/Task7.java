package edu.hw1;

import java.util.Collections;
import java.util.Vector;

public final class Task7 {

    public static int[] intToBits(int x) {
        int n = x;
        var a = new Vector<Integer>();
        do {
            a.add(n % 2);
            n /= 2;
        } while (n > 0);
        Collections.reverse(a);
        return a.stream().mapToInt(i -> i).toArray();
    }

    public static int bitsToInt(int[] a) {
        int n = a.length;
        int res = 0;
        int two = 1;
        for (int i = n - 1; i >= 0; i--) {
            res += two * a[i];
            two *= 2;
        }
        return res;
    }

    public static int[] shiftLeft(int[] a, int shift) {
        int length = a.length;
        int[] b = new int[length];
        System.arraycopy(a, shift, b, 0, length - shift);
        System.arraycopy(a, 0, b, length - shift, shift);
        return b;
    }

    public static int rotateLeft(int n, int shift) {
        // Не работаем с отрицательными числами
        if (n < 0) {
            return -1;
        }
        var a = intToBits(n);
        // Чтобы shift всегда был в [0, length-1]
        var shiftNew = (shift % a.length + a.length) % a.length;
        a = shiftLeft(a, shiftNew);
        return bitsToInt(a);
    }

    public static int rotateRight(int n, int shift) {
        // Не работаем с отрицательными числами
        if (n < 0) {
            return -1;
        }
        var a = intToBits(n);
        // Чтобы shift всегда был в [0, length-1]
        var shiftNew = (shift % a.length + a.length) % a.length;
        // Сдвиг вправо на shift = сдвиг влево на длину числа в битах - shift
        a = shiftLeft(a, a.length - shiftNew);
        return bitsToInt(a);
    }

    private Task7() {
    }
}
