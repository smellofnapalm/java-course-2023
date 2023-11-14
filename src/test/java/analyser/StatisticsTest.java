package analyser;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static analyser.ReadLogs.getStatistics;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StatisticsTest {
    final static URI TEST_URI = URI.create(
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs");
    final static OffsetDateTime FROM = OffsetDateTime.MIN;
    final static OffsetDateTime TO = OffsetDateTime.MAX;

    @Test
    @DisplayName("Тест на количество IPv6")
    void findIPv4AndIPv6Test1() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        var ips = stat.findIPv4AndIPv6();
        var ipsMap = ips.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        assertThat(ipsMap.get("IPv6")).isEqualTo(50L);
    }

    @Test
    @DisplayName("Тест на последнюю дату в логах")
    void findLatestDateTest1() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        String latestDate = stat.findLatestDate();
        assertThat(latestDate).isEqualTo("2015-06-04Z");
    }

    @Test
    @DisplayName("Тест на первую дату в логах")
    void findEarliestTest1() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        String latestDate = stat.findEarliestDate();
        assertThat(latestDate).isEqualTo("2015-05-17Z");
    }

    @Test
    @DisplayName("Тест на средний размер ответа в байтах")
    void averageResponseSizeTest1() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        double avg = stat.averageResponseSize();
        assertThat(avg).isEqualTo(659509.514398974);
    }

    @Test
    @DisplayName("Тест на общее число запросов")
    void totalNumberOfRequestsTest1() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        int total = stat.totalNumberOfRequests();
        assertThat(total).isEqualTo(51462);
    }

    @Test
    @DisplayName("Тест на поиск самых частых кодов возврата")
    void findFrequentlyUsedTest1() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        var res = stat.findFrequentlyUsed(5, LogsParser.Args.CODE);
        assertThat(res.toString()).isEqualTo("[404=33876, 304=13330, 200=4028, 206=186, 403=38]");
    }

    @Test
    @DisplayName("Тест на поиск наиболее часто используемых ресурсов")
    void findFrequentlyUsedTest2() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        var res = stat.findFrequentlyUsed(5, LogsParser.Args.PATH);
        assertThat(res.get(0).getValue().toString()).isEqualTo("30285");
    }
}
