package edu.maze;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EllerGeneratorTest {
    @Test
    @DisplayName("Тест генератора Эллера")
    void ellerGeneratorTest() {
        int width = 3;
        int height = 3;
        double probability = 0.0;

        var gen = new EllerGenerator(probability);
        var maze = gen.generate(height, width);

        var path = new BFSSolver().solve(maze, new Coordinate(0, 0), new Coordinate(0, 0));

        System.out.println(new ConsoleRenderer().render(maze, path));
    }
}
