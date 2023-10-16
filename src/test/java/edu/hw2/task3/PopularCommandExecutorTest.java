package edu.hw2.task3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PopularCommandExecutorTest {
    @Test
    @DisplayName("Тест коннекта, который никогда не кидает exception")
    void executorTest1() {
        StableConnection connection = new StableConnection();
        connection.execute("make friends");
        connection.close();
    }

    @Test
    @DisplayName("Попытаемся сделать число попыток отрицательным")
    void executorTest2() {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                ConnectionManager manager = new DefaultConnectionManager();
                final int maxAttempts = -1;
                PopularCommandExecutor executor = new PopularCommandExecutor(manager, maxAttempts);

                executor.updatePackages();
            }
        );
        Assertions.assertEquals("maxAttempts > 0", thrown.getMessage());
    }

    @Test
    @DisplayName("Используем коннект, который всегда выдает exception")
    void executorTest3() {
        ConnectionException thrown = Assertions.assertThrows(
            ConnectionException.class,
            () -> {
                FaultyConnection connection = new FaultyConnection(1.0);
                try {
                    connection.execute("sudo rm -rf /*");
                } catch (Exception e) {
                    throw e;
                } finally {
                    connection.close();
                }
            }
        );
        Assertions.assertEquals(
            "Faulty Connection не удалось выполнить команду " + "sudo rm -rf /*",
            thrown.getMessage()
        );
    }
}
