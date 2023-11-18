package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public final class LocalDateMatcher {
    private static final AbstractParser DAYS_AGO_PARSER = new DaysAgoParser();
    private static final AbstractParser SPECIAL_WORDS_PARSER = new SpecialWordsParser();
    private static final AbstractParser PATTERNS_PARSER = new PatternsParser();

    static {
        DAYS_AGO_PARSER.setNext(SPECIAL_WORDS_PARSER);
        SPECIAL_WORDS_PARSER.setNext(PATTERNS_PARSER);
    }

    public static Optional<LocalDate> parseDate(String date) {
        return DAYS_AGO_PARSER.tryParsing(date);
    }

    private LocalDateMatcher() {
    }
}
