package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import edu.hw1.Task1;

public class Task1Test {
    @Test
    @DisplayName("Корректный тест")
    void minutesToSecondsTest1() {
        // given
        String s = "01:00";

        // when
        long sec = Task1.minutesToSeconds(s);

        // then
        assertThat(sec)
            .isEqualTo(60);
    }

    @Test
    @DisplayName("Большой тест")
    void minutesToSecondsTest2() {
        // given
        String s = "999:59";

        // when
        long sec = Task1.minutesToSeconds(s);

        // then
        assertThat(sec)
            .isEqualTo(59999);
    }

    @Test
    @DisplayName("Некорректный тест")
    void minutesToSecondsTest3() {
        // given
        String s = "23:61";

        // when
        long sec = Task1.minutesToSeconds(s);

        // then
        assertThat(sec)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Отрицательный тест")
    void minutesToSecondsTest4() {
        // given
        String s = "-23:01";

        // when
        long sec = Task1.minutesToSeconds(s);

        // then
        assertThat(sec)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Тест на 60 секунд")
    void minutesToSecondsTest5() {
        // given
        String s = "213:60";

        // when
        long sec = Task1.minutesToSeconds(s);

        // then
        assertThat(sec)
            .isEqualTo(-1);
    }
}
