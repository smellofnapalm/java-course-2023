package edu.hw10.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RandomObjectGeneratorTest {
    @Test
    @DisplayName("Тестирование создания String")
    void rogTest1() {
        String s = RandomObjectGenerator.nextObjectConstructor(
            String.class,
            new Class<?>[] {String.class}
        );
        assertThat(s).isEqualTo("");
    }

    @Test
    @DisplayName("Тестирование создания Integer")
    void rogTest2() {
        Integer i = RandomObjectGenerator.nextObjectConstructor(
            Integer.class,
            new Class<?>[] {int.class}
        );
        assertThat(i).isInstanceOf(Integer.class);
    }

    @Test
    @DisplayName("Тестирование создания класса PersonPOJO, содержит поля String и Integer")
    void rogTest3() {
        PersonPOJO p =
            RandomObjectGenerator.nextObjectConstructor(
                PersonPOJO.class,
                new Class<?>[] {String.class, int.class}
            );
        assertThat(p.age).isGreaterThan(0);
        assertThat(p.age).isLessThan(100);
        assertThat(p.name).isEqualTo("");
    }

    @Test
    @DisplayName("Тестирование создания класса PersonRecord, содержит поля String и Integer с аннотациями")
    void rogTest4() {
        PersonRecord p =
            RandomObjectGenerator.nextRecordConstructor(PersonRecord.class);
        assertThat(p.age()).isGreaterThan(0);
        assertThat(p.age()).isLessThan(100);
        assertThat(p.name()).isInstanceOf(String.class).isNotEmpty();
    }

    @Test
    @DisplayName("Тестирование создания класса PersonPOJO, содержит поля String и Integer с аннотациями при помощи фабричного метода create()")
    void rogTest5() {
        PersonPOJO p =
            RandomObjectGenerator.nextObject(PersonPOJO.class, "create");
        assertThat(p.age).isGreaterThan(0);
        assertThat(p.age).isLessThan(100);
        assertThat(p.name).isInstanceOf(String.class).isNotEmpty();
    }

    @Test
    @DisplayName("Тестирование создания класса PersonPOJO, содержит поля String и Integer с аннотациями при помощи фабричного метода make(), которого не существует")
    void rogTest6() {
        var thrown = Assertions.assertThrows(RuntimeException.class, () -> RandomObjectGenerator.nextObject(PersonPOJO.class, "make"));
        assertThat(thrown.getCause()).hasMessage("Такой функции нет у этого класса!");
    }
}
