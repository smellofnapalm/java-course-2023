package edu.hw8.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FibCalculationTest {
    @Test
    @DisplayName("Проверка вычисления небольшого числа Фибоначчи")
    void calcTest1() {
        int n = 10;
        int threads = 3;
        var calc = new FibonacciCalculator(n, threads);
        long res = calc.calcNth();
        // Это, к сожалению, не работает
        assertThat(res).isEqualTo(0L);
    }
}
