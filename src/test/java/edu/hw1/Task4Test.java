package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Корректный тест со строкой четной длины")
    void countDigits1() {
        // given
        String s = "123456";

        // when
        String ans = Task4.fixString(s);

        // then
        assertThat(ans)
            .isEqualTo("214365");
    }

    @Test
    @DisplayName("Корректный тест со строкой нечетной длины")
    void countDigits2() {
        // given
        String s = "12345a7";

        // when
        String ans = Task4.fixString(s);

        // then
        assertThat(ans)
            .isEqualTo("2143a57");
    }

    @Test
    @DisplayName("Тест с пустой строкой")
    void countDigits3() {
        // given
        String s = "";

        // when
        String ans = Task4.fixString(s);

        // then
        assertThat(ans)
            .isEqualTo("");
    }
    @Test
    @DisplayName("Тест с одним символом")
    void countDigits4() {
        // given
        String s = "A";

        // when
        String ans = Task4.fixString(s);

        // then
        assertThat(ans)
            .isEqualTo("A");
    }

    @Test
    @DisplayName("Тест с null")
    void countDigits5() {
        // given
        String s = null;

        // when
        String ans = Task4.fixString(s);

        // then
        assertThat(ans)
            .isEqualTo(null);
    }
}
