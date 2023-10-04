package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Корректный тест на true")
    void countDigits1() {
        // given
        long[] a1 = {-1, 0, 1, 1};
        long[] a2 = {-2, 2};

        // when
        boolean nestable = Task3.isNestable(a1, a2);

        // then
        assertThat(nestable)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Корректный тест на false")
    void countDigits2() {
        // given
        long[] a1 = {-1, 0, 1, 1};
        long[] a2 = {-1, 2};

        // when
        boolean nestable = Task3.isNestable(a1, a2);

        // then
        assertThat(nestable)
            .isEqualTo(false);
    }

    @Test
    @DisplayName("Тест с пустым множеством 1")
    void countDigits3() {
        // given
        long[] a1 = {};
        long[] a2 = {-1, 2};

        // when
        boolean nestable = Task3.isNestable(a1, a2);

        // then
        assertThat(nestable)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Тест с пустым множеством 2")
    void countDigits4() {
        // given
        long[] a1 = {-1, 2};
        long[] a2 = {};

        // when
        boolean nestable = Task3.isNestable(a1, a2);

        // then
        assertThat(nestable)
            .isEqualTo(false);
    }

    @Test
    @DisplayName("Тест с null")
    void countDigits5() {
        // given
        long[] a1 = null;
        long[] a2 = {};

        // when
        boolean nestable = Task3.isNestable(a1, a2);

        // then
        assertThat(nestable)
            .isEqualTo(true);
    }
}
