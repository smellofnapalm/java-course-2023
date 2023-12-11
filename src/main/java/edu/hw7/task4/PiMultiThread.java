package edu.hw7.task4;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class PiMultiThread {
    private static final double FACTOR = 4.0;

    static double calcPi(int iterations, int threadCount) throws InterruptedException {
        List<Thread> pool = new ArrayList<>();
        final int iterForThread = iterations / threadCount;
        final int moreItersToAdd = iterations % threadCount;
        AtomicInteger pointsInCircle = new AtomicInteger(0);
        for (int i = 0; i < threadCount; i++) {
            final int iters = iterForThread + (i < moreItersToAdd ? 1 : 0);
            pool.add(new Thread(() -> {
                int points = 0;
                final SecureRandom RANDOM = new SecureRandom();
                for (int k = 0; k < iters; k++) {
                    final Point2D p = new Point2D(RANDOM.nextDouble(), RANDOM.nextDouble());
                    if (p.inCircle()) {
                        points++;
                    }
                }
                pointsInCircle.addAndGet(points);
            }));
            pool.get(pool.size() - 1).start();
        }
        for (var thread : pool) {
            thread.join();
        }
        final double factor = 4.0;
        return (factor * pointsInCircle.get()) / iterations;
    }

    static double calcTime(int iterations, int threadCount) throws InterruptedException {
        final double toSeconds = 1e-9;
        var start = System.nanoTime();
        var pi = calcPi(iterations, threadCount);
        var end = System.nanoTime();
        return toSeconds * (end - start);
    }

    static double calcError(int iterations, int threadCount) throws InterruptedException {
        var pi = calcPi(iterations, threadCount);
        return Math.abs(pi - Math.PI);
    }

    private PiMultiThread() {
    }
}
