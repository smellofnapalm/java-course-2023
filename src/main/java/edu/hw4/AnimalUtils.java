package edu.hw4;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toMap;

public final class AnimalUtils {
    static List<Animal> sortByHeight(List<Animal> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список животных должен быть не null!");
        }
        Comparator<Animal> cmp = comparing(Animal::height);
        return list.stream().sorted(cmp).toList();
    }

    static List<Animal> getFirstKByWeight(List<Animal> list, int k) {
        if (list == null) {
            throw new IllegalArgumentException("Список животных должен быть не null!");
        }
        int newK = Math.max(0, k);
        Comparator<Animal> cmp = comparing(Animal::weight).reversed();
        return list.stream().sorted(cmp).limit(newK).toList();
    }

    static Map<Animal.Type, Long> countTypesOfAnimals(List<Animal> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список животных должен быть не null!");
        }
        return list.stream()
            .collect(groupingBy(Animal::type, counting()));
    }

    static Animal whoHasLongestName(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        return list.stream().max(Comparator.comparingInt(a -> a.name().length())).get();
    }

    static Animal.Sex findSexMajority(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        return list.stream()
            .collect(groupingBy(Animal::sex, counting()))
            .entrySet()
            .stream()
            .max(comparingLong(Map.Entry::getValue))
            .get()
            .getKey();
    }

    static Map<Animal.Type, Animal> findHeaviestAnimalOfEachType(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        return list.stream()
            .collect(groupingBy(Animal::type, maxBy(comparing(Animal::weight))))
            .entrySet()
            .stream()
            .collect(toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }

    static Animal findKthOldestAnimal(List<Animal> list, int k) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        if (k <= 0 || k > list.size()) {
            throw new IllegalArgumentException("Номер животного должен быть в диапазоне [1, list.size()]!");
        }
        return list.stream().sorted(comparing(Animal::age).reversed()).skip(k - 1).findFirst().get();
    }

    static Optional<Animal> findHeaviestAnimalLowerThenK(List<Animal> list, int k) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        return list.stream().filter(a -> a.height() < k).max(comparing(Animal::weight));
    }

    static Integer calcTotalAmountOfPaws(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        return list.stream().mapToInt(Animal::paws).sum();
    }

    static List<Animal> getAnimalsHavingAgeNotEqualToPaws(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        return list.stream().filter(a -> a.paws() != a.age()).toList();
    }

    static List<Animal> getAnimalsThatCanBiteAndTall(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        final int threshold = 100;
        return list.stream().filter(a -> a.bites() && a.height() > threshold).toList();
    }

    static Integer calcNumberOfAnimalWithWeightGreaterThanHeight(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список животных должен быть не null и не пустым!");
        }
        return Math.toIntExact(list.stream().filter(a -> a.weight() > a.height()).count());
    }
}
