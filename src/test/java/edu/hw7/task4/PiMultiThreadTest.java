package edu.hw7.task4;

import edu.hw7.task4.PiMultiThread;
import edu.hw7.task4.PiSingleThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PiMultiThreadTest {
    @ParameterizedTest
    @DisplayName("Подсчет времени для разного числа итераций, 1 поток, 5 потоков, 10 потоков и 20 потоков")
    @ValueSource(ints = {10_000, 100_000, 1_000_000, 10_000_000})
    void piMultiThreadTest1(int iters) throws InterruptedException {
        var time = PiSingleThread.calcTime(iters);
        var time20 = PiMultiThread.calcTime(iters, 20);
        var time10 = PiMultiThread.calcTime(iters, 10);
        var time5 = PiMultiThread.calcTime(iters, 5);

        System.out.printf(
            "Сравнение времени исполнения для %d итераций и 1, 5, 10 и 20 потоков:\n",
            iters
        );
        System.out.println(time);
        System.out.println(time5);
        System.out.println(time10);
        System.out.println(time20);
    }

    @ParameterizedTest
    @DisplayName("Подсчет погрешности для разного числа итераций, 1 поток, 5 потоков, 10 потоков и 20 потоков")
    @ValueSource(ints = {10_000, 100_000, 1_000_000, 10_000_000})
    void piMultiThreadTest2(int iters) throws InterruptedException {
        var error = PiSingleThread.calcError(iters);
        var error20 = PiMultiThread.calcError(iters, 20);
        var error10 = PiMultiThread.calcError(iters, 10);
        var error5 = PiMultiThread.calcError(iters, 5);

        System.out.printf(
            "Сравнение погрешности подсчета для %d итераций и 1, 5, 10 и 20 потоков:\n",
            iters
        );
        System.out.println(error);
        System.out.println(error5);
        System.out.println(error10);
        System.out.println(error20);
    }
}
