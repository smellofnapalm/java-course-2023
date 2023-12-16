package edu.hw8.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FibCalculationTest {
    static long realFib(int n) {
        long[] arr = new long[n + 1];
        arr[0] = 0L;
        arr[1] = 1L;
        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];
    }

    @Test
    @DisplayName("Проверка вычисления небольшого числа Фибоначчи")
    void calcTest1() throws InterruptedException {
        int[] ns = {10, 30, 13, 20, 5, 1};
        int length = ns.length;
        int threads = 5;
        var calc = new FibonacciCalculator(Arrays.stream(ns).max().getAsInt(), threads);
        long[] res = calc.calcMany(ns);
        boolean allMatch = IntStream.range(0, length).mapToObj(i -> res[i] == realFib(ns[i])).allMatch(f -> f);
        assertThat(allMatch).isTrue();
    }
}
