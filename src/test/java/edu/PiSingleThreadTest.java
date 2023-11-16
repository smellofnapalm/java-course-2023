package edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PiSingleThreadTest {
    @Test
    @DisplayName("Тест погрешности однопоточного приложения")
    void piSingleThreadTest1() {
        int iterations = 100_000;
        int times = 10;
        double eps = 0.01;
        double pi = PiSingleThread.calcPi(iterations);
        assertThat(pi - Math.PI).isLessThan(eps);
    }

    @Test
    @DisplayName("Тест времени однопоточного приложения")
    void piSingleThreadTest2() {
        int iterations = 100_000;
        int times = 10;
        double nano = PiSingleThread.calcTime(iterations);
        assertThat(nano).isLessThan(2.0);
    }
}
