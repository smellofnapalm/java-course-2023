package edu.hw3.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BackwardIteratorTest {
    @Test
    @DisplayName("Тест с List<Integer>")
    void backwardIteratorTest1() {
        var list = List.of(1, 2, 3);
        var it = new BackwardIterator<>(list);

        assertThat(it.hasNext()).isTrue();
        assertThat(it.next()).isEqualTo(3);
        assertThat(it.next()).isEqualTo(2);
    }

    @Test
    @DisplayName("Тест с List<Queue<Integer>>")
    void backwardIteratorTest2() {
        var q1 = new LinkedList<>(List.of(1, 2));
        var q2 = new LinkedList<>(List.of(3, 4));
        var list = List.of(q1, q2);
        var it = new BackwardIterator<>(list);

        assertThat(it.hasNext()).isTrue();
        assertThat(it.next().getFirst()).isEqualTo(3);
        assertThat(it.next().getLast()).isEqualTo(2);
    }
}
