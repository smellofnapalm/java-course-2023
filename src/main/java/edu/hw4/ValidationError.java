package edu.hw4;

public class ValidationError extends RuntimeException {
    final String badFieldName;

    ValidationError(String badFieldName) {
        super("Логическая ошибка в поле %s".formatted(badFieldName));
        this.badFieldName = badFieldName;
    }
}
