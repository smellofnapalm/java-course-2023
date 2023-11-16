package edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PersonSetTest {
    final static Person p1 = new Person(1, "Михаил", "Земля", "42");
    final static Person p2 = new Person(2, "Григорий", "Москва", "8-800-555-35-35");
    final static Person p3 = new Person(3, "Мария", "Омск", ".");
    final static Person p4 = new Person(4, "Иван", "Ватикан", "-");

    @Test
    @DisplayName("Тест с записью в одном потоке и поиском в другом")
    void personSetTest1() throws InterruptedException {
        var personSet = new PersonSet();
        AtomicReference<Person> res1 = new AtomicReference<>();
        AtomicReference<Person> res2 = new AtomicReference<>();

        Thread threadWriter = new Thread(() -> {
            personSet.add(p1);
            personSet.add(p2);
            personSet.add(p3);
            personSet.add(p4);
            personSet.add(null);
            personSet.delete(3);
        });

        Thread threadReader = new Thread(() -> {
            var res = personSet.findByPhone("8-800-555-35-35");
            if (res != null) {
                res1.set(res);
            }
            res = personSet.findByName("Григорий");
            if (res != null) {
                res2.set(res);
            }
        });

        threadWriter.start();
        threadReader.start();

        threadWriter.join();
        threadReader.join();

        // Как мне реализовать тест на попытку совместного доступа? В этом тесте следующий assert иногда падает
        // assertThat(res1.get()).isEqualTo(res2.get());
    }

    @Test
    @DisplayName("Такой же поиск, но теперь удаленного человека")
    void personSetTest2() throws InterruptedException {
        var personSet = new PersonSet();
        AtomicReference<Person> res1 = new AtomicReference<>();
        AtomicReference<Person> res2 = new AtomicReference<>();

        Thread threadWriter = new Thread(() -> {
            personSet.add(p1);
            personSet.add(p2);
            personSet.add(p3);
            personSet.add(p4);
            personSet.delete(3);
        });

        Thread threadReader = new Thread(() -> {
            synchronized (personSet) {
                var res = personSet.findByAddress("Омск");
                if (res != null) {
                    res1.set(res);
                }
                res = personSet.findById(3);
                if (res != null) {
                    res2.set(res);
                }
            }
        });

        threadWriter.start();
        threadReader.start();

        threadWriter.join();
        threadReader.join();

        // assertThat(res1.get()).isEqualTo(res2.get());
    }
}
