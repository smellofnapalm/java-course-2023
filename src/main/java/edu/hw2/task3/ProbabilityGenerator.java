package edu.hw2.task3;

import java.util.random.RandomGenerator;

public class ProbabilityGenerator {
    static final RandomGenerator GENERATOR = RandomGenerator.getDefault();

    public static boolean generate(double probability) {
        // Так как распределение равномерное на отрезке [0, 1]
        // То с вероятностью PROBABILITY с.в. попадет в отрезок
        // [0, PROBABILITY]
        var value = GENERATOR.nextDouble();
        return value <= probability;
    }

    private ProbabilityGenerator() {
    }
}
