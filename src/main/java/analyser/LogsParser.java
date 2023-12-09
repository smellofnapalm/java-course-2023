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
import static analyser.LogsParser.Args.AGENT;
import static analyser.LogsParser.Args.CODE;
import static analyser.LogsParser.Args.IP;
import static analyser.LogsParser.Args.PROTOCOL;
import static analyser.LogsParser.Args.REFERER;
import static analyser.LogsParser.Args.REQUEST;
import static analyser.LogsParser.Args.USER;

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

    private static ParsedLogContainer groupsToObjects(Map<Args, String> dict) {
        String time = dict.get(Args.DATETIME);
        OffsetDateTime parsedTime = OffsetDateTime.parse(time, DF);
        Path parsedPath = Path.of(dict.get(Args.PATH));
        long parsedSent = Long.parseLong(dict.get(Args.SENT));
        return new ParsedLogContainer(
            dict.get(IP),
            dict.get(USER),
            parsedTime,
            dict.get(REQUEST),
            parsedPath,
            dict.get(PROTOCOL),
            dict.get(CODE),
            parsedSent,
            dict.get(REFERER),
            dict.get(AGENT)
        );
    }

    public static ParsedLogContainer parse(String log) {
        return groupsToObjects(logToGroups(log));
    }

    private LogsParser() {
    }
}
