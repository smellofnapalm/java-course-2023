package analyser.printer;

import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static analyser.ReadLogs.getStatistics;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ADOCPrinterTest {
    final static URI TEST_URI = URI.create(
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs");
    final static OffsetDateTime FROM = OffsetDateTime.MIN;
    final static OffsetDateTime TO = OffsetDateTime.MAX;

    @Test
    @DisplayName("Тест вывода ADOC")
    void printTest1() {
        var stat = getStatistics(TEST_URI.toString(), FROM, TO);
        assertThat(stat.getOutput("adoc")).isEqualTo("""
            .Общая информация

            |===
            |Метрика |Значение
            |Файлы
            |[https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs]
            |Начальная дата
            |2015-05-17Z
            |Конечная дата
            |2015-06-04Z
            |Количество запросов
            |51462
            |Средний размер ответа (в байтах)
            |659509.514398974
            |===

            .Запрашиваемые ресурсы

            |===
            |Ресурс |Количество
            |\\downloads\\product_1
            |30285
            |\\downloads\\product_2
            |21104
            |\\downloads\\product_3
            |73
            |===

            .Коды ответа

            |===
            |Код ответа |Количество
            |404
            |33876
            |304
            |13330
            |200
            |4028
            |206
            |186
            |403
            |38
            |===

            .IPv4 vs. IPv6

            |===
            |Версия IP |Количество
            |IPv4
            |51412
            |IPv6
            |50
            |===

            .Часы запросов по популярности

            |===
            |Промежуток времени |Количество запросов
            |20:00 -- 21:00
            |2209
            |05:00 -- 06:00
            |2182
            |22:00 -- 23:00
            |2178
            |15:00 -- 16:00
            |2178
            |23:00 -- 24:00
            |2176
            |12:00 -- 13:00
            |2174
            |13:00 -- 14:00
            |2170
            |00:00 -- 01:00
            |2168
            |18:00 -- 19:00
            |2166
            |01:00 -- 02:00
            |2163
            |09:00 -- 10:00
            |2159
            |04:00 -- 05:00
            |2157
            |14:00 -- 15:00
            |2151
            |03:00 -- 04:00
            |2145
            |02:00 -- 03:00
            |2135
            |10:00 -- 11:00
            |2131
            |19:00 -- 20:00
            |2124
            |17:00 -- 18:00
            |2120
            |16:00 -- 17:00
            |2119
            |06:00 -- 07:00
            |2116
            |21:00 -- 22:00
            |2106
            |11:00 -- 12:00
            |2102
            |08:00 -- 09:00
            |2092
            |07:00 -- 08:00
            |2041
            |===

            """);
    }
}
