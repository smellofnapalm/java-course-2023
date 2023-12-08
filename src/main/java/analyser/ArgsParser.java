package analyser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, который занимается парсингом аргументов командной строки,
 * вставкой значений по-умолчанию и приведение их к нужному типу
 */
public final class ArgsParser {
    public enum Args {
        PATH, FROM, TO, FORMAT
    }

    private final static DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static String ADOC = "adoc";
    private final static String MARKDOWN = "markdown";
    private final static String DEFAULT_FORMAT = ADOC;
    private final static List<String> FORMAT_VALUES = List.of(MARKDOWN, ADOC);

    static ArgsContainer parseArgs(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Не передано аргументов командной строки!");
        }

        Map<Args, String> params = new HashMap<>();
        Args current = null;

        for (final String s : args) {
            if (s.length() > 2 && s.matches("^--[a-z]+$")) {
                current = Args.valueOf(s.substring(2).toUpperCase());
            } else if (current != null) {
                params.put(current, s);
            }
        }

        return setDefaultValues(params);
    }

    private static boolean dateIsValid(String date) {
        try {
            LocalDate.parse(date, DF);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private static ArgsContainer setDefaultValues(Map<Args, String> params) {
        if (params == null || !params.containsKey(Args.PATH)) {
            throw new IllegalArgumentException("Не передан обязательный аргумент -- путь к логам!");
        }

        boolean hasFrom = params.containsKey(Args.FROM) && dateIsValid(params.get(Args.FROM));
        boolean hasTo = params.containsKey(Args.TO) && dateIsValid(params.get(Args.TO));
        boolean hasFormat = params.containsKey(Args.FORMAT)
            && FORMAT_VALUES.contains(params.get(Args.FORMAT));

        String path = params.get(Args.PATH);
        OffsetDateTime from;
        OffsetDateTime to;
        String format;

        if (!hasFormat) {
            format = DEFAULT_FORMAT;
        } else {
            format = params.get(Args.FORMAT);
        }

        if (!hasTo) {
            to = OffsetDateTime.MAX;
        } else {
            to = OffsetDateTime.of(
                LocalDate.parse(params.get(Args.TO), DF),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC
            );
        }

        if (!hasFrom) {
            from = OffsetDateTime.MIN;
        } else {
            from = OffsetDateTime.of(
                LocalDate.parse(params.get(Args.FROM), DF),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC
            );
        }

        return new ArgsContainer(path, from, to, format);
    }

    private ArgsParser() {
    }
}
