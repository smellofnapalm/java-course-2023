package analyser;

import analyser.printer.ADOCPrinter;
import analyser.printer.MarkdownPrinter;
import analyser.printer.Printer;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, который позволяет получить табличку с данными.
 */
public class OutputMaker {

    public static final String MARKDOWN = "markdown";
    public static final String ADOC = "adoc";
    public static final String AMOUNT = "Количество";
    public static final String RESPONSE_AMOUNT = "Количество запросов";
    final Statistics statistics;

    public OutputMaker(Statistics statistics) {
        this.statistics = statistics;
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
        final List<Map.Entry<String, Object>> list5 = statistics.findTimeOfResponses();
        return printer.print(header5, names5, list5);
    }

    private String getTableWithIPs(Printer printer) {
        final String header4 = "IPv4 vs. IPv6";
        final List<String> names4 = List.of("Версия IP", AMOUNT);
        final List<Map.Entry<String, Object>> list4 = statistics.findIPv4AndIPv6();
        return printer.print(header4, names4, list4);
    }

    private String getTableWithCodes(Printer printer) {
        final String header3 = "Коды ответа";
        final List<String> names3 = List.of("Код ответа", AMOUNT);
        final List<Map.Entry<String, Object>> list3 = statistics.findFrequentlyUsed(5, LogsParser.Args.CODE);
        return printer.print(header3, names3, list3);
    }

    private String getTableWithResourses(Printer printer) {
        final String header2 = "Запрашиваемые ресурсы";
        final List<String> names2 = List.of("Ресурс", AMOUNT);
        final List<Map.Entry<String, Object>> list2 = statistics.findFrequentlyUsed(5, LogsParser.Args.PATH);
        return printer.print(header2, names2, list2);
    }

    private String getTableWithTotalInfo(Printer printer) {
        final String header1 = "Общая информация";
        final List<String> names1 = List.of("Метрика", "Значение");
        final List<Map.Entry<String, Object>> list1 = List.of(
            new AbstractMap.SimpleEntry<>("Файлы", statistics.paths),
            new AbstractMap.SimpleEntry<>("Начальная дата", statistics.findEarliestDate()),
            new AbstractMap.SimpleEntry<>("Конечная дата", statistics.findLatestDate()),
            new AbstractMap.SimpleEntry<>(RESPONSE_AMOUNT, statistics.totalNumberOfRequests()),
            new AbstractMap.SimpleEntry<>("Средний размер ответа (в байтах)", statistics.averageResponseSize())
        );
        return printer.print(header1, names1, list1);
    }
}
