package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Корректный тест")
    void countDigits1() {
        // given
        long num = 1231234560L;

        // when
        int count = Task2.countDigits(num);

        // then
        assertThat(count)
            .isEqualTo(10);
    }

    @Test
    @DisplayName("Тест с нулем")
    void countDigits2() {
        // given
        long num = 0;

        // when
        int count = Task2.countDigits(num);

        // then
        assertThat(count)
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Тест с большим отрицательным числом")
    void countDigits3() {
        // given
        long num = -9080706050403L;

        // when
        int count = Task2.countDigits(num);

        // then
        assertThat(count)
            .isEqualTo(13);
    }
}
