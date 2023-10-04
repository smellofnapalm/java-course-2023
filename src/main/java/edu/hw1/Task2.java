package edu.hw1;

import static java.lang.Math.abs;

public class Task2 {
    public static int countDigits(long num) {
        // Будем считать даже в случае отрицательного числа
        num = abs(num);
        int ans = 0;
        do {
            ans++;
            num /= 10L;
        } while(num > 0);
        return ans;
    }
}
