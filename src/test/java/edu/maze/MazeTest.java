package edu.maze;

import edu.maze.generators.EllerGenerator;
import edu.maze.renderers.ASCIIRenderer;
import edu.maze.solvers.BFSSolver;
import edu.maze.solvers.DFSSolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MazeTest {
    static Maze MAZE = new Maze(2, 2, new Cell[][] {
        {new Cell(0, 0, true, false), new Cell(0, 1, true, false)},
        {new Cell(1, 0, false, true), new Cell(1, 1, true, true)}
    });

    @Test
    @DisplayName("Тест генератора Эллера")
    void ellerGeneratorTest() {
        int width = 3;
        int height = 3;
        double probability = 0.4;

        var gen = new EllerGenerator(probability);
        var maze = gen.generate(height, width);
        var path = new BFSSolver().solve(maze, new Coordinate(0, 0), new Coordinate(0, 0));

        assertThat(path.size()).isEqualTo(1);
        assertThat(new Coordinate(0, 0)).isIn(path);
    }

    @Test
    @DisplayName("Тест поиска пути при помощи DFS")
    void DFSTest() {
        int width = 10;
        int height = 5;
        double probability = 0.3;

        var gen = new EllerGenerator(probability);
        var maze = gen.generate(height, width);

        Coordinate start = new Coordinate(0,0);
        Coordinate end = new Coordinate(height-1, width-1);
        var path = new DFSSolver().solve(maze, start, end);

        assertThat(path).asList().contains(start);
        assertThat(path).asList().contains(end);
    }

    @Test
    @DisplayName("Тест отрисовки на уже созданном лабиринте 2х2")
    void knownMazeTest() {
        var start = new Coordinate(0, 1);
        var end = new Coordinate(0, 0);
        var path = new DFSSolver().solve(MAZE, start, end);

        final String pathRendered = "+---+---+\n" +
            "| e | s |\n" +
            "+   +   +\n" +
            "| *   * |\n" +
            "+---+---+\n";

        final String mazeRendered = "+---+---+\n" +
            "|   |   |\n" +
            "+   +   +\n" +
            "|       |\n" +
            "+---+---+\n";

        assertThat(new ASCIIRenderer().render(MAZE, path)).isEqualTo(pathRendered);
        assertThat(new ASCIIRenderer().render(MAZE)).isEqualTo(mazeRendered);
    }

    @Test
    @DisplayName("Тест на поиск некорректного пути")
    void badPathTest() {
        int width = 10;
        int height = 5;
        double probability = 0.3;
        var gen = new EllerGenerator(probability);
        var maze = gen.generate(height, width);
        var start = new Coordinate(0, 0);
        var end = new Coordinate(height+1, width+1);

        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var path = new DFSSolver().solve(maze, start, end);
        });

        assertThat(thrown).hasMessage("Координаты start или end не принадлежат лабиринту (или лабиринт null)");
    }

    @Test
    @DisplayName("Тест на некорректные параметры при генерации (нулевая ширина)")
    void badMazeTest1() {
        int width = 0;
        int height = 5;
        double probability = 0.3;
        var gen = new EllerGenerator(probability);

        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var maze = gen.generate(height, width);
        });

        assertThat(thrown).hasMessage("Ширина и высота лабиринта должны быть положительными!");
    }

    @Test
    @DisplayName("Тест на некорректные параметры при генерации (неправильная вероятность появление стенки)")
    void badMazeTest2() {
        int width = 10;
        int height = 5;
        double probability = 1.1;

        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var gen = new EllerGenerator(probability);
        });

        assertThat(thrown).hasMessage("Вероятность создания стенки должна лежать в диапазоне [0,1]!");
    }
}
