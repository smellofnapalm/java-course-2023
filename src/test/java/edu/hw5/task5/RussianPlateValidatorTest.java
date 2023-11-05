package edu.hw5.task5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.task5.RussianPlateValidator.validatePlate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RussianPlateValidatorTest {
    @Test
    @DisplayName("Тест на корректных номерах 1")
    void validatePlateTest1() {
        String carNumber = "А123ВЕ777";
        assertThat(validatePlate(carNumber)).isTrue();
    }

    @Test
    @DisplayName("Тест на корректных номерах 2")
    void validatePlateTest2() {
        String carNumber = "О777ОО177";
        assertThat(validatePlate(carNumber)).isTrue();
    }

    @Test
    @DisplayName("Тест на некорректных номерах 1")
    void validatePlateTest3() {
        String carNumber = "123АВЕ777";
        assertThat(validatePlate(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест на некорректных номерах 2")
    void validatePlateTest4() {
        String carNumber = "А123ВГ77";
        assertThat(validatePlate(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест на некорректных номерах 3")
    void validatePlateTest5() {
        String carNumber = "А123ВЕ7777";
        assertThat(validatePlate(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест с null")
    void validatePlateTest6() {
        var thrown = Assertions.assertThrows(NullPointerException.class, () -> validatePlate(null));
        assertThat(thrown).hasMessage("Номера машины не могут быть null!");
    }
}
