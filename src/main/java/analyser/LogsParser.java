package analyser;

import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, который переводит одну строку логов в промежуточное типизированное представление
 */
public final class LogsParser {

    public enum Args {
        IP, USER, DATETIME, REQUEST, PATH, PROTOCOL, CODE, SENT, REFERER, AGENT
    }

    private static final Pattern PATTERN = Pattern.compile(
        "^(?<IP>[\\w.:]+) - (?<USER>.+) \\[(?<DATETIME>.*)\\] \"(?<REQUEST>\\w+) (?<PATH>.+)"
            + " (?<PROTOCOL>[\\w/.]+)\" (?<CODE>[\\d]+) (?<SENT>[\\d]+) \"(?<REFERER>.+)\" \"(?<AGENT>.+)\"$");

    private static final DateTimeFormatter DF =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss X").withLocale(Locale.ENGLISH).withResolverStyle(
            ResolverStyle.SMART);

    private static Map<Args, String> logToGroups(String log) {
        Matcher matcher = PATTERN.matcher(log);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Логи не соответствуют паттерну!");
        }
        Map<Args, String> dict = new HashMap<>();
        for (var field : Args.values()) {
            dict.put(field, matcher.group(field.name()));
        }
        return dict;
    }

    private static Map<Args, Object> groupsToObjects(Map<Args, String> dict) {
        var objectDict = new HashMap<Args, Object>(dict);

        String time = dict.get(Args.DATETIME);
        var parsedTime = OffsetDateTime.parse(time, DF);
        objectDict.put(Args.DATETIME, parsedTime);

        Path parsedPath = Path.of(dict.get(Args.PATH));
        objectDict.put(Args.PATH, parsedPath);

        Long parsedSent = Long.valueOf(dict.get(Args.SENT));
        objectDict.put(Args.SENT, parsedSent);

        return objectDict;
    }

    public static Map<Args, Object> parse(String log) {
        return groupsToObjects(logToGroups(log));
    }

    private LogsParser() {
    }
}
