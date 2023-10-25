package edu.hw4;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toMap;

public final class AnimalUtils {
    static List<Animal> sortByHeight(List<Animal> list) {
        if (list == null) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        Comparator<Animal> cmp = comparing(Animal::height);
        return list.stream().sorted(cmp).toList();
    }

    static List<Animal> getFirstKByWeight(List<Animal> list, int k) {
        if (list == null) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        int newK = Math.max(0, k);
        Comparator<Animal> cmp = comparing(Animal::weight).reversed();
        return list.stream().sorted(cmp).limit(newK).toList();
    }

    static Map<Animal.Type, Long> countTypesOfAnimals(List<Animal> list) {
        if (list == null) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream()
            .collect(groupingBy(Animal::type, counting()));
    }

    static Animal whoHasLongestName(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().max(Comparator.comparingInt(a -> a.name().length())).get();
    }

    static Animal.Sex findSexMajority(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
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
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream()
            .collect(groupingBy(Animal::type, maxBy(comparing(Animal::weight))))
            .entrySet()
            .stream()
            .collect(toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }

    static Animal findKthOldestAnimal(List<Animal> list, int k) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        if (k <= 0 || k > list.size()) {
            throw new IllegalArgumentException("Номер животного должен быть в диапазоне [1, list.size()]!");
        }
        return list.stream().sorted(comparing(Animal::age).reversed()).skip(k - 1).findFirst().get();
    }

    static Optional<Animal> findHeaviestAnimalLowerThenK(List<Animal> list, int k) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().filter(a -> a.height() < k).max(comparing(Animal::weight));
    }

    static Integer calcTotalAmountOfPaws(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().mapToInt(Animal::paws).sum();
    }

    static List<Animal> getAnimalsHavingAgeNotEqualToPaws(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().filter(a -> a.paws() != a.age()).toList();
    }

    static List<Animal> getAnimalsThatCanBiteAndTall(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        final int threshold = 100;
        return list.stream().filter(a -> a.bites() && a.height() > threshold).toList();
    }

    static Integer calcNumberOfAnimalWithWeightGreaterThanHeight(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return Math.toIntExact(list.stream().filter(a -> a.weight() > a.height()).count());
    }

    static List<Animal> getAnimalsHavingMoreThanTwoWordsName(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().filter(a -> a.name().split(" ").length > 2).toList();
    }

    static boolean isThereDogWithHeightMoreThanK(List<Animal> list, int k) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return !list.stream().filter(a -> a.type() == Animal.Type.DOG && a.height() > k).toList().isEmpty();
    }

    static Integer calcTotalAgeOfAnimalsBetween(List<Animal> list, int k, int l) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().filter(x -> x.age() >= k && x.age() <= l).mapToInt(Animal::weight).sum();
    }

    static List<Animal> sortByTypeAgeName(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream()
            .sorted(comparing(Animal::type).thenComparing(Animal::age).thenComparing(Animal::name))
            .toList();
    }

    static boolean doSpidersBiteOftenThanDogs(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().mapToInt(a -> {
            if (!a.bites() || (a.type() != Animal.Type.SPIDER && a.type() != Animal.Type.DOG)) {
                return 0;
            }
            return (a.type() == Animal.Type.SPIDER ? 1 : -1);
        }).sum() > 0;
    }

    static Optional<Animal> findHeaviestFishInManyLists(List<List<Animal>> lists) {
        if (lists == null || lists.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return lists.stream()
            .map(list -> list.stream().filter(a -> a.type() == Animal.Type.FISH).max(comparing(Animal::weight)))
            .filter(Optional<Animal>::isPresent)
            .map(Optional::get)
            .max(comparing(Animal::weight));
    }

    static Map<String, Set<ValidationError>> getNamesAndErrorsInAnimalList(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        return list.stream().collect(toMap(Animal::name, AnimalUtils::getAllErrorsFromAnimal, (a, b) -> b));
    }

    static Map<String, String> getPrettyErrors(List<Animal> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_OR_NULL_STR_MESSAGE);
        }
        var dict = getNamesAndErrorsInAnimalList(list);
        return dict.entrySet().stream().collect(toMap(
            Map.Entry::getKey,
            entry -> String.join(", ", entry.getValue().stream().map(x -> x.badFieldName).toList())
        ));
    }

    private static Set<ValidationError> getAllErrorsFromAnimal(Animal a) {
        final int bigAge = 10000;
        if (a == null) {
            throw new IllegalArgumentException("Животное не может быть null!");
        }
        Set<ValidationError> errors = new HashSet<>();
        if (a.name() == null || !a.name().matches("[a-zA-Z]+")) {
            errors.add(new ValidationError("name"));
        }
        if (a.type() == null) {
            errors.add(new ValidationError("type"));
        }
        if (a.sex() == null) {
            errors.add(new ValidationError("sex"));
        }
        if (a.age() < 0 || a.age() > bigAge) {
            errors.add(new ValidationError("age"));
        }
        if (a.height() <= 0) {
            errors.add(new ValidationError("height"));
        }
        if (a.weight() <= 0) {
            errors.add(new ValidationError("weight"));
        }
        return errors;
    }

    static final String EMPTY_OR_NULL_STR_MESSAGE = "Список животных должен быть не null и не пустым!";

    private AnimalUtils() {
    }
}
