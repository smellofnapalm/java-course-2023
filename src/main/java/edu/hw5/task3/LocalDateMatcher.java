package edu.hw5.task3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LocalDateMatcher {
    final static String[] PATTERNS = {"yyyy-MM-dd", "yyyy-MM-d", "M/d/yy", "M/d/y"};
    final static LocalDate TODAY = LocalDate.now();

    final static String DAYS_AGO = "^([0-9]+) days ago$";

    static Optional<LocalDate> parseDate(String date) {
        var df = new SimpleDateFormat();
        df.setLenient(false);

        if (date.matches(DAYS_AGO)) {
            Pattern pattern = Pattern.compile(DAYS_AGO);
            Matcher matcher = pattern.matcher(date);
            matcher.find();
            int days = Integer.parseInt(matcher.group(1));
            return Optional.of(TODAY.minusDays(days));
        }

        var shift = switch (date) {
            case "today" -> {
                yield 0;
            }
            case "yesterday", "1 day ago" -> {
                yield -1;
            }
            case "tomorrow" -> {
                yield 1;
            }
            default -> {
                yield Integer.MAX_VALUE;
            }
        };
        if (shift != Integer.MAX_VALUE) {
            return Optional.of(TODAY.plusDays(shift));
        }

        for (var pattern : PATTERNS) {
            df.applyPattern(pattern);
            try {
                return Optional.ofNullable(df.parse(date).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate());
            } catch (ParseException ignored) {
            }
        }
        return Optional.empty();
    }

    private LocalDateMatcher() {
    }
}
