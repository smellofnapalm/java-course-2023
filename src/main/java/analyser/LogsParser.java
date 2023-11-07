package analyser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogsParser {
    private static final String QBL = Pattern.quote("[");
    private static final String QBR = Pattern.quote("]");
    private static final String[] FIELD_NAMES =
        {"ip", "user", "datetime", "request", "path", "protocol", "code", "sent", "referer", "agent"};
    private static final Pattern PATTERN = Pattern.compile(
        "^(?<ip>[0-9.]+) - (?<user>.+) \\[(?<datetime>.*)\\] \"(?<request>\\w+) (?<path>[\\w/]+)"
            + " (?<protocol>[\\w/.]+)\" (?<code>[\\d]+) (?<sent>[\\d]+) \"(?<referer>.+)\" \"(?<agent>.+)\"$");

    public static Map<String, String> logToGroups(String log) {
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

    private LogsParser() {
    }
}
