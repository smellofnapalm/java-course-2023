package edu.flame;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.abs;

public class ImageGenerator {
    final int height;
    final int width;
    int threadCount = Runtime.getRuntime().availableProcessors();
    final List<Thread> threads = new ArrayList<>();
    final private static Color BLACK = new Color(0, 0, 0);

    final Cell[][] res;

    ImageGenerator(int height, int width) {
        this.height = height;
        this.width = width;
        this.res = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res[i][j] = new Cell(BLACK);
            }
        }
    }

    void generateImage(int iterations, double threshold) {
        int iterPerThread = iterations / threadCount;
        for (int th = 0; th < threadCount; th++) {
            FlameFunctions flameFunctions = new FlameFunctions();
            Thread thread = new Thread(() -> doIterations(threshold, iterPerThread, flameFunctions));
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        });
    }

    private void doIterations(double threshold, int iterPerThread, FlameFunctions flameFunctions) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final double h = random.nextDouble(-1.0, 1.0);
        final double w = random.nextDouble(-1.0, 1.0);
        final int r = random.nextInt(256);
        final int g = random.nextInt(256);
        final int b = random.nextInt(256);
        PointWithColor p = new PointWithColor(new Point2D.Double(h, w), new Color(r, g, b));
        for (int i = 0; i < iterPerThread; i++) {
            p = flameFunctions.functionList.get(getRandomFlameFunction(random, flameFunctions)).apply(p);
            if (abs(p.point.getX()) >= threshold || abs(p.point.getY()) >= threshold) {
                continue;
            }
            int x = (int) (Math.floor((p.point.getX() / threshold) * height / 2.0) + height / 2.0);
            int y = (int) (Math.floor((p.point.getY() / threshold) * width / 2.0) + width / 2.0);
            synchronized (res[x][y]) {
                res[x][y].count++;
                res[x][y].color = p.color;
            }
        }
    }

    private int getRandomFlameFunction(ThreadLocalRandom random, FlameFunctions flameFunctions) {
        final int n = flameFunctions.functionList.size();
        double r = random.nextDouble();
        for (int i = 0; i < n - 1; i++) {
            r -= flameFunctions.probabilities.get(i);
            if (r < 0) {
                return i;
            }
        }
        return n - 1;
    }

}
