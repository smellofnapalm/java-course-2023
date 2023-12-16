package edu.hw11.task3;

public final class RealFib {
    static long fib(int n) {
        if (n < 2) {
            return n;
        }
        long left = 0;
        long right = 1;
        long t = 0;
        for (int i = 2; i <= n; i++) {
            t = left + right;
            left = right;
            right = t;
        }
        return t;
    }

    private RealFib() {
    }
}
