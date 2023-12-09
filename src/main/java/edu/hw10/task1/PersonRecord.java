package edu.hw10.task1;

public record PersonRecord(String name, @Min(1) @Max(100) Integer age) {
}
