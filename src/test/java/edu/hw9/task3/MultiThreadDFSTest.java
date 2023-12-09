package edu.hw9.task3;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MultiThreadDFSTest {
    final private static Graph graph = new Graph(5, List.of(
        new Graph.Edge(2, 0),
        new Graph.Edge(2, 3),
        new Graph.Edge(1, 3),
        new Graph.Edge(3, 0)
    ));

    @Test
    @DisplayName("Тест многопоточного поиска в глубину 1")
    void multiThreadDFSTest1() {
        int begin = 0;
        int end = 1;
        var path = new MultiThreadDFS(graph).findPath(begin, end);
        assertThat(path.get(0)).isEqualTo(0);
        assertThat(path.get(1)).isEqualTo(3);
    }

    @Test
    @DisplayName("Тест многопоточного поиска в глубину 2")
    void multiThreadDFSTest2() {
        int begin = 0;
        int end = 2;
        var path = new MultiThreadDFS(graph).findPath(begin, end);
        assertThat(path.get(0)).isEqualTo(0);
        assertThat(path.get(1)).isEqualTo(2);
    }

    @Test
    @DisplayName("Тест многопоточного поиска в глубину, пути нет")
    void multiThreadDFSTest3() {
        int begin = 4;
        int end = 0;
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> new MultiThreadDFS(graph).findPath(begin, end));
        assertThat(thrown).hasMessage("Между вершинами нет пути!");
    }

    @Test
    @DisplayName("Тест многопоточного поиска в глубину, вершина некорректная")
    void multiThreadDFSTest4() {
        int begin = 5;
        int end = 0;
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> new MultiThreadDFS(graph).findPath(begin, end));
        assertThat(thrown).hasMessage("Некорректные вершины графа!");
    }
}
