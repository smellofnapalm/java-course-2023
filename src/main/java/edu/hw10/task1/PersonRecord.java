package edu.hw10.task1;

public record PersonRecord(@NotNullReflection String name, @Min(1) @Max(100) Integer age) {
}
