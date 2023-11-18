package edu.hw5.task4;

import java.util.regex.Pattern;

public final class PasswordMatcher {
    private static final Pattern PATTERN = Pattern.compile("^.*[~!@#$%^&*|]+.*$");

    static boolean passwordMatches(String password) {
        if (password == null) {
            throw new NullPointerException("Пароль не может быть null!");
        }
        return PATTERN.matcher(password).matches();
    }

    private PasswordMatcher() {
    }
}
