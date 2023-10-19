package edu.hw3.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static edu.hw3.task7.NullComparator.getNullComparator;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NullComparatorTest {
    @Test
    @DisplayName("Тест компаратора для Int")
    void nullComparatorTest1() {
        Comparator<Integer> cmp = (o1, o2) -> o1 - o2;
        Comparator<Integer> nullComparator = getNullComparator(cmp);

        Integer num1 = 5;
        Integer num2 = 0;
        Integer num3 = -1;
        Integer nullNum = null;

        assertThat(num1).usingComparator(nullComparator).isGreaterThan(null);
        assertThat(num2).usingComparator(nullComparator).isGreaterThan(null);
        assertThat(nullNum).usingComparator(nullComparator).isEqualTo(nullNum);
        assertThat(num3).usingComparator(nullComparator).isLessThan(num2);
    }

    @Test
    @DisplayName("Тест компаратора для String")
    void nullComparatorTest2() {
        Comparator<String> cmp = Comparator.comparing(String::toString);
        Comparator<String> nullComparator = getNullComparator(cmp);

        String s1 = "Tinkoff";
        String s2 = "Yandex";
        String nullStr = null;

        assertThat(s1).usingComparator(nullComparator).isGreaterThan(nullStr);
        assertThat(nullComparator.compare(nullStr, s2)).isLessThan(0);
        assertThat(s1).usingComparator(nullComparator).isLessThan(s2);
    }
}
