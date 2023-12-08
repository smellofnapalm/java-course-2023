package analyser.printer;

import analyser.reader.URIReader;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ADOCPrinterTest {
    final static URI TEST_URI = URI.create(
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs");
    final static OffsetDateTime FROM = OffsetDateTime.MIN;
    final static OffsetDateTime TO = OffsetDateTime.MAX;

    @Test
    @DisplayName("Тест вывода ADOC")
    void printTest1() {
        var stat = new URIReader().getStatistics(TEST_URI.toString(), FROM, TO);
        assertThat(stat.getOutput("adoc").split("\n")[0]).isEqualTo(".Общая информация");
    }
}
