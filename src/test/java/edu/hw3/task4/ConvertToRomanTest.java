package edu.hw3.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw3.task4.ConvertToRoman.convertToRoman;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConvertToRomanTest {
    @Test
    @DisplayName("Корректный тест")
    void convertToRomanTest1() {
        int x = 1235;

        String roman = convertToRoman(x);

        assertThat(roman).isEqualTo("MCCXXXV");
    }

    @Test
    @DisplayName("Граничный тест")
    void convertToRomanTest2() {
        int x = 3999;

        String roman = convertToRoman(x);

        assertThat(roman).isEqualTo("MMMCMXCIX");
    }

    @Test
    @DisplayName("Тест с единицей")
    void convertToRomanTest3() {
        int x = 1;

        String roman = convertToRoman(x);

        assertThat(roman).isEqualTo("I");
    }

    @Test
    @DisplayName("Тест с невалидным числом")
    void convertToRomanTest4() {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                int x = 4000;

                String roman = convertToRoman(x);
            }
        );

        assertThat(thrown).hasMessage("Римские числа могут быть только от 1 до 3999");
    }
}
