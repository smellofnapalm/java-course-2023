package edu.hw3.task5;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw3.task5.ParseContacts.parseContacts;
import static edu.hw3.task5.ParseContacts.Contact;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ParseContactsTest {
    @Test
    @DisplayName("Тест с именами и фамилиями, в возрастающем порядке")
    void parseContactsTest1() {
        String[] input = {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        String order = "ASC";

        Contact[] arr = parseContacts(input, order);

        assertThat(arr[0].toString()).isEqualTo("Thomas Aquinas");
        assertThat(arr[2].toString()).isEqualTo("David Hume");
    }

    @Test
    @DisplayName("Тест с именами и фамилиями, в убывающем порядке")
    void parseContactsTest2() {
        String[] input = {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        String order = "DESC";

        Contact[] arr = parseContacts(input, order);

        assertThat(arr[0].toString()).isEqualTo("John Locke");
        assertThat(arr[2].toString()).isEqualTo("Rene Descartes");
    }

    @Test
    @DisplayName("Тест с некорректным порядком сортировки")
    void parseContactsTest3() {
        var thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                String[] input = {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
                String order = "DESC_ASC";

                Contact[] arr = parseContacts(input, order);
            }
        );

        assertThat(thrown).hasMessage("Второй аргумент должен быть ASC или DESC!");
    }

    @Test
    @DisplayName("Тест с null")
    void parseContactsTest4() {
        String[] input = null;
        String order = "DESC";

        Contact[] arr = parseContacts(input, order);

        assertThat(arr).hasSize(0);
    }

    @Test
    @DisplayName("Тест с именами")
    void parseContactsTest5() {
        String[] input = {"John", "Thomas Aquinas", "David", "Rene"};
        String order = "ASC";

        Contact[] arr = parseContacts(input, order);

        assertThat(arr[0].toString()).isEqualTo("Thomas Aquinas");
        assertThat(arr[2].toString()).isEqualTo("John");
    }
}
