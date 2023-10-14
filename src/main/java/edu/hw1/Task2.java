package edu.hw1;

public final class Task2 {
    public static int countDigits(long num) {
        // Будем считать даже в случае отрицательного числа
        long x = num;
        int ans = 0;
        final long base = 10L;
        do {
            ans++;
            x /= base;
        } while (x != 0);
        return ans;
    }

    private Task2() {
    }
}
