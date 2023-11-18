package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public abstract class AbstractParser {
    private AbstractParser next;

    public void setNext(AbstractParser next) {
        this.next = next;
    }

    public Optional<LocalDate> tryParsing(String date) {
        Optional<LocalDate> res = parseDate(date);
        if (res.isPresent()) {
            return res;
        }
        if (next != null) {
            return next.tryParsing(date);
        }
        return Optional.empty();
    }

    abstract protected Optional<LocalDate> parseDate(String date);
}
