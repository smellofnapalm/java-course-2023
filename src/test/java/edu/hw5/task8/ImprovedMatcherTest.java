package edu.hw5.task8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ImprovedMatcherTest {
    @Test
    @DisplayName("Тест функции пропускания нечетной длины")
    void hasOddLengthTest() {
        String text = "01001";
        assertThat(ImprovedMatcher01.hasOddLength(text)).isTrue();
    }

    @Test
    @DisplayName("Тест функции начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину")
    void zeroOddOrOneEvenTest() {
        String text = "1101";
        assertThat(ImprovedMatcher01.zeroOddOrOneEven(text)).isTrue();
    }

    @Test
    @DisplayName("Тест функции количество 0 кратно 3")
    void has3kOfZeroesTest() {
        String text = "011110101";
        assertThat(ImprovedMatcher01.has3kOfZeroes(text)).isTrue();
    }

    @Test
    @DisplayName("Тест функции любая строка, кроме 11 или 111")
    void except11Or111Test() {
        String text = "111";
        assertThat(ImprovedMatcher01.except11Or111(text)).isFalse();
    }

    @Test
    @DisplayName("Тест функции количество 0 кратно 3")
    void everyOddIsOneTest() {
        String text = "10111";
        assertThat(ImprovedMatcher01.everyOddIsOne(text)).isTrue();
    }

    @Test
    @DisplayName("Тест функции содержит не менее двух 0 и не более одной 1")
    void hasTwoOrMoreZeroesAndNotMoreThanOneOneTest() {
        String text = "10";
        assertThat(ImprovedMatcher01.hasTwoOrMoreZeroesAndNotMoreThanOneOne(text)).isFalse();
    }

    @Test
    @DisplayName("Тест функции нет последовательных 1")
    void noConsecutiveOnesTest() {
        String text = "101";
        assertThat(ImprovedMatcher01.noConsecutiveOnes(text)).isTrue();
    }

    @Test
    @DisplayName("Тест с неправильным алфавитом")
    void verifyTextTest() {
        String text = "012";
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> ImprovedMatcher01.noConsecutiveOnes(text));
        assertThat(thrown).hasMessage("Это не строка из нулей и единиц!");
    }
}
