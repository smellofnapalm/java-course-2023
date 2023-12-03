package edu.hw8.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FibCalculationTest {
    @Test
    @DisplayName("Проверка вычисления небольшого числа Фибоначчи")
    void calcTest1() throws InterruptedException {
        int[] ns = {10, 13, 20, 5};
        int threads = 5;
        var calc = new FibonacciCalculator(Arrays.stream(ns).max().getAsInt(), threads);
        long[] res = calc.calcMany(ns);
        assertThat(res[0]).isEqualTo(55L);
        assertThat(res[2]).isEqualTo(6765L);
    }
}
