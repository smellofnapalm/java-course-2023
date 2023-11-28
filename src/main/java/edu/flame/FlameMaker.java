package edu.flame;

import java.nio.file.Path;

public class FlameMaker {
    public static void createImage(
        int height,
        int width,
        int iterations,
        Path path,
        String format,
        int window,
        double gamma,
        double threshold
    ) {
        final double toSeconds = 1e-9;
        var start = System.nanoTime();
        var gen = new ImageGenerator(height, width);
        gen.generateImage(iterations, threshold);
        var writer = new ImageWriter(path, format, gen.res, window, gamma);
        writer.writeImage();
        var end = System.nanoTime();
        System.out.printf("Время работы -- %f\n", (end - start) * toSeconds);
    }
}
