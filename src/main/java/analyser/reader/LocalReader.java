package analyser.reader;

import analyser.ParsedLogContainer;
import analyser.Statistics;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class LocalReader implements ReadLogs {
    @Override
    public List<ParsedLogContainer> readOneFile(String pathOrUri, OffsetDateTime from, OffsetDateTime to) {
        try {
            List<String> logs = Files.readAllLines(Path.of(pathOrUri));
            return ReadLogs.readFromStringList(logs, from, to);
        } catch (IOException e) {
            throw new IllegalArgumentException("Путь к логам задан некорректно!");
        }
    }

    public List<Path> findMatchingFiles(String globPattern) {
        String pattern = "glob:./" + globPattern;
        var matcher = FileSystems.getDefault().getPathMatcher(pattern);
        try {
            return Files.find(DIR, MAX_DEPTH,
                (path, attributes) -> matcher.matches(path) && attributes.isRegularFile()
                    && ReadLogs.extension(path).equals("txt")
            ).toList();
        } catch (IOException ignored) {
            // Ошибка тут не должна бросаться -- директория по пути Path.of(".") всегда существует
        }
        return List.of();
    }

    public List<ParsedLogContainer> readAllFilesGlob(
        List<Path> logFiles,
        OffsetDateTime from, OffsetDateTime to
    ) {
        List<ParsedLogContainer> res = new ArrayList<>();
        logFiles.stream().map(path -> readOneFile(String.valueOf(path), from, to)).forEach(res::addAll);
        return res;
    }

    @Override
    public Statistics getStatistics(String uriOrPattern, OffsetDateTime from, OffsetDateTime to) {
        List<String> paths;
        List<ParsedLogContainer> logs;
        var pathsClear = findMatchingFiles(uriOrPattern);
        logs = readAllFilesGlob(pathsClear, from, to);
        paths = pathsClear.stream().map(Path::toString).toList();
        return new Statistics(logs, paths);
    }
}
