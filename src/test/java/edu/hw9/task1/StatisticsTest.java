package edu.hw9.task1;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StatisticsTest {
    final static List<String> STAT_NAMES = List.of(
        "MEAN",
        "MAX",
        "MIN",
        "SUM"
    );

    @Test
    @DisplayName("Тестирование добавления и агрегирования статистики")
    void statisticsTest1() {
        final StatisticsCollector collector = new StatisticsCollector();
        final int size = 10;
        final int amount = 100_000;
        List<double[]> listOfData =
            Stream.generate(() -> ThreadLocalRandom.current().doubles().limit(size).toArray()).limit(amount).toList();
        listOfData.stream().parallel().forEach(arr -> collector.push(STAT_NAMES, arr));

        listOfData.stream().parallel().map(collector::get).forEach(stats -> {
            assertThat(stats.min()).isLessThanOrEqualTo(stats.max());
            assertThat(stats.sum()).isCloseTo(stats.mean() * size, Percentage.withPercentage(0.1));
            assertThat(stats.max()).isNotNaN();
            assertThat(stats.min()).isNotNaN();
            assertThat(stats.sum()).isNotNaN();
            assertThat(stats.mean()).isNotNaN();
        });
    }

    @Test
    @DisplayName("Тестирование добавления и агрегирования статистики для нескольких вручную заданных данных")
    void statisticsTest2() {
        final StatisticsCollector collector = new StatisticsCollector();
        List<double[]> listOfData = List.of(new double[] {1.0, 2.0, 3.0}, new double[] {0.5, -0.5}, new double[] {0.0});
        listOfData.forEach(arr -> collector.push(STAT_NAMES, arr));
        var stats0 = collector.get(listOfData.get(0));
        assertThat(stats0.max()).isEqualTo(3.0);
        assertThat(stats0.min()).isEqualTo(1.0);
        assertThat(stats0.sum()).isEqualTo(3 * stats0.mean());
    }
}
