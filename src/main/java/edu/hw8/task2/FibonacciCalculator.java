package edu.hw8.task2;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class FibonacciCalculator {
    final int n;
    final int threadCount;
    final List<Long> fibList = new Vector<>();
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final ThreadPool threadPool;
    final private static int WAIT_TIME = 100;

    FibonacciCalculator(int n, int threadCount) {
        if (n <= 0 || threadCount <= 0) {
            throw new IllegalArgumentException("Нельзя посчитать число Фибоначчи с отрицательным номером");
        }
        this.n = n;
        this.threadCount = threadCount;
        for (int i = 0; i <= n; i++) {
            fibList.add(0L);
        }
        fibList.set(1, 1L);
        fibList.set(2, 1L);
        threadPool = PoolFabric.create(threadCount);
    }

    long[] calcMany(int[] ns) throws InterruptedException {
        threadPool.start();
        for (final int k : ns) {
            threadPool.execute(() -> fib(k));
        }
        Thread.sleep(WAIT_TIME);
        return Arrays.stream(ns).mapToLong(fibList::get).toArray();
    }

    private long fib(int n) {
        if (n <= 2) {
            if (n == 0) {
                return 0;
            } else {
                return 1;
            }
        }
        final long that;
        long left;
        long right;
        try {
            lock.readLock().lock();
            that = fibList.get(n);
            left = fibList.get(n - 2);
            right = fibList.get(n - 1);
        } finally {
            lock.readLock().unlock();
        }
        if (that != 0) {
            return that;
        }
        if (right == 0) {
            right = fib(n - 1);
        }
        if (left == 0) {
            left = fib(n - 2);
        }
        try {
            lock.writeLock().lock();
            fibList.set(n, left + right);
        } finally {
            lock.writeLock().unlock();
        }
        return left + right;
    }
}
