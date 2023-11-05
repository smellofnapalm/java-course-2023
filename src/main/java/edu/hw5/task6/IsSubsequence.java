package edu.hw5.task6;

import java.util.stream.Collectors;

public final class IsSubsequence {
    static boolean isSubsequence(String sub, String text) {
        if (sub == null || text == null) {
            throw new NullPointerException("Подстрока и текст не могут быть null!");
        }
        // Будем считать, что пустая подстрока входит куда угодно
        if (sub.isEmpty()) {
            return true;
        }
        String pattern = sub.codePoints().mapToObj(pt -> ".*[%s]{1}.*".formatted(new String(Character.toChars(pt))))
            .map(String::toString).collect(Collectors.joining());
        return text.matches(pattern);
    }

    private IsSubsequence() {
    }
}
