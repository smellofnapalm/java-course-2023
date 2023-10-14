package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static java.lang.Math.abs;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @Test
    @DisplayName("Корректный тест со строкой четной длины 1")
    void isPalindromeDescent1() {
        // given
        long x = 11211230L;

        // when
        boolean pal = Task5.isPalindromeDescendant(x);

        // then
        assertThat(pal)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Корректный тест со строкой четной длины 2")
    void isPalindromeDescent2() {
        // given
        long x = 23336014L;

        // when
        boolean pal = Task5.isPalindromeDescendant(x);

        // then
        assertThat(pal)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Тест со строкой длины 1")
    void isPalindromeDescent3() {
        // given
        long x = 1L;

        // when
        boolean pal = Task5.isPalindromeDescendant(x);

        // then
        assertThat(pal)
            .isEqualTo(false);
    }

    @Test
    @DisplayName("Тест со строкой нечетной длины")
    void isPalindromeDescent4() {
        // given
        long x = 121L;

        // when
        boolean pal = Task5.isPalindromeDescendant(x);

        // then
        assertThat(pal)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Тест с отрицательным числом")
    void isPalindromeDescent5() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> {
                // given
                long x = -1213L;

                // when
                boolean pal = Task5.isPalindromeDescendant(x);
            }
        );

        // then
        assertThat(thrown.getMessage())
            .isEqualTo("Аргумент должен быть >= 0");
    }

    @Test
    @DisplayName("Тест 9999 -> 1818 -> 99")
    void isPalindromeDescent6() {
        // given
        long x = 9999L;

        // when
        boolean pal = Task5.isPalindromeDescendant(x);
        String next = Task5.makeStep(String.valueOf(x));

        // then
        assertThat(pal)
            .isEqualTo(true);
        assertThat(next)
            .isEqualTo("1818");
    }

    @Test
    @DisplayName("Тест с нулем")
    void isPalindromeDescent7() {
        // given
        long x = 0L;

        // when
        boolean pal = Task5.isPalindromeDescendant(x);

        // then
        assertThat(pal)
            .isEqualTo(false);
    }
}
