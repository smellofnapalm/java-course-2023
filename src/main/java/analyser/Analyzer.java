package analyser;

import analyser.reader.LocalReader;
import analyser.reader.ReadLogs;
import analyser.reader.URIReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import static analyser.ArgsParser.parseArgs;
import static analyser.reader.ReadLogs.isURI;

public final class Analyzer {
    @SuppressWarnings("RegexpSinglelineJava")
    public static void analyze(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Аргументы командной строки должны быть непустыми!");
        }
        ArgsContainer argsObjectMap = parseArgs(args);
        System.out.println("Получили аргументы командной строки");
        String pathToLogs = argsObjectMap.path();
        OffsetDateTime from = argsObjectMap.from();
        OffsetDateTime to = argsObjectMap.to();
        String format = argsObjectMap.format();

        ReadLogs reader = (isURI(pathToLogs) ? new URIReader() : new LocalReader());

        Statistics stat = reader.getStatistics(pathToLogs, from, to);
        System.out.println("Прочитали файл(-ы) и посчитали статистику");

        String output = stat.getOutput(format);
        System.out.println("Получили таблички с результатом");

        final Path path = Path.of(".", "logsAnalysis." + (format.equals("markdown") ? "md" : "adoc"));
        try {
            Files.writeString(path, output);
            System.out.printf("Данные записаны в файл %s%n", path.toString());
        } catch (IOException e) {
            System.out.println("Не получилось записать в файл!");
        }
    }

    private Analyzer() {
    }
}
