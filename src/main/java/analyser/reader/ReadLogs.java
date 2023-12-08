package analyser.reader;

import analyser.LogsParser;
import analyser.ParsedLogContainer;
import analyser.Statistics;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Класс, который позволяет по URI/glob паттерну получить все логи в типизированном представлении
 * Основной метод для внешнего взаимодействия
 * <p> {@link #getStatistics(String uriOrPattern, OffsetDateTime from, OffsetDateTime to)} </p>
 */
public interface ReadLogs {

    int MAX_DEPTH = 50;
    Path DIR = Path.of(".");

    List<ParsedLogContainer> readOneFile(String pathOrUri, OffsetDateTime from, OffsetDateTime to);

    static List<ParsedLogContainer> readFromStringList(
        List<String> logs,
        OffsetDateTime from, OffsetDateTime to
    ) {
        return logs.stream()
            .map(LogsParser::parse)
            .filter(
                dict -> from.isBefore(dict.datetime())
                    && to.isAfter(dict.datetime()))
            .toList();
    }

    static String extension(Path path) {
        var nameAndExt = path.getFileName().toString().split("\\.");
        if (nameAndExt.length < 2) {
            return "";
        } else {
            return nameAndExt[1];
        }
    }

    static boolean isURI(String path) {
        URI uri = null;
        try {
            uri = new URI(path);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        } catch (URISyntaxException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    Statistics getStatistics(String uriOrPattern, OffsetDateTime from, OffsetDateTime to);
}
