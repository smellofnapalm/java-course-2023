package edu.hw7.task1;

import java.util.concurrent.atomic.AtomicInteger;

class ParallelCounter {
    private final AtomicInteger c;

    ParallelCounter(int initial) {
        c = new AtomicInteger(initial);
    }

    public void increment() {
        c.incrementAndGet();
    }

    public void decrement() {
        c.decrementAndGet();
    }

    public int value() {
        return c.get();
    }

}
