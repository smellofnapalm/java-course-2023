package edu.hw8.task1;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CyclicBarrier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RawSocketServerTest {
    @Test
    @DisplayName("Тест работы с двумя входящими соединениями")
    public void rawSocketServerTest1() throws InterruptedException {
        final RawSocketsServer server = new RawSocketsServer();
        final CyclicBarrier BARRIER = new CyclicBarrier(2, server.service::shutdown);
        Thread serverThread = new Thread(server::runServer);
        Set<String> answers = new ConcurrentSkipListSet<>();
        Thread clientThread1 = new Thread(() -> {
            var client = new RemoteClient();
            client.sendRequest("интеллект");
            answers.add(client.getAnswer());
            client.sendRequest("вкус");
            answers.add(client.getAnswer());
            try {
                BARRIER.await();
            } catch (Exception ignored) {
            }
        });
        Thread clientThread2 = new Thread(() -> {
            var client = new RemoteClient();
            client.sendRequest("личности");
            answers.add(client.getAnswer());
            client.sendRequest("глупый");
            answers.add(client.getAnswer());
            try {
                BARRIER.await();
            } catch (Exception ignored) {
            }
        });

        serverThread.start();
        Thread.sleep(50);
        clientThread1.start();
        clientThread2.start();
        clientThread1.join();
        clientThread2.join();
        serverThread.join();

        assertThat(answers.contains("Чем ниже интеллект, тем громче оскорбления")).isTrue();
        assertThat(answers.contains("Не переходи на личности там, где их нет")).isTrue();
    }
}
