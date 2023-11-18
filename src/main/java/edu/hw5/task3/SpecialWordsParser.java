package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public class SpecialWordsParser extends AbstractParser {
    @Override
    protected Optional<LocalDate> parseDate(String date) {
        var shift = switch (date) {
            case "today" -> 0;
            case "yesterday", "1 day ago" -> -1;
            case "tomorrow" -> 1;
            default -> Integer.MAX_VALUE;
        };
        if (shift == Integer.MAX_VALUE) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.now().plusDays(shift));
    }
}
