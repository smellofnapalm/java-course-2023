package edu.hw7.task4;

import java.security.SecureRandom;

public final class PiSingleThread {
    private static final SecureRandom RANDOM = new SecureRandom();

    static double calcPi(int iterations) {
        int in = 0;
        final double factor = 4.0;
        for (int i = 0; i < iterations; i++) {
            var p = new Point2D(RANDOM.nextDouble(), RANDOM.nextDouble());
            if (p.inCircle()) {
                in++;
            }
        }
        return (factor * in) / iterations;
    }

    static double calcTime(int iterations) {
        final double toSeconds = 1e-9;
        var start = System.nanoTime();
        var pi = calcPi(iterations);
        var end = System.nanoTime();
        return toSeconds * (end - start);
    }

    static double calcError(int iterations) {
        var pi = calcPi(iterations);
        return Math.abs(pi - Math.PI);
    }

    private PiSingleThread() {
    }
}
