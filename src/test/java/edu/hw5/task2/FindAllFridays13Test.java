package edu.hw5.task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import static edu.hw5.task2.FindAllFridays13.findAllFridays13;
import static edu.hw5.task2.FindAllFridays13.findNextFriday13;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindAllFridays13Test {
    @Test
    @DisplayName("Тест с 1925 годом")
    void findAllFridays13Test1() {
        final int year = 1925;
        assertThat(findAllFridays13(year)).asList().contains("1925-03-13");
        assertThat(findAllFridays13(year)).asList().contains("1925-11-13");
    }

    @Test
    @DisplayName("Тест с 2999 годом")
    void findAllFridays13Test2() {
        final int year = 2999;
        assertThat(findAllFridays13(year)).asList().contains("2999-12-13");
        assertThat(findAllFridays13(year)).asList().contains("2999-09-13");
    }

    @Test
    @DisplayName("Тест с некорректным годом")
    void findAllFridays13Test3() {
        final int year = -2;
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var list = findAllFridays13(year);
        });
        assertThat(thrown).hasMessage("Год должен быть неотрицательным и не слишком большим!");
    }

    @Test
    @DisplayName("Тест с 1925-01-12")
    void findNextFriday13Test1() throws ParseException {
        var df = new SimpleDateFormat("yyyy-MM-dd");
        var date = df.parse("1925-01-12");
        assertThat(findNextFriday13(date)).isEqualTo("1925-02-13");
    }

    @Test
    @DisplayName("Тест с 2023-12-30")
    void findNextFriday13Test2() throws ParseException {
        var df = new SimpleDateFormat("yyyy-MM-dd");
        var date = df.parse("2023-12-30");
        assertThat(findNextFriday13(date)).isEqualTo("2024-09-13");
    }
}
