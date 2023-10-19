package edu.hw3.task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw3.task2.Clusterize.clusterize;
import static edu.hw3.task2.Clusterize.isBracketSequence;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClusterizeTest {
    @Test
    @DisplayName("Тест с неправильной скобочной последовательностью")
    void isBracketSequenceTest1() {
        String s = "((())()";

        boolean flag = isBracketSequence(s);

        assertThat(flag).isFalse();
    }

    @Test
    @DisplayName("Тест с правильной скобочной последовательностью")
    void isBracketSequenceTest2() {
        String s = "((()))()";

        boolean flag = isBracketSequence(s);

        assertThat(flag).isTrue();
    }

    @Test
    @DisplayName("Тест кластеризации с ПСП")
    void clusterizeTest1() {
        String s = "((()))()";

        String[] clusters = clusterize(s);

        assertThat(clusters[0]).isEqualTo("((()))");
        assertThat(clusters[1]).isEqualTo("()");
    }

    @Test
    @DisplayName("Тест кластеризации с неправильной строкой на входе")
    void clusterizeTest3() {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                String s = "((a+b)";
                String[] clusters = clusterize(s);
            }
        );

        assertThat(thrown).hasMessage("Строка не является (правильной) скобочной последовательностью");
    }

    @Test
    @DisplayName("Тест кластеризации с пустой строкой на входе")
    void clusterizeTest4() {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                String s = "";
                String[] clusters = clusterize(s);
            }
        );

        assertThat(thrown).hasMessage("Строка не является (правильной) скобочной последовательностью");
    }
}
