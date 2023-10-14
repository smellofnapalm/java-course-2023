package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Корректный тест сдвига налево")
    void rotateLeft1() {
        // given
        int x = 17;
        int shift = 2;

        // when
        int k = Task7.rotateLeft(x, shift);

        // then
        assertThat(k)
            .isEqualTo(6);
    }

    @Test
    @DisplayName("Сдвиг на длину, большую чем длина числа")
    void rotateLeft2() {
        // given
        int x = 17;
        int shift = 6;

        // when
        int k = Task7.rotateLeft(x, shift);

        // then
        assertThat(k)
            .isEqualTo(3);
    }

    @Test
    @DisplayName("Корректный тест сдвига направо")
    void rotateRight1() {
        // given
        int x = 8;
        int shift = 1;

        // when
        int k = Task7.rotateRight(x, shift);

        // then
        assertThat(k)
            .isEqualTo(4);
    }

    @Test
    @DisplayName("Сдвиг нуля")
    void rotateRight2() {
        // given
        int x = 0;
        int shift = 2;

        // when
        int k = Task7.rotateRight(x, shift);

        // then
        assertThat(k)
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Возврат в исходное число")
    void rotateRight3() {
        // given
        int x = 8;
        int shift = 4;

        // when
        int k = Task7.rotateRight(x, shift);

        // then
        assertThat(k)
            .isEqualTo(8);
    }

    @Test
    @DisplayName("Попробуем отрицательное число")
    void rotateRight4() {
        // given
        int x = -8;
        int shift = 1;

        // when
        int k = Task7.rotateRight(x, shift);

        // then
        assertThat(k)
            .isEqualTo(-1);
    }
}
