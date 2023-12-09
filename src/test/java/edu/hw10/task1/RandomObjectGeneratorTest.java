package edu.hw10.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static java.lang.Math.abs;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RandomObjectGeneratorTest {
    @Test
    @DisplayName("Тестирование создания String")
    void rogTest1() {
        String s = RandomObjectGenerator.nextObjectConstructor(String.class, new Class<?>[] {String.class});
        assertThat(s.length()).isLessThan(11);
    }

    @Test
    @DisplayName("Тестирование создания Integer")
    void rogTest2() {
        Integer i = RandomObjectGenerator.nextObjectConstructor(Integer.class, new Class<?>[] {int.class});
        assertThat(i).isInstanceOf(Integer.class);
    }

    @Test
    @DisplayName("Тестирование создания класса PersonPOJO, содержит поля String и Integer")
    void rogTest3() {
        PersonPOJO p =
            RandomObjectGenerator.nextObjectConstructor(PersonPOJO.class, new Class<?>[] {String.class, int.class});
        assertThat(abs(p.age)).isGreaterThan(0);
        assertThat(p.name).isInstanceOf(String.class).isNotEmpty();
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
}
