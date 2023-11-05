package edu.hw5.task7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MatcherFor01Test {
    @Test
    @DisplayName("Тест на первую подзадачу 1")
    void thirdSymbolIsZeroTest1() {
        String text = "001";
        assertThat(MatcherFor01.thirdSymbolIsZero(text)).isFalse();
    }

    @Test
    @DisplayName("Тест на первую подзадачу 2")
    void thirdSymbolIsZeroTest2() {
        String text = "100111";
        assertThat(MatcherFor01.thirdSymbolIsZero(text)).isTrue();
    }

    @Test
    @DisplayName("Тест на вторую подзадачу 1")
    void startsAndEndsWithSameTest1() {
        String text = "101";
        assertThat(MatcherFor01.startsAndEndsWithSame(text)).isTrue();
    }

    @Test
    @DisplayName("Тест на вторую подзадачу 2")
    void startsAndEndsWithSameTest2() {
        String text = "11110";
        assertThat(MatcherFor01.startsAndEndsWithSame(text)).isFalse();
    }

    @Test
    @DisplayName("Тест на вторую подзадачу с null")
    void startsAndEndsWithSameTest3() {
        String text = null;
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> MatcherFor01.lengthFrom1To3(text));
        assertThat(thrown).hasMessage("Это не строка из нулей и единиц!");
    }

    @Test
    @DisplayName("Тест на третью подзадачу 1")
    void lengthFrom1To3Test1() {
        String text = "11110";
        assertThat(MatcherFor01.lengthFrom1To3(text)).isFalse();
    }

    @Test
    @DisplayName("Тест на третью подзадачу 2")
    void lengthFrom1To3Test2() {
        String text = "00";
        assertThat(MatcherFor01.lengthFrom1To3(text)).isTrue();
    }

    @Test
    @DisplayName("Тест на третью подзадачу с символами не из алфавита")
    void lengthFrom1To3Test3() {
        String text = "0 ";
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> MatcherFor01.lengthFrom1To3(text));
        assertThat(thrown).hasMessage("Это не строка из нулей и единиц!");
    }
}
