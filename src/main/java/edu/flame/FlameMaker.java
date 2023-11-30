package edu.flame;

import java.util.logging.Logger;

public class FlameMaker {
    private final Initializer initializer;

    public void createImage() {
        final double toSeconds = 1e-9;
        var start = System.nanoTime();
        var gen = new ImageGenerator(initializer.height, initializer.width);
        gen.generateImage(initializer.iterations, initializer.threshold, initializer.flameFunctions);
        var writer = new ImageWriter(initializer.pathToFile, initializer.format, gen.res,
            initializer.window, initializer.gamma
        );
        writer.writeImage();
        var end = System.nanoTime();
        LOGGER.info("Время работы -- %f\n".formatted((end - start) * toSeconds));
    }

    private final static Logger LOGGER = Logger.getGlobal();

    FlameMaker(Initializer init) {
        this.initializer = init;
    }
}
