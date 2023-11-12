package analyser;

import java.nio.file.Path;
import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogsParser {
    private static final String QBL = Pattern.quote("[");
    private static final String QBR = Pattern.quote("]");
    private static final String[] FIELD_NAMES =
        {"ip", "user", "datetime", "request", "path", "protocol", "code", "sent", "referer", "agent"};
    private static final Pattern PATTERN = Pattern.compile(
        "^(?<ip>[\\w.:]+) - (?<user>.+) \\[(?<datetime>.*)\\] \"(?<request>\\w+) (?<path>[\\w/]+)"
            + " (?<protocol>[\\w/.]+)\" (?<code>[\\d]+) (?<sent>[\\d]+) \"(?<referer>.+)\" \"(?<agent>.+)\"$");

    private static final DateTimeFormatter DF =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss X").withLocale(Locale.ENGLISH).withResolverStyle(
            ResolverStyle.SMART);

    private static Map<String, String> logToGroups(String log) {
        Matcher matcher = PATTERN.matcher(log);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Логи не соответствуют паттерну!");
        }
        Map<String, String> dict = new HashMap<>();
        for (var field : FIELD_NAMES) {
            dict.put(field, matcher.group(field));
        }
        return dict;
    }

    private static Map<String, Object> groupsToObjects(Map<String, String> dict) {
        var objectDict = new HashMap<String, Object>(dict);

        String time = dict.get("datetime");
        var datetime = OffsetDateTime.parse(time, DF);
        objectDict.put("datetime", datetime);

        Path path = Path.of(dict.get("path"));
        objectDict.put("path", path);

        Long sent = Long.valueOf(dict.get("sent"));
        objectDict.put("sent", sent);

        return objectDict;
    }

    public static Map<String, Object> parse(String log) {
        return groupsToObjects(logToGroups(log));
    }

    private LogsParser() {
    }
}
