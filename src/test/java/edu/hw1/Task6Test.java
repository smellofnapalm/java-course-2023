package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test
    @DisplayName("Корректный тест 1")
    void countK1() {
        // given
        int x = 6621;

        // when
        int k = Task6.countK(x);

        // then
        assertThat(k)
            .isEqualTo(5);
    }

    @Test
    @DisplayName("Корректный тест 2")
    void countK2() {
        // given
        int x = 1234;

        // when
        int k = Task6.countK(x);

        // then
        assertThat(k)
            .isEqualTo(3);
    }

    @Test
    @DisplayName("Не четырехзначное число")
    void countK3() {
        // given
        int x = 12340;

        // when
        int k = Task6.countK(x);

        // then
        assertThat(k)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Число <= 1000")
    void countK4() {
        // given
        int x = 1000;

        // when
        int k = Task6.countK(x);

        // then
        assertThat(k)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Число с одинаковыми цифрами")
    void countK5() {
        // given
        int x = 6666;

        // when
        int k = Task6.countK(x);

        // then
        assertThat(k)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Первое число, которое можно брать")
    void countK6() {
        // given
        int x = 1001;

        // when
        int k = Task6.countK(x);

        // then
        assertThat(k)
            .isEqualTo(4);
    }

    @Test
    @DisplayName("Последнее число, которое можно брать")
    void countK7() {
        // given
        int x = 9998;

        // when
        int k = Task6.countK(x);

        // then
        assertThat(k)
            .isEqualTo(5);
    }
}
