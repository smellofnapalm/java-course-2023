package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaysAgoParser extends AbstractParser {
    final static Pattern PATTERN = Pattern.compile("^([0-9]+) days ago$");

    @Override
    protected Optional<LocalDate> parseDate(String date) {
        Matcher matcher = PATTERN.matcher(date);
        if (matcher.matches()) {
            int days = Integer.parseInt(matcher.group(1));
            return Optional.of(LocalDate.now().minusDays(days));
        }
        return Optional.empty();
    }
}
