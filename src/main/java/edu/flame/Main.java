package edu.flame;

import java.nio.file.Path;

public final class Main {
    public static void main(String[] args) {
        Initializer init = new Initializer(Path.of("config.txt"));
        FlameMaker maker = new FlameMaker(init);
        maker.createImage();
    }

    private Main() {
    }
}
