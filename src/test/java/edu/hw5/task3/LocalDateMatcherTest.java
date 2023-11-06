package edu.hw5.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocalDateMatcherTest {
    @Test
    @DisplayName("Тест на данных из примера")
    void parseDateTest1() {
        String[] dates = {"2020-10-10", "2020-12-2", "1/3/1976", "1/3/20", "today", "tomorrow", "520 days ago"};
        for (var date : dates) {
            assertThat(LocalDateMatcher.parseDate(date)).isPresent();
        }
    }
}
