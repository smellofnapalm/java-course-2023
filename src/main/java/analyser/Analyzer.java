package analyser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Map;
import static analyser.ArgsParser.parseArgs;
import static analyser.ReadLogs.getStatistics;

public final class Analyzer {
    @SuppressWarnings("RegexpSinglelineJava")
    public static void analyze(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Аргументы командной строки должны быть непустыми!");
        }
        Map<ArgsParser.Args, Object> argsObjectMap = parseArgs(args);
        System.out.println("Получили аргументы командной строки");
        String pathToLogs = (String) argsObjectMap.get(ArgsParser.Args.PATH);
        OffsetDateTime from = (OffsetDateTime) argsObjectMap.get(ArgsParser.Args.FROM);
        OffsetDateTime to = (OffsetDateTime) argsObjectMap.get(ArgsParser.Args.TO);
        String format = (String) argsObjectMap.get(ArgsParser.Args.FORMAT);

        Statistics stat = getStatistics(pathToLogs, from, to);
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
