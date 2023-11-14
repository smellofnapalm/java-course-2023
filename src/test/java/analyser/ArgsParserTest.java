package analyser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static analyser.ArgsParser.Args.FROM;
import static analyser.ArgsParser.Args.PATH;
import static analyser.ArgsParser.Args.TO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArgsParserTest {
    final static OffsetDateTime DATE = OffsetDateTime.of(
        LocalDate.of(2023, 8, 3), LocalTime.MIDNIGHT, ZoneOffset.UTC);

    @Test
    @DisplayName("Тест парсера -- не передан путь")
    void parserTest1() {
        final String[] args = {"--from", "2023-05-05"};
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> ArgsParser.parseArgs(args));

        assertThat(thrown).hasMessage("Не передан обязательный аргумент -- путь к логам!");
    }

    @Test
    @DisplayName("Тест парсера -- нет аргументов")
    void parserTest2() {
        final String[] args = {};
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> ArgsParser.parseArgs(args));

        assertThat(thrown).hasMessage("Не передано аргументов командной строки!");
    }

    @Test
    @DisplayName("Тест парсера -- передан только путь к файлу")
    void parserTest3() {
        final String[] args = {"--path", "C://Java"};
        var dict = ArgsParser.parseArgs(args);

        assertThat(dict.containsKey(FROM)).isTrue();
        assertThat(dict.get(FROM)).isEqualTo(OffsetDateTime.MIN);
        assertThat(dict.get(TO)).isEqualTo(OffsetDateTime.MAX);
        assertThat(dict.get(PATH)).isEqualTo("C://Java");
    }

    @Test
    @DisplayName("Тест парсера -- передан путь к файлу и дата отсчета")
    void parserTest4() {
        final String[] args = {"--path", "C://Java", "--from", "2023-08-03"};
        var dict = ArgsParser.parseArgs(args);

        assertThat(dict.containsKey(FROM)).isTrue();
        assertThat(dict.get(FROM)).isEqualTo(DATE);
        assertThat(dict.get(TO)).isEqualTo(OffsetDateTime.MAX);
        assertThat(dict.get(PATH)).isEqualTo("C://Java");
    }
}
