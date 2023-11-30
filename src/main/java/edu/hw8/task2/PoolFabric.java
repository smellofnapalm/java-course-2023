package edu.hw8.task2;

public final class PoolFabric {
    public static FixedThreadPool create(int threadCount) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("Не удается выделить столько потоков!");
        }
        return new FixedThreadPool(threadCount);
    }

    private PoolFabric() {
    }
}
