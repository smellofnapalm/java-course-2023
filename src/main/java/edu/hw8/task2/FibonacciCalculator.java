package edu.hw8.task2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class FibonacciCalculator {
    final int n;
    final int threadCount;
    final List<Long> fibList;
    final ThreadPool threadPool;
    final private static int WAIT_TIME = 100;

    FibonacciCalculator(int n, int threadCount) {
        if (n <= 0 || threadCount <= 0) {
            throw new IllegalArgumentException("Нельзя посчитать число Фибоначчи с отрицательным номером");
        }
        this.n = n;
        this.threadCount = threadCount;
        this.fibList = new CopyOnWriteArrayList<>();
        for (int i = 0; i <= n; i++) {
            fibList.add(0L);
        }
        threadPool = PoolFabric.create(threadCount);
    }

    long calcNth() {
        threadPool.start();
        threadPool.execute(() -> fib(n));
        try {
            threadPool.close();
        } catch (Exception ignored) {
        }
        return fibList.get(n);
    }

    private void fib(int n) {
        if (n == 0 || fibList.get(n) != 0) {
            return;
        }
        if (fibList.get(n - 1) == 0) {
            threadPool.execute(() -> fib(n - 1));
            try {
                synchronized (Thread.currentThread()) {
                    Thread.currentThread().wait(WAIT_TIME);
                }
            } catch (InterruptedException ignored) {
            }
        }
        if (fibList.get(n - 2) == 0) {
            threadPool.execute(() -> fib(n - 2));
            try {
                synchronized (Thread.currentThread()) {
                    Thread.currentThread().wait(WAIT_TIME);
                }
            } catch (InterruptedException ignored) {
            }
        }
        if (fibList.get(n) == 0) {
            Thread.currentThread().interrupt();
            fibList.set(n, fibList.get(n - 1) + fibList.get(n - 2));
        }
    }
}
