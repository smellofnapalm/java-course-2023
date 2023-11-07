package analyser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ArgsParser {
    public enum Args {
        PATH, FROM, TO, FORMAT
    }

    private final static DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
    private final static String TODAY = DF.format(java.sql.Date.valueOf(LocalDate.now()));
    private final static String ADOC = "adoc";
    private final static String MARKDOWN = "markdown";
    private final static String DEFAULT_FORMAT = ADOC;
    private final static Map<Args, String> DEFAULT_VALUES = Map.of(
        Args.FROM, TODAY,
        Args.TO, TODAY,
        Args.FORMAT, DEFAULT_FORMAT
    );
    private final static List<String> FORMAT_VALUES = List.of(MARKDOWN, ADOC);

    static Map<Args, Object> parseArgs(String[] args) {
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

        setDefaultValues(params);
        return convertToObjects(params);
    }

    private static boolean dateIsValid(String date) {
        DF.setLenient(false);
        try {
            DF.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static LocalDate convertToDate(String date) {
        DF.setLenient(false);
        Date res = null;
        try {
            res = DF.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return res.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static void setDefaultValues(Map<Args, String> params) {
        if (params == null || !params.containsKey(Args.PATH)) {
            throw new IllegalArgumentException("Не передан обязательный аргумент -- путь к логам!");
        }

        boolean hasFrom = params.containsKey(Args.FROM) && dateIsValid(params.get(Args.FROM));
        boolean hasTo = params.containsKey(Args.TO) && dateIsValid(params.get(Args.TO));
        boolean hasFormat = params.containsKey(Args.FORMAT)
            && FORMAT_VALUES.contains(params.get(Args.FORMAT));

        if (!hasFrom && !hasTo) {
            params.put(Args.FROM, DEFAULT_VALUES.get(Args.FROM));
            params.put(Args.TO, DEFAULT_VALUES.get(Args.TO));
        } else if (!hasFrom) {
            params.put(Args.FROM, params.get(Args.TO));
        } else if (!hasTo) {
            params.put(Args.TO, params.get(Args.FROM));
        }

        if (!hasFormat) {
            params.put(Args.FORMAT, DEFAULT_VALUES.get(Args.FORMAT));
        }
    }

    private static Map<Args, Object> convertToObjects(Map<Args, String> params) {
        Map<Args, Object> result = new HashMap<>(params);
        String fromString = params.get(Args.FROM);
        String toString = params.get(Args.TO);
        result.put(Args.FROM, convertToDate(fromString));
        result.put(Args.TO, convertToDate(toString));
        return result;
    }

    private ArgsParser() {
    }
}
