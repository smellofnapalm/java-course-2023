package edu;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ParallelCounterTest {
    @Test
    @DisplayName("Тест выполнения в двух потоках 100 декрементов и 101 инкремента")
    void parallelIncrementerTest1() throws InterruptedException {
        int initial = 10;
        var counter = new ParallelCounter(initial);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.decrement();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 101; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(counter.value()).isEqualTo(initial + 1);
    }

    @Test
    @DisplayName("Тест выполнения в двух потоках 100 декрементов и 101 инкремента, которые перемешаны в трех потоках")
    void parallelIncrementerTest2() throws InterruptedException {
        int initial = 10;
        var counter = new ParallelCounter(initial);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                counter.decrement();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 51; i++) {
                counter.increment();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                counter.decrement();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                counter.increment();
            }
        });
        var threadList = List.of(thread1, thread2, thread3, thread4);
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertThat(counter.value()).isEqualTo(initial + 1);
    }
}
