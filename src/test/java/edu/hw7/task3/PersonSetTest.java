package edu.hw7.task3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PersonSetTest {
    final static Person p1 = new Person(1, "Михаил", "Земля", "42");
    final static Person p2 = new Person(2, "Григорий", "Москва", "8-800-555-35-35");
    final static Person p3 = new Person(3, "Мария", "Омск", ".");
    final static Person p4 = new Person(4, "Иван", "Ватикан", "-");

    @Test
    @DisplayName(
        "Тест с записью в одном потоке и поиском в двух других, причем после того, как был добавлен человек в базу")
    void personSetTest1() throws InterruptedException {
        var personSet = new LockPersonSet();
        AtomicReference<Person> res1 = new AtomicReference<>();
        AtomicReference<Person> res2 = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread threadWriter = new Thread(() -> {
            personSet.add(p1);
            personSet.add(p2);
            countDownLatch.countDown();
            personSet.add(p3);
            personSet.add(p4);
            personSet.add(null);
            personSet.delete(3);
        });

        Thread threadReader1 = new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            res1.set(personSet.findByPhone("8-800-555-35-35"));
        });

        Thread threadReader2 = new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            res2.set(personSet.findByName("Григорий"));
        });

        threadWriter.start();
        threadReader1.start();
        threadReader2.start();

        threadWriter.join();
        threadReader1.join();
        threadReader2.join();

        // Как мне реализовать тест на попытку совместного доступа? В этом тесте следующий assert иногда падает
        assertThat(res1.get() == res2.get()).isTrue();
        assertThat(res1.get()).isNotNull();
    }

    @Test
    @DisplayName(
        "Тест с записью в одном потоке и поиском в двух других, причем ДО того, как был добавлен человек в базу")
    void personSetTest2() throws InterruptedException {
        var personSet = new LockPersonSet();
        AtomicReference<Person> res1 = new AtomicReference<>();
        AtomicReference<Person> res2 = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch waitForWriter = new CountDownLatch(2);

        Thread threadWriter = new Thread(() -> {
            countDownLatch.countDown();
            personSet.add(p1);
            try {
                waitForWriter.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            personSet.add(p2);
            personSet.add(p3);
            personSet.add(p4);
            personSet.add(null);
            personSet.delete(3);
        });

        Thread threadReader1 = new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            res1.set(personSet.findByPhone("8-800-555-35-35"));
            waitForWriter.countDown();
        });

        Thread threadReader2 = new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            res2.set(personSet.findByName("Григорий"));
            waitForWriter.countDown();
        });

        threadWriter.start();
        threadReader1.start();
        threadReader2.start();

        threadWriter.join();
        threadReader1.join();
        threadReader2.join();

        assertThat(res1.get() == res2.get()).isTrue();
        assertThat(res1.get() == null).isTrue();
    }

    @Test
    @DisplayName("Такой же поиск, но теперь удаленного человека")
    void personSetTest3() throws InterruptedException {
        var personSet = new LockPersonSet();
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
                    res = personSet.findById(3);
                    if (res != null) {
                        res2.set(res);
                    }
                }
            }
        });

        threadWriter.start();
        threadReader.start();

        threadWriter.join();
        threadReader.join();

        assertThat(res1.get()).isEqualTo(res2.get());
    }
}
