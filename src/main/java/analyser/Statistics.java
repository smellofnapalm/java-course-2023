package analyser;

import analyser.printer.ADOCPrinter;
import analyser.printer.MarkdownPrinter;
import analyser.printer.Printer;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, который содержит в себе типизированные логи и пути к файлам, откуда они были получены.
 * Он позволяет считать различные статистики по этим данным
 */
public final class Statistics {
    public static final String MARKDOWN = "markdown";
    public static final String ADOC = "adoc";
    public static final String AMOUNT = "Количество";
    public static final String RESPONSE_AMOUNT = "Количество запросов";
    final List<Map<LogsParser.Args, Object>> data;
    final List<String> paths;
    final static DateTimeFormatter DF = DateTimeFormatter.ISO_DATE;
    final static String IPV4 = "^[0-9.]+$";

    Statistics(List<Map<LogsParser.Args, Object>> data, List<String> paths) {
        this.data = data;
        this.paths = paths;
    }

    int totalNumberOfRequests() {
        return data.size();
    }

    List<Map.Entry<String, Object>> findFrequentlyUsed(int k, LogsParser.Args arg) {
        return data.stream()
            .collect(Collectors.groupingBy(dict -> dict.get(arg).toString(), Collectors.counting()))
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, kv -> (Object) kv.getValue()))
            .entrySet().stream()
            .sorted((o1, o2) -> Math.toIntExact((Long) o2.getValue() - (Long) o1.getValue()))
            .limit(k)
            .toList();
    }

    double averageResponseSize() {
        return data.stream().mapToLong(dict -> (long) dict.get(LogsParser.Args.SENT)).average().orElse(0);
    }

    String findEarliestDate() {
        var date = data.stream()
            .map(dict -> ((OffsetDateTime) dict.get(LogsParser.Args.DATETIME)))
            .min(OffsetDateTime::compareTo).orElse(OffsetDateTime.MAX);
        return date.format(DF);
    }

    String findLatestDate() {
        var date = data.stream()
            .map(dict -> ((OffsetDateTime) dict.get(LogsParser.Args.DATETIME)))
            .max(OffsetDateTime::compareTo).orElse(OffsetDateTime.MAX);
        return date.format(DF);
    }

    List<Map.Entry<String, Object>> findIPv4AndIPv6() {
        return data.stream()
            .collect(Collectors.groupingBy(dict -> {
                var ip = (String) dict.get(LogsParser.Args.IP);
                return ip.matches(IPV4) ? "IPv4" : "IPv6";
            }, Collectors.counting()))
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, kv -> (Object) kv.getValue()))
            .entrySet().stream()
            .sorted((o1, o2) -> Math.toIntExact((Long) o2.getValue() - (Long) o1.getValue())).toList();
    }

    List<Map.Entry<String, Object>> findTimeOfResponses() {
        return data.stream()
            .collect(Collectors.groupingBy(dict -> {
                var dateTime = (OffsetDateTime) dict.get(LogsParser.Args.DATETIME);
                return String.valueOf(dateTime.getHour());
            }, Collectors.counting()))
            .entrySet().stream()
            .collect(Collectors.toMap(kv -> {
                int hour = Integer.parseInt(kv.getKey());
                return "%02d:00 -- %02d:00".formatted(hour, hour + 1);
            }, kv -> (Object) kv.getValue()))
            .entrySet().stream()
            .sorted((o1, o2) -> Math.toIntExact((Long) o2.getValue() - (Long) o1.getValue())).toList();
    }

    public String getOutput(String markdownOrADOC) {
        if (markdownOrADOC == null || (!markdownOrADOC.equals(MARKDOWN) && !markdownOrADOC.equals(ADOC))) {
            throw new IllegalArgumentException("Выход может быть только в формате .md или .adoc!");
        }
        final Printer printer = markdownOrADOC.equals(MARKDOWN) ? new MarkdownPrinter() : new ADOCPrinter();

        final String table1 = getTableWithTotalInfo(printer);
        final String table2 = getTableWithResourses(printer);
        final String table3 = getTableWithCodes(printer);
        final String table4 = getTableWithIPs(printer);
        final String table5 = getTableWithHours(printer);

        return table1 + table2 + table3 + table4 + table5;
    }

    private String getTableWithHours(Printer printer) {
        final String header5 = "Часы запросов по популярности";
        final List<String> names5 = List.of("Промежуток времени", RESPONSE_AMOUNT);
        final List<Map.Entry<String, Object>> list5 = findTimeOfResponses();
        return printer.print(header5, names5, list5);
    }

    private String getTableWithIPs(Printer printer) {
        final String header4 = "IPv4 vs. IPv6";
        final List<String> names4 = List.of("Версия IP", AMOUNT);
        final List<Map.Entry<String, Object>> list4 = findIPv4AndIPv6();
        return printer.print(header4, names4, list4);
    }

    private String getTableWithCodes(Printer printer) {
        final String header3 = "Коды ответа";
        final List<String> names3 = List.of("Код ответа", AMOUNT);
        final List<Map.Entry<String, Object>> list3 = findFrequentlyUsed(5, LogsParser.Args.CODE);
        return printer.print(header3, names3, list3);
    }

    private String getTableWithResourses(Printer printer) {
        final String header2 = "Запрашиваемые ресурсы";
        final List<String> names2 = List.of("Ресурс", AMOUNT);
        final List<Map.Entry<String, Object>> list2 = findFrequentlyUsed(5, LogsParser.Args.PATH);
        return printer.print(header2, names2, list2);
    }

    private String getTableWithTotalInfo(Printer printer) {
        final String header1 = "Общая информация";
        final List<String> names1 = List.of("Метрика", "Значение");
        final List<Map.Entry<String, Object>> list1 = List.of(
            new AbstractMap.SimpleEntry<>("Файлы", paths),
            new AbstractMap.SimpleEntry<>("Начальная дата", findEarliestDate()),
            new AbstractMap.SimpleEntry<>("Конечная дата", findLatestDate()),
            new AbstractMap.SimpleEntry<>(RESPONSE_AMOUNT, totalNumberOfRequests()),
            new AbstractMap.SimpleEntry<>("Средний размер ответа (в байтах)", averageResponseSize())
        );
        return printer.print(header1, names1, list1);
    }
}
