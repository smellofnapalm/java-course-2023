package analyser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static analyser.LogsParser.logToGroups;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogsParserTest {
    @Test
    @DisplayName("Тест парсера логов")
    void logsParserTest1() {
        final String example = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" "
            + "304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
        var dict = logToGroups(example);

        assertThat(dict.get("datetime")).isEqualTo("17/May/2015:08:05:32 +0000");
    }

    @Test
    @DisplayName("Тест c неправильной строкой")
    void logsParserTest2() {
        final String example = "93.180.71.3";
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> logToGroups(example));
        assertThat(thrown).hasMessage("Логи не соответствуют паттерну!");
    }
}
