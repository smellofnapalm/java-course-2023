package edu.hw3.task7;

import java.util.Comparator;

public final class NullComparator {
    private NullComparator() {
    }

    public static <T> Comparator<T> getNullComparator(Comparator<T> cmp) {
        Comparator<T> nullComparator = (o1, o2) -> {
            if (o1 == o2) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            return cmp.compare(o1, o2);
        };
        return nullComparator;
    }
}
