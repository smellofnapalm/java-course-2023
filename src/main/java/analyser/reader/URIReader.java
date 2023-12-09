package analyser.reader;

import analyser.ParsedLogContainer;
import analyser.Statistics;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public class URIReader implements ReadLogs {
    @Override
    public List<ParsedLogContainer> readOneFile(String pathOrUri, OffsetDateTime from, OffsetDateTime to) {
        final int timeout = 5;
        final URI uri = URI.create(pathOrUri);
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(timeout)).build();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return ReadLogs.readFromStringList(Arrays.stream(response.body().split("\n")).toList(), from, to);
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Не удалось скачать логи по заданному пути!");
        }
    }

    @Override
    public Statistics getStatistics(String uriOrPattern, OffsetDateTime from, OffsetDateTime to) {
        List<ParsedLogContainer> logs = readOneFile(uriOrPattern, from, to);
        return new Statistics(logs, List.of(uriOrPattern));
    }
}
