package edu.hw4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw4.AnimalUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AnimalUtilsTest {
    final static Animal cat1 = new Animal("Мурзик", Animal.Type.CAT, Animal.Sex.M,
        15, 30, 10, true
    );
    final static Animal cat2 = new Animal("Демон", Animal.Type.CAT, Animal.Sex.F,
        10, 25, 8, false
    );

    final static Animal dog1 = new Animal("Мухтар", Animal.Type.DOG, Animal.Sex.M,
        18, 40, 20, true
    );

    final static Animal dog2 = new Animal("Арчи", Animal.Type.DOG, Animal.Sex.M,
        15, 30, 18, false
    );

    final static Animal bird1 = new Animal("Кеша", Animal.Type.BIRD, Animal.Sex.M,
        5, 20, 5, false
    );

    final static Animal bird2 = new Animal("Ара", Animal.Type.BIRD, Animal.Sex.F,
        2, 20, 5, true
    );

    final static Animal fish1 = new Animal("Дори", Animal.Type.FISH, Animal.Sex.F,
        4, 10, 1, false
    );

    final static Animal fish2 = new Animal("Немо", Animal.Type.FISH, Animal.Sex.M,
        5, 11, 2, false
    );

    final static Animal spider1 = new Animal("Брюс", Animal.Type.SPIDER, Animal.Sex.M,
        1, 5, 1, true
    );

    final static Animal spider2 = new Animal("Старый Дядя Шнюк", Animal.Type.SPIDER, Animal.Sex.M,
        1, 6, 7, false
    );
    final static List<Animal> listOrdinary
        = List.of(cat1, cat2, dog1, dog2, bird1, bird2, fish1, fish2, spider1, spider2);

    @Test
    @DisplayName("Тест сортировки по росту")
    void sortByHeightTest() {
        List<Animal> list = sortByHeight(listOrdinary);

        assertThat(list.get(0)).isEqualTo(spider1);
    }

    @Test
    @DisplayName("Тест взятия k первых по весу")
    void getFirstKByWeightTest() {
        List<Animal> list = getFirstKByWeight(listOrdinary, 2);

        assertThat(list.get(0)).isEqualTo(dog1);
        assertThat(list.get(1)).isEqualTo(dog2);
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Тест подсчета числа животных каждого вида")
    void countTypesOfAnimalsTest() {
        Map<Animal.Type, Long> dict = countTypesOfAnimals(listOrdinary);

        assertThat(dict.get(Animal.Type.DOG)).isEqualTo(2);
        assertThat(dict.get(Animal.Type.SPIDER)).isEqualTo(2);
    }

    @Test
    @DisplayName("Тест нахождения животного с самым длинным именем")
    void whoHasLongestNameTest() {
        Animal animal = whoHasLongestName(listOrdinary);

        assertThat(animal).isEqualTo(spider2);
    }

    @Test
    @DisplayName("Какого пола животных больше?")
    void findSexMajorityTest() {
        Animal.Sex sex = findSexMajority(listOrdinary);

        assertThat(sex).isEqualTo(Animal.Sex.M);
    }

    @Test
    @DisplayName("Самое тяжелое животное каждого вида")
    void findHeaviestAnimalOfEachTypeTest() {
        Map<Animal.Type, Animal> dict = findHeaviestAnimalOfEachType(listOrdinary);

        assertThat(dict.get(Animal.Type.DOG)).isEqualTo(dog1);
    }

    @Test
    @DisplayName("K-е самое старое животное")
    void findKthOldestAnimalTest() {
        Animal animal = findKthOldestAnimal(listOrdinary, 3);

        assertThat(animal).isEqualTo(dog2);
    }

    @Test
    @DisplayName("Самое тяжелое животное среди животных ниже k см (ни одного нет)")
    void findHeaviestAnimalLowerThenKTest1() {
        Optional<Animal> animal = findHeaviestAnimalLowerThenK(listOrdinary, 3);

        assertThat(animal).isNotPresent();
    }

    @Test
    @DisplayName("Самое тяжелое животное среди животных ниже k см")
    void findHeaviestAnimalLowerThenKTest2() {
        Optional<Animal> animal = findHeaviestAnimalLowerThenK(listOrdinary, 15);

        assertThat(animal).isPresent();
        assertThat(animal.get()).isEqualTo(spider2);
    }

    @Test
    @DisplayName("Сколько в сумме лап у животных в списке")
    void calcTotalAmountOfPawsTest() {
        Integer pawsCount = calcTotalAmountOfPaws(listOrdinary);

        assertThat(pawsCount).isEqualTo(36);
    }

    @Test
    @DisplayName("Список животных, возраст у которых не совпадает с количеством лап")
    void getAnimalsHavingAgeNotEqualToPawsTest() {
        List<Animal> list = getAnimalsHavingAgeNotEqualToPaws(listOrdinary);

        assertThat(list.size()).isEqualTo(9);
    }

    @Test
    @DisplayName("Список животных, которые могут укусить (bites == null или true) и рост которых превышает 100 см")
    void getAnimalsThatCanBiteAndTallTest() {
        List<Animal> list = getAnimalsThatCanBiteAndTall(listOrdinary);

        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Сколько в списке животных, вес которых превышает рост")
    void calcNumberOfAnimalWithWeightGreaterThanHeightTest() {
        Integer animalCount = calcNumberOfAnimalWithWeightGreaterThanHeight(listOrdinary);

        assertThat(animalCount).isEqualTo(1);
    }

    @Test
    @DisplayName("Список животных, имена которых состоят из более чем двух слов")
    void getAnimalsHavingMoreThanTwoWordsNameTest() {
        List<Animal> list = getAnimalsHavingMoreThanTwoWordsName(listOrdinary);

        assertThat(spider2).isIn(list);
    }

    @Test
    @DisplayName("Есть ли в списке собака ростом более k см")
    void isThereDogWithHeightMoreThanKTest() {
        boolean flag = isThereDogWithHeightMoreThanK(listOrdinary, 10);

        assertThat(flag).isTrue();
    }

    @Test
    @DisplayName("Найти суммарный вес животных каждого вида, которым от k до l лет")
    void calcTotalAgeOfAnimalsBetweenTest() {
        Map<Animal.Type, Integer> dict = calcTotalAgeOfAnimalsBetween(listOrdinary, 5, 15);

        assertThat(dict.get(Animal.Type.SPIDER)).isNull();
        assertThat(dict.get(Animal.Type.CAT)).isEqualTo(18);
    }

    @Test
    @DisplayName("Список животных, отсортированный по виду, затем по полу, затем по имени")
    void sortByTypeAgeNameTest() {
        List<Animal> list = sortByTypeAgeName(listOrdinary);

        assertThat(list.size()).isEqualTo(listOrdinary.size());
        assertThat(list.get(0)).isEqualTo(cat2);
    }

    @Test
    @DisplayName("Правда ли, что пауки кусаются чаще, чем собаки")
    void doSpidersBiteOftenThanDogsTest() {
        boolean flag = doSpidersBiteOftenThanDogs(listOrdinary);

        assertThat(flag).isFalse();
    }

    @Test
    @DisplayName("Найти самую тяжелую рыбку в 2-х или более списках")
    void findHeaviestFishInManyListsTest() {
        final List<Animal> listFishes = List.of(
            new Animal("Золотая Рыбка", Animal.Type.FISH, Animal.Sex.F,
                5, 11, 1, true
            ),
            new Animal("Серебряная Рыбка", Animal.Type.FISH, Animal.Sex.F,
                3, 10, 1, false
            )
        );
        Optional<Animal> fish = findHeaviestFishInManyLists(List.of(listOrdinary, listFishes));

        assertThat(fish).isPresent();
        assertThat(fish.get()).isEqualTo(fish2);
    }

    @Test
    @DisplayName("Животные, в записях о которых есть ошибки: вернуть имя и список ошибок")
    void getNamesAndErrorsInAnimalListTest() {
        final List<Animal> listWithErrors = List.of(
            new Animal("Золотая Рыбка", Animal.Type.FISH, Animal.Sex.F,
                -1, 11, 1, true
            ),
            new Animal(null, Animal.Type.FISH, null,
                30000000, 10, 1, false
            )
        );
        Map<String, Set<ValidationError>> dict = getNamesAndErrorsInAnimalList(listWithErrors);

        assertThat(dict.get("Золотая Рыбка").stream()
            .findFirst().get().badFieldName).isEqualTo("age");
        assertThat(dict.get(null).size()).isEqualTo(3);
    }

    @Test
    @DisplayName(
        "Сделать результат предыдущего задания более читабельным: вернуть имя и названия полей с ошибками, объединенные в строку")
    void getPrettyErrorsTest() {
        final List<Animal> listWithErrors = List.of(
            new Animal("Золотая Рыбка", Animal.Type.FISH, Animal.Sex.F,
                -1, 11, 1, true
            ),
            new Animal(null, Animal.Type.FISH, null,
                30000000, 10, 1, false
            )
        );
        Map<String, String> dict = getPrettyErrors(listWithErrors);

        var errors1 = dict.get(null).split(", ");
        Arrays.sort(errors1);
        var errors2 = new String[] {"age", "name", "sex"};

        assertThat(dict.get("Золотая Рыбка")).isEqualTo("age");
        Assertions.assertArrayEquals(errors1, errors2);
    }
}
