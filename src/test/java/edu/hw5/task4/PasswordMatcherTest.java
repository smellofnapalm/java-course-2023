package edu.hw5.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.task4.PasswordMatcher.passwordMatches;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PasswordMatcherTest {
    @Test
    @DisplayName("Тест корректного пароля")
    void passwordMatchesTest1() {
        String password = "ab~oba";
        assertThat(passwordMatches(password)).isTrue();
    }

    @Test
    @DisplayName("Тест неподходящего пароля")
    void passwordMatchesTest2() {
        String password = "pass word";
        assertThat(passwordMatches(password)).isFalse();
    }

    @Test
    @DisplayName("Тест c null")
    void passwordMatchesTest3() {
        String password = null;
        var thrown = Assertions.assertThrows(NullPointerException.class, () -> passwordMatches(password));
        assertThat(thrown).hasMessage("Пароль не может быть null!");
    }

    @Test
    @DisplayName("Тест с пустым паролем")
    void passwordMatchesTest4() {
        String password = "";
        assertThat(passwordMatches(password)).isFalse();
    }
}
