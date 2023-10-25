package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    public int paws() {
        final int catPaws = 4;
        final int spiderPaws = 8;
        final int birdPaws = 2;
        return switch (type) {
            case CAT, DOG -> catPaws;
            case BIRD -> birdPaws;
            case FISH -> 0;
            case SPIDER -> spiderPaws;
        };
    }
}
