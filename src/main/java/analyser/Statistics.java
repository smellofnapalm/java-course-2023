package analyser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, который содержит в себе типизированные логи и пути к файлам, откуда они были получены.
 * Он позволяет считать различные статистики по этим данным
 */
public final class Statistics {
    final List<ParsedLogContainer> data;
    final List<String> paths;
    final static DateTimeFormatter DF = DateTimeFormatter.ISO_DATE;
    final static String IPV4 = "^[0-9.]+$";

    public Statistics(List<ParsedLogContainer> data, List<String> paths) {
        this.data = data;
        this.paths = paths;
    }

    int totalNumberOfRequests() {
        return data.size();
    }

    List<Map.Entry<String, Object>> findFrequentlyUsed(int k, LogsParser.Args arg) {
        try {
            Method method
                = ParsedLogContainer.class.getMethod(String.valueOf(arg).toLowerCase());
            return data.stream()
                .collect(Collectors.groupingBy(dict -> {
                    try {
                        var value = method.invoke(dict);
                        return value.toString();
                    } catch (IllegalAccessException | InvocationTargetException ignored) {
                    }
                    return null;
                }, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, kv -> (Object) kv.getValue()))
                .entrySet().stream()
                .sorted((o1, o2) -> Math.toIntExact((Long) o2.getValue() - (Long) o1.getValue()))
                .limit(k)
                .toList();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    double averageResponseSize() {
        return data.stream().mapToLong(ParsedLogContainer::sent).average().orElse(0);
    }

    String findEarliestDate() {
        var date = data.stream()
            .map(ParsedLogContainer::datetime)
            .min(OffsetDateTime::compareTo).orElse(OffsetDateTime.MAX);
        return date.format(DF);
    }

    String findLatestDate() {
        var date = data.stream()
            .map(ParsedLogContainer::datetime)
            .max(OffsetDateTime::compareTo).orElse(OffsetDateTime.MAX);
        return date.format(DF);
    }

    List<Map.Entry<String, Object>> findIPv4AndIPv6() {
        return data.stream()
            .collect(Collectors.groupingBy(dict -> {
                var ip = dict.ip();
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
                var dateTime = dict.datetime();
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
}
