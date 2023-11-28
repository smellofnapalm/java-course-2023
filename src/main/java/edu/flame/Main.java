package edu.flame;

import java.nio.file.Path;

public final class Main {
    @SuppressWarnings("RegexpSinglelineJava")
    public static void main(String[] args) {
        final int height = 7000;
        final int width = 7000;
        final int iterations = 100_000_000;
        final Path path = Path.of("image.bmp");
        final String format = "bmp";
        final int window = 3;
        final double gamma = 2.2;
        final double threshold = 3.0;

        FlameMaker.createImage(height, width, iterations, path, format, window, gamma, threshold);
    }

    private Main() {
    }
}
