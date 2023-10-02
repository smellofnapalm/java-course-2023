package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    @DisplayName("Тест с отрицательным не палиндромом")
    void isPalindromeDescent5() {
        // given
        long x = -1213L;

        // when
        boolean pal = Task5.isPalindromeDescendant(x);

        // then
        assertThat(pal)
            .isEqualTo(false);
    }
}
