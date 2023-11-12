package analyser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ReadLogs {

    final static int MAX_DEPTH = 50;
    final static Path DIR = Path.of(".");

    static List<Map<LogsParser.Args, Object>> readOneFile(Path path, OffsetDateTime from, OffsetDateTime to) {
        try {
            List<String> logs = Files.readAllLines(path);
            return readFromStringList(logs, from, to);
        } catch (IOException e) {
            throw new IllegalArgumentException("Путь к логам задан некорректно!");
        }
    }

    static private List<Map<LogsParser.Args, Object>> readFromStringList(
        List<String> logs,
        OffsetDateTime from, OffsetDateTime to
    ) {
        return logs.stream()
            .map(LogsParser::parse)
            .filter(
                dict -> from.isBefore((OffsetDateTime) dict.get(LogsParser.Args.DATETIME))
                    && to.isAfter((OffsetDateTime) dict.get(LogsParser.Args.DATETIME)))
            .toList();
    }

    static List<Map<LogsParser.Args, Object>> readOneFileFromURI(URI uri, OffsetDateTime from, OffsetDateTime to) {
        final int timeout = 5;
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(timeout)).build();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return readFromStringList(Arrays.stream(response.body().split("\n")).toList(), from, to);
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Не удалось скачать логи по заданному пути!");
        }
    }

    private static String extension(Path path) {
        var nameAndExt = path.getFileName().toString().split("\\.");
        if (nameAndExt.length < 2) {
            return "";
        } else {
            return nameAndExt[1];
        }
    }

    static List<Path> findMatchingFiles(String globPattern) {
        String pattern = "glob:./" + globPattern;
        var matcher = FileSystems.getDefault().getPathMatcher(pattern);
        try {
            return Files.find(DIR, MAX_DEPTH,
                (path, attributes) -> matcher.matches(path) && attributes.isRegularFile()
                    && extension(path).equals("txt")
            ).toList();
        } catch (IOException ignored) {
            // Ошибка тут не должна бросаться -- директория по пути Path.of(".") всегда существует
        }
        return List.of();
    }

    static List<Map<LogsParser.Args, Object>> readAllFilesGlob(
        List<Path> logFiles,
        OffsetDateTime from, OffsetDateTime to
    ) {
        List<Map<LogsParser.Args, Object>> res = new ArrayList<>();
        logFiles.stream().map(path -> readOneFile(path, from, to)).forEach(res::addAll);
        return res;
    }

    private ReadLogs() {
    }
}
