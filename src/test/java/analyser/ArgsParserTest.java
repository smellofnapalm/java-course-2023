package analyser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static analyser.ArgsParser.Args.*;

public class ArgsParserTest {
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
        assertThat(dict.get(FROM)).isEqualTo(LocalDate.now());
        assertThat(dict.get(TO)).isEqualTo(LocalDate.now());
        assertThat(dict.get(PATH)).isEqualTo("C://Java");
    }
}
