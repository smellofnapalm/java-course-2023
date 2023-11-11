package edu.hw6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import static edu.hw6.HackerNews.hackerNewsTopStories;
import static edu.hw6.HackerNews.news;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HackerNewsTest {
    @Test
    @DisplayName("Проверка получения доступа к JSON")
    void hackerNewsTest1() {
        var longs = hackerNewsTopStories();
        assertThat(longs).hasSize(500);
        assertThat(longs).containsSequence(38194102L, 38193319L);
    }

    @Test
    @DisplayName("Проверка получения доступа к новости")
    void hackerNewsTest2() {
        long id = 37570037L;
        String title = news(id);
        assertThat(title).isEqualTo("JDK 21 Release Notes");
    }

    @Test
    @DisplayName("Тест на некорректный айдишник")
    void hackerNewsTest3() {
        long id = 37570038L;
        var thrown = Assertions.assertThrows(RuntimeException.class, () -> news(id));
        assertThat(thrown).hasMessage("Название новости не было найдено!");
    }
}
