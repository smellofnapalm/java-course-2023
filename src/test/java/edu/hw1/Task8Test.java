package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task8Test {
    @Test
    @DisplayName("Тест из примера 1")
    void knightBoardCapture1() {
        // given
        int[][] a = {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };

        // when
        boolean ans = Task8.knightBoardCapture(a);

        // then
        assertThat(ans)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Тест из примера 2")
    void knightBoardCapture2() {
        // given
        int[][] a = {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };

        // when
        boolean ans = Task8.knightBoardCapture(a);

        // then
        assertThat(ans)
            .isEqualTo(false);
    }

    @Test
    @DisplayName("Тест с null")
    void knightBoardCapture3() {
        // given
        int[][] a = null;

        // when
        boolean ans = Task8.knightBoardCapture(a);

        // then
        assertThat(ans)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Тест с конями во всех клетках")
    void knightBoardCapture4() {
        // given
        int[][] a = {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        };

        // when
        boolean ans = Task8.knightBoardCapture(a);

        // then
        assertThat(ans)
            .isEqualTo(false);
    }

    @Test
    @DisplayName("Тест без коней")
    void knightBoardCapture5() {
        // given
        int[][] a = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // when
        boolean ans = Task8.knightBoardCapture(a);

        // then
        assertThat(ans)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Тест с одним конем")
    void knightBoardCapture7() {
        // given
        int[][] a = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // when
        boolean ans = Task8.knightBoardCapture(a);

        // then
        assertThat(ans)
            .isEqualTo(true);
    }

    @Test
    @DisplayName("Тест с маленьким полем")
    void knightBoardCapture8() {
        // given
        int[][] a = {
            {1, 0, 0},
            {0, 0, 1}
        };

        // when
        boolean ans = Task8.knightBoardCapture(a);

        // then
        assertThat(ans)
            .isEqualTo(false);
    }
}
