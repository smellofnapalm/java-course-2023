package edu.hw5.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class PatternsParser extends AbstractParser {

    final static String[] PATTERNS = {"yyyy-MM-dd", "yyyy-MM-d", "M/d/yy", "M/d/y"};

    @Override
    protected Optional<LocalDate> parseDate(String date) {
        for (var pattern : PATTERNS) {
            var df = DateTimeFormatter.ofPattern(pattern);
            try {
                return Optional.of(LocalDate.parse(date, df));
            } catch (DateTimeParseException ignored) {
            }
        }
        return Optional.empty();
    }

}
