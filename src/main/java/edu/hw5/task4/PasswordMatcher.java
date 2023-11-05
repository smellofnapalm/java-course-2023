package edu.hw5.task4;

public final class PasswordMatcher {
    static boolean passwordMatches(String password) {
        if (password == null) {
            throw new NullPointerException("Пароль не может быть null!");
        }
        String pattern = "^.*[~!@#$%^&*|]+.*$";
        return password.matches(pattern);
    }

    private PasswordMatcher() {
    }
}
