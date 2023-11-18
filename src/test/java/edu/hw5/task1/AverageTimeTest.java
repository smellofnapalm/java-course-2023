package edu.hw5.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static edu.hw5.task1.AverageTime.averageTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AverageTimeTest {
    @Test
    @DisplayName("Авторский тест")
    void averageTimeTest1() {
        var dates = List.of("2022-03-12, 20:20 - 2022-03-12, 23:50", "2022-04-01, 21:30 - 2022-04-02, 01:20");
        assertThat(averageTime(dates)).isEqualTo("0д 3ч 40м 0с");
    }

    @Test
    @DisplayName("Тест с пустым списком")
    void averageTimeTest2() {
        List<String> dates = List.of();
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var avg = averageTime(dates);
        });
        assertThat(thrown).hasMessage("Размер списка строк должен быть ненулевым!");
    }

    @Test
    @DisplayName("Тест с некорректным временем")
    void averageTimeTest3() {
        List<String> dates = List.of("2022-03-12, 20:20 - 2022-03-12, 312:50");
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var avg = averageTime(dates);
        });
        assertThat(thrown).hasMessage("Строки должны быть в нужном формате!");
    }
}
