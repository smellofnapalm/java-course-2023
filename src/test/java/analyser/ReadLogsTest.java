package analyser;

import analyser.reader.LocalReader;
import analyser.reader.URIReader;
import java.net.URI;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReadLogsTest {

    final static Path TEST_PATH1 = Path.of(".", "src", "logs", "2023logs", "logs.txt");
    final static Path TEST_PATH2 = Path.of(".", "src", "logs", "2023logii.txt");
    final static OffsetDateTime FROM = OffsetDateTime.MIN;
    final static OffsetDateTime TO = OffsetDateTime.MAX;
    final static URI TEST_URI = URI.create(
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs");

    @Test
    @DisplayName("Тест на считывание тестовых логов из ./src/logs 1")
    void readLogsTest1() {
        String pattern = "src/logs/2023**";
        var paths = new LocalReader().findMatchingFiles(pattern);
        assertThat(paths).asList()
            .contains(TEST_PATH1);
        assertThat(paths).asList()
            .contains(TEST_PATH2);
    }

    @Test
    @DisplayName("Тест на пустой match")
    void readLogsTest2() {
        String pattern = "src/2023**";
        assertThat(new LocalReader().findMatchingFiles(pattern)).asList().isEmpty();
    }

    @Test
    @DisplayName("Тест на считывание из файла")
    void readOneFileTest1() {
        var list = new LocalReader().readOneFile(String.valueOf(TEST_PATH1), FROM, TO);
        int size = list.size();
        assertThat(list.get(0).ip()).isEqualTo("93.180.71.3");
        assertThat(list.get(size - 1).sent()).isEqualTo(1768L);
    }

    @Test
    @DisplayName("Тест на считывание из некорректного файла")
    void readOneFileTest2() {
        Path badPath = Path.of(".", "l2o1g3s.txt");
        var thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new LocalReader().readOneFile(String.valueOf(badPath), FROM, TO)
        );
        assertThat(thrown).hasMessage("Путь к логам задан некорректно!");
    }

    @Test
    @DisplayName("Тест на считывание тестовых логов из ./src/logs 2")
    void readAllFilesGlobTest1() {
        String pattern = "src/logs/2023**";
        LocalReader reader = new LocalReader();
        var paths = reader.findMatchingFiles(pattern);
        var allLogs = reader.readAllFilesGlob(paths, FROM, TO);
        assertThat(allLogs).asList().hasSize(51462);
    }

    @Test
    @DisplayName("Тест на считывание тестовых логов по URL")
    void readOneFileFromURLTest1() {
        var allLogs = new URIReader().readOneFile(String.valueOf(TEST_URI), FROM, TO);
        assertThat(allLogs).asList().hasSize(51462);
    }

    @Test
    @DisplayName("Тест на считывание при помощи функции getStatistics")
    void getStatisticsTest1() {
        var allLogs = new URIReader().getStatistics(TEST_URI.toString(), FROM, TO);
        assertThat(allLogs.data).asList().hasSize(51462);
    }
}
