package edu.hw10.task1;

public class PersonPOJO {
    String name;
    int age;

    public PersonPOJO(String name, @Min(1) @Max(99) int age) {
        this.name = name;
        this.age = age;
    }

    public static PersonPOJO create(@NotNullReflection String name, @Min(1) @Max(99) int age) {
        return new PersonPOJO(name, age);
    }
}
