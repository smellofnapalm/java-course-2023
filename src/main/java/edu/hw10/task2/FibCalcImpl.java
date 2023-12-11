package edu.hw10.task2;

public class FibCalcImpl implements FibCalculator {
    @Override
    public long fib(int number) {
        final long[] arr = new long[number + 1];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i <= number; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[number];
    }
}
